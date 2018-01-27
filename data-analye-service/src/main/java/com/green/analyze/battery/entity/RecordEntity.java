package com.green.analyze.battery.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.mchange.lang.ByteUtils;

public class RecordEntity {

	private int year; // u8
	private int month; // u8
	private int day; // u8
	private int hour; // u8
	private int minute; // u8
	private int second; // u8
	private int type; // 记录类型 //u8
	private int zdy; // 总电压 u16 2mv
	private int scdl; // 输出电流 u16 2ma
	private float wd; // 电池包温度 u16 -0.1
	private long state; // 电池状态 u32
	private int[] dy; // 电芯电压 23 个 u16 -1mv
	
//	public static RecordEntity convert(File f){
//		
//	}
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public Date getDate(){
		String d = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		try {
			return sdf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static RecordEntity convet(byte[] data){
		if(data == null || data.length != 64) return null;
		RecordEntity record = new RecordEntity();
		record.setYear(ByteUtils.toUnsigned(data[0])+2000);
		record.setMonth(ByteUtils.toUnsigned(data[1]));
		record.setDay(ByteUtils.toUnsigned(data[2]));
		record.setHour(ByteUtils.toUnsigned(data[3]));
		record.setMinute(ByteUtils.toUnsigned(data[4]));
		record.setSecond(ByteUtils.toUnsigned(data[5]));
		
		record.setType(ByteUtils.toUnsigned(data[6]));
		record.setZdy(bytes2Ushort(data[7],data[8])*2);
		record.setScdl(bytes2Sshort(data[9], data[10])*4);
		record.setWd(bytes2Sshort(data[11], data[12])*0.1f);
		record.setState(bytes2Long(data[13], data[14], data[15], data[16]));
		
		int[] dy  = new int[23];
		for(int i=0;i<23;i++){
			dy[i] = bytes2Ushort(data[i*2+17], data[i*2+18]);
		}
		record.setDy(dy);
		return record;
	}
	
	
	@Override
	public String toString() {
		return "RecordEntity :\n时间戳:" + year + "-" + month + "-"
				+ day + " " + hour + ":" + minute + ":"
				+ second + ",  \n记录类型:" + typeStr(type) + "\n总电压:" + zdy + "mV\n输出电流:" + scdl
				+ "mA\n温度:" + wd + "C\n状态" + state + "\n电芯电压:"
				+ Arrays.toString(dy) + "";
	}


	/**
	 * 小端
	 * @param b1
	 * @param b2
	 * @return
	 */
	private static short bytes2Sshort(byte b1,byte b2){
		return (short) ((b2<<8)&0xff00|b1&0x00ff);
	}
	
	private static int bytes2Ushort(byte b1,byte b2){
		return ((b2<<8)&0xff00|b1&0x00ff);
	}
	
//	private static int bytes2SInt(byte b1,byte b2){
//		return (b2<<8)&0xff00|b1&0x0ff;
//	}
	
	private static long bytes2Long(byte b1,byte b2,byte b3,byte b4){
		long r =  (b4<<24 & 0xff000000)|(b3<<16&0xff0000)|(b2<<8&0xff00)|b1;
		return r&0x00ffffffff;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getZdy() {
		return zdy;
	}

	public void setZdy(int zdy) {
		this.zdy = zdy;
	}

	public int getScdl() {
		return scdl;
	}

	public void setScdl(int scdl) {
		this.scdl = scdl;
	}

	public float getWd() {
		return wd;
	}

	public void setWd(float wd) {
		this.wd = wd;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public int[] getDy() {
		return dy;
	}

	public void setDy(int[] dy) {
		this.dy = dy;
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
	
	
	public static void main(String args[]) throws FileNotFoundException{
		File file = new File("g:/GREENWAY.GWR");
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[64];
		int len = -1;
		try {
			int count = 0;
			while((len = is.read(buffer))!=-1){
				System.out.println(len+":"+convert(buffer));
				if(len == 64){
						System.out.println(RecordEntity.convet(buffer).toString());
				}else{
					
				}
				System.out.println();
				count++;
			}
			System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(bytes2Sshort((byte)0x30, (byte)0x80));
//		System.out.println(bytes2Ushort((byte)0x30, (byte)0x80));
	}
	
	public static String convert(byte[] b){
		StringBuffer sb = new StringBuffer();
		for(byte bb:b){
			sb.append(ByteUtils.toUnsigned(bb)+" ");
		}
		return sb.toString();
	}
}
