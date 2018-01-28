package com.green.battery.server.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.green.battery.server.dao.BatteryRecordDao;
import com.mchange.lang.ByteUtils;

@Entity
@Table(name = "green_battery_record")
public class BatteryRecordEntity {

	private long id;
	private long bid;  //所属电池ID
	private int indexes; // 记录游标
	private Date timestp; // 时间戳
	private int type; // 记录类型 //u8
	private int zdy; // 总电压 u16 2mv mV
	private int scdl; // 输出电流 u16 4ma mA
	private float wd; // 电池包温度 u16 -0.1 C
	private long state; // 电池状态 u32
	private String dxdy; // 电芯电压
	private Date savetime;  //存储时间
	// private int[] dy; // 电芯电压 23 个 u16 -1mv

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static BatteryRecordEntity convet(byte[] data) {
		if (data == null || data.length != 64)
			return null;
		BatteryRecordEntity record = new BatteryRecordEntity();

		int year = ByteUtils.toUnsigned(data[0]) + 2000;
		int month = ByteUtils.toUnsigned(data[1]);
		int day = ByteUtils.toUnsigned(data[2]);
		int hour = ByteUtils.toUnsigned(data[3]);
		int minute = ByteUtils.toUnsigned(data[4]);
		int second = ByteUtils.toUnsigned(data[5]);
		String d = year + "-" + month + "-" + day + " " + hour + ":" + minute
				+ ":" + second;
		try {
			record.setTimestp(sdf.parse(d));
		} catch (Exception e) {
			e.printStackTrace();
		}

		record.setType(ByteUtils.toUnsigned(data[6]));
		record.setZdy(bytes2Ushort(data[7], data[8]) * 2);
		record.setScdl(bytes2Sshort(data[9], data[10]) * 4);
		record.setWd(bytes2Sshort(data[11], data[12]) * 0.1f);
		record.setState(bytes2Long(data[13], data[14], data[15], data[16]));

		StringBuffer dys = new StringBuffer();
		int[] dy = new int[23];
		for (int i = 0; i < 23; i++) {
			dy[i] = bytes2Ushort(data[i * 2 + 17], data[i * 2 + 18]);
			
			if(i==0)
				dys.append(dy[i]);
			else
				dys.append("-"+dy[i]);
		}
		record.setDxdy(dys.toString());
		
		record.setSavetime(new Date());
		return record;
	}

	@Override
	public String toString() {
		return "RecordEntity :\n时间戳:" + sdf.format(timestp) + ",  \n记录类型:"
				+ typeStr(type) + "\n总电压:" + zdy + "mV\n输出电流:" + scdl
				+ "mA\n温度:" + wd + "C\n状态" + state + "\n电芯电压:"
				+ dxdy + "";
	}

	/**
	 * 小端
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	private static short bytes2Sshort(byte b1, byte b2) {
		return (short) ((b2 << 8) & 0xff00 | b1 & 0x00ff);
	}

	private static int bytes2Ushort(byte b1, byte b2) {
		return ((b2 << 8) & 0xff00 | b1 & 0x00ff);
	}

	private static long bytes2Long(byte b1, byte b2, byte b3, byte b4) {
		long r = (b4 << 24 & 0xff000000) | (b3 << 16 & 0xff0000)
				| (b2 << 8 & 0xff00) | b1;
		return r & 0x00ffffffff;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="bid")
	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	@Column(name = "indexes")
	public int getIndexes() {
		return indexes;
	}

	public void setIndexes(int indexes) {
		this.indexes = indexes;
	}

	@Column(name = "timestp")
	public Date getTimestp() {
		return timestp;
	}

	public void setTimestp(Date timestp) {
		this.timestp = timestp;
	}

	@Column(name = "dxdy")
	public String getDxdy() {
		return dxdy;
	}

	public void setDxdy(String dxdy) {
		this.dxdy = dxdy;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "zdy")
	public int getZdy() {
		return zdy;
	}

	public void setZdy(int zdy) {
		this.zdy = zdy;
	}

	@Column(name = "scdl")
	public int getScdl() {
		return scdl;
	}

	public void setScdl(int scdl) {
		this.scdl = scdl;
	}

	@Column(name = "wd")
	public float getWd() {
		return wd;
	}

	public void setWd(float wd) {
		this.wd = wd;
	}

	@Column(name = "state")
	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	@Column(name="savetime")
	public Date getSavetime() {
		return savetime;
	}

	public void setSavetime(Date savetime) {
		this.savetime = savetime;
	}

	private static String typeStr(int type) {
		switch (type) {
		case 0:
			return "常规记录";
		case 1:
			return "过充保护发生";
		case 2:
			return "过充保护释放";
		case 3:
			return "欠压保护发生";
		case 4:
			return "欠压保护释放";
		case 5:
			return "过流保护发生";
		case 6:
			return "过流保护释放";
		case 7:
			return "短路保护发生";
		case 8:
			return "短路保护释放";
		case 9:
			return "放电过温保护发生";
		case 10:
			return "放电过温保护释放";
		case 11:
			return "放电欠温保护发生";
		case 12:
			return "放电欠温保护释放";
		case 13:
			return "充电过温保护发生";
		case 14:
			return "充电过温保护释放";
		case 15:
			return "充电欠温保护发生";
		case 16:
			return "开机事件";
		case 17:
			return "关机事件";
		default:
			return "未知";
		}
	}

	public static void main(String args[]) throws FileNotFoundException {
		File file = new File("g:/GREENWAY.GWR");
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[64];
		int len = -1;
		BatteryRecordDao dao = new BatteryRecordDao();
		int cursor = 1;
		List<BatteryRecordEntity> records = new ArrayList<BatteryRecordEntity>();
		try {
			int count = 0;
			while ((len = is.read(buffer)) != -1) {
				System.out.println(len + ":" + convert(buffer));
				if (len == 64) {
					BatteryRecordEntity tmp = BatteryRecordEntity.convet(buffer);
					tmp.setIndexes(cursor++);
					records.add(tmp);
				} else {

				}
				System.out.println();
				count++;
			}
			System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			dao.addBatchBatteryRecords(records);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(bytes2Sshort((byte)0x30, (byte)0x80));
		// System.out.println(bytes2Ushort((byte)0x30, (byte)0x80));
	}

	public static String convert(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (byte bb : b) {
			sb.append(ByteUtils.toUnsigned(bb) + " ");
		}
		return sb.toString();
	}
}
