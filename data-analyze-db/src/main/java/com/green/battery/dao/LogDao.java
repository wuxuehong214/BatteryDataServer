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
import com.green.battery.entity.LogEntity;
import com.green.battery.entity.TaskEntity;
import com.green.battery.entity.UserEntity;

public class LogDao {
	
	private DatabasePool pool = DatabasePool.getInstance();
	
	/**
	 * 添加日志
	 * @param log
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public void addLog(LogEntity log) throws TimeoutException, NotInitializedException, SQLException{
		String sql = "insert into td_log(USER_ID,TASK_ID,TYPE, LOG_TIME,EVENT_TYPE,CONTENT,LEVEL) values(?,?,?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setLong(1, log.getUserId());
				ps.setLong(2, log.getTaskId());
				ps.setInt(3, log.getType());
				ps.setTimestamp(4, new Timestamp(log.getLogTime().getTime()));
				ps.setInt(5, log.getEventType());
				ps.setString(6, log.getContent());
				ps.setInt(7, log.getLevel());
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
	
	public List<LogEntity>getAnalyzeLogs(int role, long userId) throws TimeoutException, NotInitializedException, SQLException{
		String sql = null;
		if(role == UserEntity.ROLE_ADMIN)
			sql = "select a.*,c.USERNAME,c.NAME from td_log a, td_user c where a.USER_ID=c.ID and a.TYPE = 2 order by a.LOG_TIME desc";
		else 
			sql = "select a.*,c.USERNAME,c.NAME from td_log a, td_user c where a.USER_ID=c.ID and a.TYPE = 2  and c.id = ? order by a.LOG_TIME desc";
		Connection conn = pool.getConnection();
		List<LogEntity> logs = new ArrayList<LogEntity>();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				if(role != UserEntity.ROLE_ADMIN)
					ps.setLong(1, userId);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						
						LogEntity log = new LogEntity();
						log.setId(rs.getLong("ID"));
						log.setUserId(rs.getLong("USER_ID"));
						log.setTaskId(rs.getLong("TASK_ID"));
						log.setType(rs.getInt("TYPE"));
						log.setLogTime(rs.getTimestamp("LOG_TIME"));
						log.setEventType(rs.getInt("EVENT_TYPE"));
						log.setContent(rs.getString("CONTENT"));
						log.setLevel(rs.getInt("LEVEL"));
						
						UserEntity u = new UserEntity();
						u.setUserName(rs.getString("c.USERNAME"));
						u.setName(rs.getString("c.NAME"));
						log.setUser(u);
						
//						TaskEntity t = new TaskEntity();
//						t.setName(rs.getString("b.name"));
//						t.setSerialNum(rs.getString("b.SERIAL_NUM"));
//						log.setTask(t);
						logs.add(log);
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
		return logs;
	
	}
	
	public List<LogEntity> getLoginLogs(int role, long userId) throws SQLException, TimeoutException, NotInitializedException{
		String sql = null;
		if(role == UserEntity.ROLE_ADMIN)
				sql  = "select a.*,b.username,b.name from td_log a, td_user b where a.USER_ID = b.ID and TYPE =1 order by LOG_TIME desc";
		else 
			sql  = "select a.*,b.username,b.name from td_log a, td_user b where a.USER_ID = b.ID and TYPE =1 and b.ID=? order by LOG_TIME desc";
		Connection conn = pool.getConnection();
		List<LogEntity> logs = new ArrayList<LogEntity>();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				if(role != UserEntity.ROLE_ADMIN)
					ps.setLong(1, userId);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						
						LogEntity log = new LogEntity();
						log.setId(rs.getLong("ID"));
						log.setUserId(rs.getLong("USER_ID"));
						log.setTaskId(rs.getLong("TASK_ID"));
						log.setType(rs.getInt("TYPE"));
						log.setLogTime(rs.getTimestamp("LOG_TIME"));
						log.setEventType(rs.getInt("EVENT_TYPE"));
						log.setContent(rs.getString("CONTENT"));
						log.setLevel(rs.getInt("LEVEL"));
						
						UserEntity u = new UserEntity();
						u.setUserName(rs.getString("USERNAME"));
						u.setName(rs.getString("NAME"));
						log.setUser(u);
						logs.add(log);
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
		return logs;
	}

}
