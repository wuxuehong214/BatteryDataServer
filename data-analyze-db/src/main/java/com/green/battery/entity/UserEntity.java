package com.green.battery.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserEntity implements Serializable{
	
	public static final int ROLE_ADMIN = 0;
	public static final int ROLE_USER = 1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9213342220915048265L;
	
	private long id;
	private String userName;
	private String passWord;
	private int role = 1;   //  0-管理员  1-客户
	private Date createdAt = new Date();
	private Date lastLogin;
	private String name;
	private int sex = 0;    //0- male  1-female
	private String phoneNum;
	private String company;
	private String email;
	private String qq;
	private String remark;
	private String address;
	
	private String sexs;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String getLastLogins(){
		if(lastLogin != null)
			 return sdf.format(lastLogin);
		return "";
	}
	
	public void setSexs(String sexs) {
		this.sexs = sexs;
	}

	public String getSexs(){
		if(sexs != null) return sexs;
		if(sex == 0) return "男";
		else return "女";
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
