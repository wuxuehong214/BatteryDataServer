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
import com.green.battery.entity.YlwRecord;

/**
 * 电池详细记录
 * 
 * @author Administrator
 * 
 */
public class YlwRecordDao {

	private DatabasePool pool = DatabasePool.getInstance();

	/**
	 * 查询电压、电流、温度历史记录
	 * @return
	 * @throws SQLException
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 */
	public List<YlwRecord> queryRecords(long taskid) throws SQLException, TimeoutException, NotInitializedException{
		List<YlwRecord> list = new ArrayList<YlwRecord>();
		String sql = "select * from td_ylw where TASK_ID=? order by sjc";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, taskid);
			try {
				rs = ps.executeQuery();
				try {
					while (rs.next()){
						YlwRecord record = new YlwRecord();
						record.setId(rs.getLong("ID"));
						record.setDy(rs.getInt("DY"));
						record.setDl(rs.getInt("DL"));
						record.setWd(rs.getFloat("WD"));
						record.setSjc(rs.getTimestamp("SJC"));
						record.setTaskId(rs.getLong("TASK_ID"));
						list.add(record);
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
		return list;
	}
	/**
	 * 批量插入
	 * @param datas
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public void insertBatch(List<YlwRecord> datas)
			throws TimeoutException, NotInitializedException, SQLException {

		String sql = "insert into td_ylw(dy,dl,wd,sjc,task_id) values(?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				for (YlwRecord data : datas) {
					ps.setInt(1, data.getDy());
					ps.setInt(2, data.getDl());
					ps.setFloat(3, data.getWd());
					ps.setTimestamp(4, new Timestamp(data.getSjc()
							.getTime()));
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
