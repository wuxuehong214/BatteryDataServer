package com.green.battery.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.green.battery.dao.LogDao;
import com.green.battery.dao.TaskDao;
import com.green.battery.dao.UserDao;
import com.green.battery.db.NotInitializedException;
import com.green.battery.entity.LogEntity;
import com.green.battery.entity.TaskEntity;
import com.green.battery.entity.TaskHistoryEntity;
import com.green.battery.entity.UserEntity;
import com.green.battery.entity.ext.DashBoardStatus;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport implements RequestAware,
		SessionAware, ApplicationAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1164288905958048143L;
	private Map<String, Object> session;
	private Map<String, Object> request;
	private Map<String, Object> application;

	private UserEntity user;
	private String loginTip; // 登录提示
	private String page;
	private String tip;  //通用提示
	private List<UserEntity> data;
	private DashBoardStatus status;  //个人工作台 首页状态
	private int role;
	private long userid;
	private String pagetmp;
	
	private DashBoardStatus getDashBoardStatus(long userid) throws SQLException, TimeoutException, NotInitializedException{
		TaskDao dao = new TaskDao();
		int total = dao.countByState(TaskEntity.STATE_ALL,userid);
		int waiting = dao.countByState(TaskEntity.STATE_QUEUE,userid);
		int finished = dao.countByState(TaskEntity.STATE_FINISHED,userid);
		int executing = dao.countByState(TaskEntity.STATE_EXECUTING,userid);
		int exception = dao.countByState(TaskEntity.STATE_EXCEPTION,userid);
		
		status = new DashBoardStatus();
		status.setException(exception);
		status.setTotal(total);
		status.setExecuting(executing);
		status.setFinished(finished);
		status.setWait(waiting);
		
		return status;
	}
	
	public String update(){
		UserDao userdao = new UserDao();
		try {
			userdao.update(user);
			tip = getText("global.operate.success");
//			tip = "操作成功!";
		} catch (Exception e) {
			e.printStackTrace();
			tip = getText("global.operate.fail");
//			tip = "操作失败!";
		}
		return "target";
	}
	
	public String query(){
		UserDao userdao = new UserDao();
		try {
			user = userdao.getUser(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "target";
	}
	
	/**
	 * 个人工作台
	 * @return
	 */
	public String dashboard(){
		try {
			UserEntity user = (UserEntity) session.get("user");
			long id = 0;
			if(user != null) id = user.getId();
			status = getDashBoardStatus(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		page = "index";
		return "target";
	}
	
	/**
	 * 新增客户账号
	 * @return
	 */
	public String add(){
		
		boolean ok = false;
		page = "user_add";
		if(user == null || user.getUserName() == null || "".equals(user.getUserName())) {
			tip = "请输入账号信息";
			return "target";
		}
		if(user.getPassWord() ==null || "".equals(user.getPassWord())){
			tip = "输入账号密码";
			return "targt";
		}
		
		UserDao dao = new UserDao();
		try {
			dao.addUser(user);
			ok = true;
		} catch (Exception e) {
			e.printStackTrace();
			tip = "异常:["+e.getMessage()+"]";
		}
		if(ok) page = pagetmp;
		return "target";
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public String del(){
		
		if(user != null){
			 UserDao userdao = new UserDao();
			 try {
				userdao.delUser(user.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "target";
		
	}
	
	/**
	 * 账号登陆
	 * @return
	 */
	public String login() {

		UserDao dao = new UserDao();

		boolean ok = false;

		if (user == null || user.getUserName() == null
				|| "".equals(user.getUserName()) || user.getPassWord() == null
				|| "".equals(user.getPassWord())) {
			loginTip = "请输入用户名/密码!";
		} else {
			try {
				UserEntity u = dao.getUser(user.getUserName());
				if (u == null) {
					loginTip = "该账号未注册!";
				} else {
					if (u.getPassWord().equals(user.getPassWord())) {
						ok = true;
						
						//设置session
						session.put("user", u);
						
						//更新最新登录日期
						 u.setLastLogin(new Date());
						 dao.update(u);
						 
						 //记录登录日志
						 LogDao d = new LogDao();
						 LogEntity log = new LogEntity();
						 log.setUserId(u.getId());
						 log.setType(LogEntity.TYPE_LOGIN);
						 log.setEventType(LogEntity.TYPE_LOGIN_IN);
						 log.setContent(LogEntity.event(log.getEventType()));
						 d.addLog(log);
						 
						 //DashBoard status
						 status = getDashBoardStatus(u.getId());
					} else {
						loginTip = "密码错误!";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				ok = false;
				loginTip = "登录异常:[" + e.getMessage() + "]";
			}
		}
		if (ok)
			page = "index";
		else
			page = "login";
		return "target";
	}
	
	/**
	 * 更新基本信息
	 * @return
	 */
	public String updateBasic(){
		UserEntity u = (UserEntity) session.get("user");
		u.setName(user.getName());
		u.setSex(user.getSex());
		u.setPhoneNum(user.getPhoneNum());
		u.setEmail(user.getEmail());
		u.setQq(user.getQq());
		u.setCompany(user.getCompany());
		u.setAddress(user.getAddress());
		
		UserDao dao = new UserDao();
		try {
			dao.update(u);
//			tip = "更新成功!";
			tip = getText("global.operate.success");
		} catch (Exception e) {
			e.printStackTrace();
//			tip = "更新异常:["+e.getMessage()+"]";
			tip = getText("global.operate.fail")+"["+e.getMessage()+"]";
		}
		page = "personal_profile";
		return "target";
	}
	
	/**
	 * 更新密码
	 * @return
	 */
	public String updatePassword(){
		UserEntity u = (UserEntity) session.get("user");
		u.setPassWord(user.getPassWord());
		UserDao dao = new UserDao();
		try {
			dao.update(u);
//			tip = "更新成功!";
			tip = getText("global.operate.success");
		} catch (Exception e) {
			e.printStackTrace();
//			tip = "更新异常:["+e.getMessage()+"]";
			tip = getText("global.operate.fail")+"["+e.getMessage()+"]";
		}
		page = "personal_password";
		return "target";
	}
	
	/**
	 * 退出
	 * @return
	 */
	public String logout(){
		
		UserEntity u = (UserEntity) session.get("user");
		 //记录登出日志
		 LogDao d = new LogDao();
		 LogEntity log = new LogEntity();
		 log.setUserId(u.getId());
		 log.setType(LogEntity.TYPE_LOGIN);
		 log.setEventType(LogEntity.TYPE_LOGIN_OUT);
		 log.setContent(LogEntity.event(log.getEventType()));
		 
		 try {
			 session.remove("user");
			d.addLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 page = "login";
		 return "target";
	}
	
	/**
	 * 账号管理
	 * @return
	 */
	public String users(){
		UserDao dao = new UserDao();
		try {
			data = dao.getUseByRoler(role);
			
			for(UserEntity user:data){
				user.setRemark("<a	href=\"#myModal\" role=\"button\" data-toggle=\"modal\" onclick=\"delUser('"+user.getId()+"')\"><i class=\"fa fa-trash-o\"></i></a> &nbsp;&nbsp;&nbsp;&nbsp;"
						+ "<a	href=\"#\" onclick=\"editUser('"+user.getId()+"')\"><i class=\"fa fa-edit\"></i></a>  ");
				user.setSexs(getText(user.getSex()==0?"user.sex.male":"user.sex.female"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setApplication(Map<String, Object> arg0) {
		this.application = arg0;
	}

	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	public void setRequest(Map<String, Object> arg0) {
		this.request = arg0;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getLoginTip() {
		return loginTip;
	}

	public void setLoginTip(String loginTip) {
		this.loginTip = loginTip;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}

	public List<UserEntity> getData() {
		return data;
	}

	public void setData(List<UserEntity> data) {
		this.data = data;
	}

	public DashBoardStatus getStatus() {
		return status;
	}

	public void setStatus(DashBoardStatus status) {
		this.status = status;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getPagetmp() {
		return pagetmp;
	}

	public void setPagetmp(String pagetmp) {
		this.pagetmp = pagetmp;
	}
}
