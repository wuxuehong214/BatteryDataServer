package com.green.power.data.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;

import com.green.power.data.db.DatabasePool;
import com.green.power.data.db.NotInitializedException;
import com.green.power.data.entity.BatteryInfoEntity;

/**
 * 电池信息表 操作
 * @author Administrator
 *
 */
public class BatteryInfoDao {

	public DatabasePool pool = DatabasePool.getInstance();

	/**
	 * 新增电池信息
	 * @param battery
	 * @throws TimeoutException
	 * @throws NotInitializedException
	 * @throws SQLException
	 */
	public void addBatteryInfo(BatteryInfoEntity battery) throws TimeoutException,
			NotInitializedException, SQLException {
		String sql = "insert into td_battery_info(serial_num,manufacture_name,name,chem,manufacture_date) values"
				+ "(?,?,?,?,?)";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, battery.getSerialNum());
				ps.setString(2, battery.getManufacturerName());
				ps.setString(3, battery.getBatteryName());
				ps.setString(4, battery.getBatteryChemID());
				ps.setTimestamp(5, new Timestamp(battery.getManufactureDate().getTime()));
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
	
	public BatteryInfoEntity queryBatteryInfo(String serialNum) throws TimeoutException, NotInitializedException, SQLException{
		String	sql = "select * from td_battery_info where serial_num=?";
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
						BatteryInfoEntity battery = new BatteryInfoEntity();
						battery.setId(rs.getInt("id"));
						battery.setSerialNum(rs.getString("serial_num"));
						battery.setManufacturerName(rs.getString("manufacture_name"));
						battery.setBatteryName(rs.getString("name"));
						battery.setBatteryChemID(rs.getString("chem"));
						battery.setManufactureDate(rs.getTimestamp("manufacture_date"));
						return battery;
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
	 * 更新电池信息
	 * @param battery
	 * @throws NotInitializedException 
	 * @throws TimeoutException 
	 * @throws SQLException 
	 */
	public void updateBatteryInfo(BatteryInfoEntity battery) throws TimeoutException, NotInitializedException, SQLException{
		String	sql = "update td_battery_info set manufacture_name=?, name=?,chem=?,manufacture_date=? where serial_num=?";
		Connection conn = pool.getConnection();
		java.sql.PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			try {
				ps.setString(1, battery.getManufacturerName());
				ps.setString(2, battery.getBatteryName());
				ps.setString(3, battery.getBatteryChemID());
				ps.setTimestamp(4, new Timestamp(battery.getManufactureDate().getTime()));
				ps.setString(5, battery.getSerialNum());
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
