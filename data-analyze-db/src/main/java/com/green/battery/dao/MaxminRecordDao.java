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
import com.green.battery.entity.MaxminRecord;

/**
 * 最值记录操作
 * td_maxmin_record
 * @author Administrator
 *
 */
public class MaxminRecordDao {

	private DatabasePool pool = DatabasePool.getInstance();
	
	/**
	 * 查询最值记录
	 * @param taskid
	 * @return
	 * @throws SQLException
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 */
	public List<MaxminRecord> queryRecoreds(long taskid) throws SQLException, TimeoutException, NotInitializedException{
		List<MaxminRecord> records = new ArrayList<MaxminRecord>();
		String sql = "select * from td_maxmin_record where task_id=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setLong(1, taskid);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						MaxminRecord record = new MaxminRecord();
						record.setId(rs.getLong("ID"));
						record.setType(rs.getString("TYPE"));
						record.setStartTime(rs.getTimestamp("START_TIME"));
						record.setEndTime(rs.getTimestamp("END_TIME"));
						record.setContinus(rs.getInt("CONTINUE_SECONDS"));
						record.setTaskId(rs.getLong("TASK_ID"));
						record.setValue(rs.getFloat("VALUE"));
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
	
	public void insertBatch(List<MaxminRecord> datas)
			throws TimeoutException, NotInitializedException, SQLException {

		String sql = "insert into td_maxmin_record(task_id,start_time,end_time,continue_seconds,type,value) values(?,?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				for (MaxminRecord data : datas) {
					ps.setLong(1, data.getTaskId());
					ps.setTimestamp(2, new Timestamp(data.getStartTime().getTime()));
					ps.setTimestamp(3, new Timestamp(data.getEndTime().getTime()));
					ps.setInt(4, data.getContinus());
					ps.setString(5, data.getType());
					ps.setFloat(6, data.getValue());
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
