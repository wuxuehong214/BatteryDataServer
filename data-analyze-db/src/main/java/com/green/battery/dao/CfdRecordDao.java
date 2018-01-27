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
import com.green.battery.entity.CfdRecord;

public class CfdRecordDao {
	
	private DatabasePool pool = DatabasePool.getInstance();
	
	public List<CfdRecord> queryCfdRecords(String type, long taskId, int continues) throws SQLException, TimeoutException, NotInitializedException{
		List<CfdRecord> records = new ArrayList<CfdRecord>();
		String sql = "select * from td_cfd_record where type=? and task_id=? and CONTINUE_SECONDS>=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, type);
				ps.setLong(2, taskId);
				ps.setInt(3, continues);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						CfdRecord record = new CfdRecord();
						record.setId(rs.getLong("ID"));
						record.setType(rs.getString("TYPE"));
						record.setStartTime(rs.getTimestamp("START_TIME"));
						record.setEndTime(rs.getTimestamp("END_TIME"));
						record.setContinus(rs.getInt("CONTINUE_SECONDS"));
						records.add(record);
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
		return records;
	}
	
	public List<CfdRecord> queryCfdRecords(String type, long taskId) throws SQLException, TimeoutException, NotInitializedException{
		List<CfdRecord> records = new ArrayList<CfdRecord>();
		String sql = "select * from td_cfd_record where type=? and task_id=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, type);
				ps.setLong(2, taskId);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						CfdRecord record = new CfdRecord();
						record.setId(rs.getLong("ID"));
						record.setType(rs.getString("TYPE"));
						record.setStartTime(rs.getTimestamp("START_TIME"));
						record.setEndTime(rs.getTimestamp("END_TIME"));
						record.setContinus(rs.getInt("CONTINUE_SECONDS"));
						records.add(record);
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
		return records;
	}
	
	public void insertBatch(List<CfdRecord> datas)
			throws TimeoutException, NotInitializedException, SQLException {

		String sql = "insert into td_cfd_record(type,start_time,end_time,continue_seconds,task_id) values(?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				for (CfdRecord data : datas) {
					ps.setString(1, data.getType());
					ps.setTimestamp(2, new Timestamp(data.getStartTime().getTime()));
					ps.setTimestamp(3, new Timestamp(data.getEndTime().getTime()));
					ps.setInt(4, data.getContinus());
					ps.setLong(5, data.getTaskId());
					ps.addBatch();
				}
				ps.executeBatch();
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
