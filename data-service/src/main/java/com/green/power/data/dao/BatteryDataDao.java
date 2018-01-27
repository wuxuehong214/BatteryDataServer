package com.green.power.data.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.green.power.data.db.DatabasePool;
import com.green.power.data.db.NotInitializedException;
import com.green.power.data.entity.BatteryDataEntity;

/**
 * 电池数据信息表管理
 * 
 * @author Administrator
 * 
 */
public class BatteryDataDao {

	private DatabasePool pool = DatabasePool.getInstance();

	/**
	 * 批量插入电池数据
	 * @param datas
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public void insertBatch(List<BatteryDataEntity> datas) throws TimeoutException,
			NotInitializedException, SQLException {

		String sql = "insert into td_battery_data(serial_num,record_time,record_cursor,data_length,client_read_time,server_read_time,data,remark1,remark2) values(?,?,?,?,?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				for (BatteryDataEntity data : datas) {
					ps.setString(1, data.getSerialNum());
					ps.setLong(2, data.getRecordTime());
					ps.setLong(3, data.getRecordCurcor());
					ps.setInt(4, data.getDataLength());
					ps.setTimestamp(5, new Timestamp(data.getClientReadTime()
							.getTime()));
					ps.setTimestamp(6, new Timestamp(data.getServerReadTime()
							.getTime()));
					ps.setString(7, data.getData());
					ps.setString(8, data.getRemark1());
					ps.setString(9, data.getRemark2());
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
	
	/**
	 * 
	 * 查询指定电池的 最后一条监控数据
	 * 
	 * @param serialNum
	 * @throws NotInitializedException 
	 * @throws TimeoutException 
	 * @throws SQLException 
	 */
	public BatteryDataEntity queryLastBatteryData(String serialNum) throws TimeoutException, NotInitializedException, SQLException{
		String	sql = "select * from td_battery_data where id=(select max(id) from td_battery_data where serial_num=?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, serialNum);
				rs = ps.executeQuery();
				try {
					if (rs.next()) {
						BatteryDataEntity data = new BatteryDataEntity();
						data.setId(rs.getLong("id"));
						data.setSerialNum(rs.getString("serial_num"));
						data.setRecordTime(rs.getLong("record_time"));
						data.setRecordCurcor(rs.getLong("record_cursor"));
						data.setDataLength(rs.getInt("data_length"));
						data.setClientReadTime(rs.getTimestamp("client_read_time"));
						data.setServerReadTime(rs.getTimestamp("server_read_time"));
						data.setData(rs.getString("data"));
						data.setRemark1(rs.getString("remark1"));
						data.setRemark2(rs.getString("remark2"));
						return data;
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
	 * 分页查询电池监控数据信息
	 * @param serialNum
	 * @param start
	 * @param size
	 * @return
	 * @throws NotInitializedException 
	 * @throws TimeoutException 
	 * @throws SQLException 
	 */
	public List<BatteryDataEntity> queryBatteryDatas(String serialNum, int start, int size) throws TimeoutException, NotInitializedException, SQLException{
		List<BatteryDataEntity> datas = new ArrayList<BatteryDataEntity>();
		String	sql = "select * from td_battery_data where serial_num=? order by id desc limit ? ";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, serialNum);
//				ps.setInt(2, start);
				ps.setInt(2, size);
				rs = ps.executeQuery();
				try {
					while (rs.next()) {
						BatteryDataEntity data = new BatteryDataEntity();
						data.setId(rs.getLong("id"));
						data.setSerialNum(rs.getString("serial_num"));
						data.setRecordTime(rs.getLong("record_time"));
						data.setRecordCurcor(rs.getLong("record_cursor"));
						data.setDataLength(rs.getInt("data_length"));
						data.setClientReadTime(rs.getTimestamp("client_read_time"));
						data.setServerReadTime(rs.getTimestamp("server_read_time"));
						data.setData(rs.getString("data"));
						data.setRemark1(rs.getString("remark1"));
						data.setRemark2(rs.getString("remark2"));
						datas.add(data);
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
		return datas;
	}
	
	/**
	 * 删除数据
	 * @param serialNum
	 * @throws SQLException 
	 * @throws NotInitializedException 
	 * @throws TimeoutException 
	 */
	public void deleteData(String serialNum) throws SQLException, TimeoutException, NotInitializedException{
		String sql = "delete from td_battery_data where serial_num=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, serialNum);
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
	 * 查询指定电池的监控数据条数
	 * @param serialNum
	 * @return
	 * @throws SQLException
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 */
	public int countData(String serialNum) throws SQLException, TimeoutException, NotInitializedException{
		String sql = "select count(1) from td_battery_data where serial_num=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, serialNum);
				ps.execute();
				rs = ps.executeQuery();
				if(rs.next()){
					return rs.getInt(1);
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

}
