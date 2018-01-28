package com.green.battery.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 电池信息实体
 * @author Administrator
 *
 */
@Entity
@Table(name = "green_battery")
public class BatteryEntity {
	
	private long id;
	private String serial;
	private int cursors;
	private Date register;    //注册时间
	private Date inserts;        //第一次发送数据时间
	private Date updates;     //数据更新时间
	private String manufacturer;  //制造商
	private String dcxh; //电池型号
	private String dxxh; //电芯型号
	private long dcdy;  //电池电压  mV
	private long dcrl; //电池容量     mAh
	private String remark; //备注信息
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name="serial")
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	@Column(name="cursors")
	public int getCursors() {
		return cursors;
	}
	public void setCursors(int cursors) {
		this.cursors = cursors;
	}
	
	@Column(name="register")
	public Date getRegister() {
		return register;
	}
	public void setRegister(Date register) {
		this.register = register;
	}
	
	@Column(name="inserts")
	public Date getInserts() {
		return inserts;
	}
	public void setInserts(Date inserts) {
		this.inserts = inserts;
	}
	@Column(name="updates")
	public Date getUpdates() {
		return updates;
	}
	public void setUpdates(Date updates) {
		this.updates = updates;
	}
	@Column(name="manufacturer")
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	@Column(name="dcxh")
	public String getDcxh() {
		return dcxh;
	}
	public void setDcxh(String dcxh) {
		this.dcxh = dcxh;
	}
	@Column(name="dxxh")
	public String getDxxh() {
		return dxxh;
	}
	public void setDxxh(String dxxh) {
		this.dxxh = dxxh;
	}
	@Column(name="dcdy")
	public long getDcdy() {
		return dcdy;
	}
	public void setDcdy(long dcdy) {
		this.dcdy = dcdy;
	}
	@Column(name="dcrl")
	public long getDcrl() {
		return dcrl;
	}
	public void setDcrl(long dcrl) {
		this.dcrl = dcrl;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
