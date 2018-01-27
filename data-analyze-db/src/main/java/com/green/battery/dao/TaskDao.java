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
import com.green.battery.entity.TaskEntity;
import com.green.battery.entity.TaskHistoryEntity;
import com.green.battery.entity.UserEntity;

/**
 * 诊断任务管理
 * 
 * @author Administrator
 * 
 */
public class TaskDao {

	private DatabasePool pool = DatabasePool.getInstance();
	
	public void deleteTask(long taskid) throws TimeoutException, NotInitializedException, SQLException{
		String sql = "delete from td_task where id=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, taskid);
			try {
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

	public List<TaskEntity> getTasksByState(String state)
			throws TimeoutException, NotInitializedException, SQLException {
		String sql = "select * from td_task where state=?";
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();

		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, state);
			try {
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						TaskEntity task = new TaskEntity();
						task.setId(rs.getLong("ID"));
						task.setName(rs.getString("NAME"));
						task.setSerialNum(rs.getString("SERIAL_NUM"));
						task.setUserId(rs.getLong("USER_ID"));
						task.setSubTime(rs.getTimestamp("SUB_TIME"));
						task.setExecuteTime(rs.getTimestamp("EXECUTE_TIME"));
						task.setFinishTime(rs.getTimestamp("FINISH_TIME"));
						task.setState(rs.getString("STATE"));
						task.setWaitTime(rs.getInt("WAIT_TIME"));
						task.setActionTime(rs.getInt("ACTION_TIME"));
						task.setPath(rs.getString("PATH"));
						task.setReason(rs.getString("REASON"));
						tasks.add(task);
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
		return tasks;
	}

	/**
	 * 根据状态统计 任务
	 * 
	 * @param state
	 * @return
	 * @throws SQLException
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 */
	public int countByState(String state, long userid) throws SQLException,
			TimeoutException, NotInitializedException {
		String sql = null;
		if (TaskEntity.STATE_ALL.equals(state))
			sql = "select count(id) from td_task where USER_ID=?";
		else
			sql = "select count(id) from td_task where USER_ID=? and  state=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, userid);
			if (!TaskEntity.STATE_ALL.equals(state))
				ps.setString(2, state);
			try {
				rs = ps.executeQuery();
				try {
					if (rs.next())
						return rs.getInt(1);
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
		return 0;
	}

	/**
	 * 批量插入电池数据
	 * 
	 * @param datas
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public void addTask(TaskEntity task) throws TimeoutException,
			NotInitializedException, SQLException {

		String sql = "insert into td_task(NAME,USER_ID,SUB_TIME,STATE,SERIAL_NUM,PATH) values(?,?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, task.getName());
				ps.setLong(2, task.getUserId());
				ps.setTimestamp(3, new Timestamp(task.getSubTime().getTime()));
				ps.setString(4, task.getState());
				ps.setString(5, task.getSerialNum());
				ps.setString(6, task.getPath());
				ps.execute();

				long id = queryLastInsertId(conn);
				task.setId(id);
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

	private long queryLastInsertId(Connection conn) throws SQLException {
		if (conn != null) {
			java.sql.PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				try {
					ResultSet rs = ps.executeQuery();
					try {
						if (rs.next())
							return rs.getShort(1);
					} finally {
						rs.close();
					}
				} finally {
					ps.close();
				}
			} catch (SQLException e) {
				throw e;
			}
		}
		return -1;
	}

	public void update(TaskEntity task) throws TimeoutException,
			NotInitializedException, SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("update td_task set");

		Object[] objs = new Object[10];
		int index = 0;
		if (task.getSerialNum() != null) {
			sb.append(" SERIAL_NUM=?,");
			objs[index++] = task.getSerialNum();
		}
		if (task.getExecuteTime() != null) {
			sb.append(" EXECUTE_TIME=?,");
			objs[index++] = task.getExecuteTime();
		}
		if (task.getFinishTime() != null) {
			sb.append(" FINISH_TIME=?,");
			objs[index++] = task.getFinishTime();
		}
		if (task.getState() != null) {
			sb.append(" STATE=?,");
			objs[index++] = task.getState();
		}
		if (task.getWaitTime() != -1) {
			sb.append(" WAIT_TIME=?,");
			objs[index++] = task.getWaitTime();
		}
		if (task.getActionTime() != -1) {
			sb.append(" ACTION_TIME=?,");
			objs[index++] = task.getActionTime();
		}
		if (task.getPath() != null) {
			sb.append(" PATH=?,");
			objs[index++] = task.getPath();
		}
		if (task.getReason() != null) {
			sb.append(" REASON=?,");
			objs[index++] = task.getReason();
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append(" where id=?");
		String sql = sb.toString();
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				for (int i = 0; i < index; i++)
					ps.setObject(i + 1, objs[i]);
				ps.setLong(index + 1, task.getId());
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

	/**
	 * 
	 * @param userId
	 * @param start
	 * @param size
	 * @return
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public List<TaskEntity> getTasks(int role, long userId, int start, int size)
			throws TimeoutException, NotInitializedException, SQLException {
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		String sql = null;
		if (userId != -1) {
			sql = "select * from td_task where USER_ID=? order by SUB_TIME desc limit ?,? ";
		} else
			sql = "select * from td_task order by SUB_TIME desc limit ?,? ";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {

				if (userId != -1) {
					ps.setLong(1, userId);
					ps.setInt(2, start);
					ps.setInt(3, size);
				} else {
					ps.setInt(1, start);
					ps.setInt(2, size);
				}

				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						TaskEntity task = new TaskEntity();
						task.setId(rs.getLong("ID"));
						task.setName(rs.getString("NAME"));
						task.setSerialNum(rs.getString("SERIAL_NUM"));
						task.setUserId(rs.getLong("USER_ID"));
						task.setSubTime(rs.getTimestamp("SUB_TIME"));
						task.setExecuteTime(rs.getTimestamp("EXECUTE_TIME"));
						task.setFinishTime(rs.getTimestamp("FINISH_TIME"));
						task.setState(rs.getString("STATE"));
						task.setWaitTime(rs.getInt("WAIT_TIME"));
						task.setActionTime(rs.getInt("ACTION_TIME"));
						task.setPath(rs.getString("PATH"));
						task.setReason(rs.getString("REASON"));
						tasks.add(task);
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
		return tasks;
	}

	public List<TaskHistoryEntity> getAllTasks(int start, int size)
			throws TimeoutException, NotInitializedException, SQLException {
		List<TaskHistoryEntity> tasks = new ArrayList<TaskHistoryEntity>();
		String sql = null;
		sql = "select a.*,b.username,b.name from td_task a, td_user b where a.USER_ID=b.ID order by a.SUB_TIME desc limit ?,? ";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setInt(1, start);
				ps.setInt(2, size);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						TaskHistoryEntity history = new TaskHistoryEntity();
						TaskEntity task = new TaskEntity();
						UserEntity user = new UserEntity();
						
						task.setId(rs.getLong("ID"));
						task.setName(rs.getString("NAME"));
						task.setSerialNum(rs.getString("SERIAL_NUM"));
						task.setUserId(rs.getLong("USER_ID"));
						task.setSubTime(rs.getTimestamp("SUB_TIME"));
						task.setExecuteTime(rs.getTimestamp("EXECUTE_TIME"));
						task.setFinishTime(rs.getTimestamp("FINISH_TIME"));
						task.setState(rs.getString("STATE"));
						task.setWaitTime(rs.getInt("WAIT_TIME"));
						task.setActionTime(rs.getInt("ACTION_TIME"));
						task.setPath(rs.getString("PATH"));
						task.setReason(rs.getString("REASON"));
						
						user.setUserName(rs.getString("b.USERNAME"));
						user.setName(rs.getString("b.NAME"));
						
						history.setTask(task);
						history.setUser(user);
						
						tasks.add(history);
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
		return tasks;
	}

	/**
	 * 
	 * @param taskId
	 * @return
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public TaskEntity getTask(long taskId) throws TimeoutException,
			NotInitializedException, SQLException {
		String sql = "select * from td_task where id=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setLong(1, taskId);
				rs = ps.executeQuery();
				try {
					if (rs.next()) {
						TaskEntity task = new TaskEntity();
						task.setId(rs.getLong("ID"));
						task.setName(rs.getString("NAME"));
						task.setSerialNum(rs.getString("SERIAL_NUM"));
						task.setUserId(rs.getLong("USER_ID"));
						task.setSubTime(rs.getTimestamp("SUB_TIME"));
						task.setExecuteTime(rs.getTimestamp("EXECUTE_TIME"));
						task.setFinishTime(rs.getTimestamp("FINISH_TIME"));
						task.setState(rs.getString("STATE"));
						task.setWaitTime(rs.getInt("WAIT_TIME"));
						task.setActionTime(rs.getInt("ACTION_TIME"));
						task.setPath(rs.getString("PATH"));
						task.setReason(rs.getString("REASON"));
						return task;
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

	public static void main(String args[]) {
		TaskDao dao = new TaskDao();
		// TaskEntity task = new TaskEntity();
		// task.setSubTime(new Date());
		try {
			TaskEntity task = dao.getTask(2);
			System.out.println(task);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotInitializedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
