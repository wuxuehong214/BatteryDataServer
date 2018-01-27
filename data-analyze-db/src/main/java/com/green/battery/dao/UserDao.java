package com.green.battery.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.green.battery.db.DatabasePool;
import com.green.battery.db.NotInitializedException;
import com.green.battery.entity.UserEntity;

public class UserDao {
	
	private DatabasePool pool = DatabasePool.getInstance();
	
	
	public void delUser(long id) throws TimeoutException, NotInitializedException, SQLException {
		String sql = "delete from td_user where id=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setLong(1, id);
				ps.executeUpdate();
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			conn.close();
			throw e;
		} finally {
			pool.release(conn);
		}
	}
	
	public void addUser(UserEntity user) throws SQLException, TimeoutException, NotInitializedException{
		String sql = "insert into td_user(USERNAME,PASSWORD,ROLE,CREATED_AT,NAME,SEX,PHONENUM,COMPANY,EMAIL,QQ,REMARK,ADDRESS) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, user.getUserName());
				ps.setString(2, user.getPassWord());
				ps.setInt(3, user.getRole());
				ps.setTimestamp(4, new Timestamp(user.getCreatedAt().getTime()));
				ps.setString(5, user.getName());
				ps.setInt(6, user.getSex());
				ps.setString(7, user.getPhoneNum());
				ps.setString(8, user.getCompany());
				ps.setString(9, user.getEmail());
				ps.setString(10, user.getQq());
				ps.setString(11, user.getRemark());
				ps.setString(12, user.getAddress());
				ps.execute();
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			conn.close();
			throw e;
		} finally {
			pool.release(conn);
		}
	}
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public UserEntity getUser(long userId) throws TimeoutException, NotInitializedException, SQLException{
		String	sql = "select * from td_user where id=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setLong(1, userId);
				rs = ps.executeQuery();
				try {
					if (rs.next()) {
						UserEntity user = new UserEntity();
						user.setId(rs.getLong("ID"));
						user.setUserName(rs.getString("USERNAME"));
						user.setPassWord(rs.getString("PASSWORD"));
						user.setRole(rs.getInt("ROLE"));
						user.setCreatedAt(rs.getTimestamp("CREATED_AT"));
						user.setLastLogin(rs.getTimestamp("LAST_LOGIN"));
						user.setName(rs.getString("NAME"));
						user.setSex(rs.getInt("SEX"));
						user.setPhoneNum(rs.getString("PHONENUM"));
						user.setCompany(rs.getString("COMPANY"));
						user.setEmail(rs.getString("EMAIL"));
						user.setQq(rs.getString("QQ"));
						user.setRemark(rs.getString("REMARK"));
						user.setAddress(rs.getString("ADDRESS"));
						return user;
					}
				} finally {
					rs.close();
				}
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			conn.close();
			throw e;
		} finally {
			pool.release(conn);
		}
		return null;
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public List<UserEntity> getUseByRoler(int role) throws TimeoutException, NotInitializedException, SQLException{
		String	sql = "select * from td_user where ROLE=?";
		List<UserEntity> users = new ArrayList<UserEntity>();
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setInt(1, role);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						UserEntity user = new UserEntity();
						user.setId(rs.getLong("ID"));
						user.setUserName(rs.getString("USERNAME"));
						user.setPassWord(rs.getString("PASSWORD"));
						user.setRole(rs.getInt("ROLE"));
						user.setCreatedAt(rs.getTimestamp("CREATED_AT"));
						user.setLastLogin(rs.getTimestamp("LAST_LOGIN"));
						user.setName(rs.getString("NAME"));
						user.setSex(rs.getInt("SEX"));
						user.setPhoneNum(rs.getString("PHONENUM"));
						user.setCompany(rs.getString("COMPANY"));
						user.setEmail(rs.getString("EMAIL"));
						user.setQq(rs.getString("QQ"));
						user.setRemark(rs.getString("REMARK"));
						user.setAddress(rs.getString("ADDRESS"));
						users.add(user);
					}
				} finally {
					rs.close();
				}
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			conn.close();
			throw e;
		} finally {
			pool.release(conn);
		}
		return users;
	}
	
	/**
	 * 
	 * @param userName
	 * @return
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public UserEntity getUser(String userName) throws TimeoutException, NotInitializedException, SQLException{
		String	sql = "select * from td_user where USERNAME=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, userName);
				rs = ps.executeQuery();
				try {
					if (rs.next()) {
						UserEntity user = new UserEntity();
						user.setId(rs.getLong("ID"));
						user.setUserName(rs.getString("USERNAME"));
						user.setPassWord(rs.getString("PASSWORD"));
						user.setRole(rs.getInt("ROLE"));
						user.setCreatedAt(rs.getTimestamp("CREATED_AT"));
						user.setLastLogin(rs.getTimestamp("LAST_LOGIN"));
						user.setName(rs.getString("NAME"));
						user.setSex(rs.getInt("SEX"));
						user.setPhoneNum(rs.getString("PHONENUM"));
						user.setCompany(rs.getString("COMPANY"));
						user.setEmail(rs.getString("EMAIL"));
						user.setQq(rs.getString("QQ"));
						user.setRemark(rs.getString("REMARK"));
						user.setAddress(rs.getString("ADDRESS"));
						return user;
					}
				} finally {
					rs.close();
				}
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			conn.close();
			throw e;
		} finally {
			pool.release(conn);
		}
		return null;
	}
	
	/**
	 * 
	 * @param userName
	 * @return
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public UserEntity getUsers(int role) throws TimeoutException, NotInitializedException, SQLException{
		String	sql = "select * from td_user where ROLE=?";
		List<UserEntity> users = new ArrayList<UserEntity>();
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setInt(1, role);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						UserEntity user = new UserEntity();
						user.setId(rs.getLong("ID"));
						user.setUserName(rs.getString("USERNAME"));
						user.setPassWord(rs.getString("PASSWORD"));
						user.setRole(rs.getInt("ROLE"));
						user.setCreatedAt(rs.getTimestamp("CREATED_AT"));
						user.setLastLogin(rs.getTimestamp("LAST_LOGIN"));
						user.setName(rs.getString("NAME"));
						user.setSex(rs.getInt("SEX"));
						user.setPhoneNum(rs.getString("PHONENUM"));
						user.setCompany(rs.getString("COMPANY"));
						user.setEmail(rs.getString("EMAIL"));
						user.setQq(rs.getString("QQ"));
						user.setRemark(rs.getString("REMARK"));
						user.setAddress(rs.getString("ADDRESS"));
						users.add(user);
						return user;
					}
				} finally {
					rs.close();
				}
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			conn.close();
			throw e;
		} finally {
			pool.release(conn);
		}
		return null;
	}
	
	
	public void update(UserEntity  user) throws TimeoutException, NotInitializedException, SQLException{
		StringBuffer sb = new StringBuffer();
		sb.append("update td_user set");
		
		Object[] objs = new Object[10];
		int index = 0;
		
		if(user.getPassWord()  !=  null){
			sb.append(" PASSWORD=?,");
			objs[index++] = user.getPassWord();
		}
		
		if(user.getLastLogin()  !=  null){
			sb.append(" LAST_LOGIN=?,");
			objs[index++] = user.getLastLogin();
		}
		if(user.getName()!= null) {
			sb.append(" NAME=?,");
			objs[index++] = user.getName();
		}
		if(user.getSex() ==0 || user.getSex() == 1){
			sb.append(" SEX=?,");
			objs[index++] = user.getSex();
		}
		if(user.getPhoneNum() != null){
			sb.append(" PHONENUM=?,");
			objs[index++] = user.getPhoneNum();
		}
		if(user.getCompany() != null){
			sb.append(" COMPANY=?,");
			objs[index++] = user.getCompany();
		}
		if(user.getEmail() != null){
			sb.append(" EMAIL=?,");
			objs[index++] = user.getEmail();
		}
		
		if(user.getQq() != null){
			sb.append(" QQ=?,");
			objs[index++] = user.getQq();
		}
		
		if(user.getRemark() != null){
			sb.append(" REMARK=?,");
			objs[index++] = user.getRemark();
		}
		
		if(user.getAddress() != null){
			sb.append(" ADDRESS=?,");
			objs[index++] = user.getAddress();
		}
		
		sb.delete(sb.length()-1, sb.length());
		sb.append(" where id=?");
		String sql = sb.toString();
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				for(int i=0;i<index;i++)
					ps.setObject(i+1, objs[i]);
				ps.setLong(index+1, user.getId());
				ps.executeUpdate();
			} finally {
				ps.close();
			}
		} catch (SQLException e) {
			conn.close();
			throw e;
		} finally {
			pool.release(conn);
		}
	}

}
