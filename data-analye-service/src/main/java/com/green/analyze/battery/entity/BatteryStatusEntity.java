package com.green.analyze.battery.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import com.mchange.lang.ByteUtils;

/**
 * 电池状态信息实体
 * @author Administrator
 *
 */
public class BatteryStatusEntity {
	
	//unit4
	private long  access;   //综合信息
	private float wd;        //温度  0.1
	private long zdy;     //总电压 1mV
	private long dl;          //电流    1mA
	private long syrl_bfb;  //剩余容量百分比
	private long jkzt_bfb;   //健康状态百分比
	private long syrl;     //剩余容量  1mAh
	private long mcrl;   //满充容量  1mAh
	private long dczt;  //电池状态
	private long xhcs;  //循环次数
	private long sjrl;   //设计容量     1mAh
	private long sjdy;  //设计电压
	private byte[] scrq; //生产日期
	private long lsh;   //流水号 
	private String serialNum; //电池序列号
	private String curTime;
	
	public String getCurTime(){
		return (nbsz[0]+2000)+"-"+(nbsz[1])+"-"+nbsz[2]+" "+nbsz[3]+":"+nbsz[4]+":"+nbsz[5];
	}
	
	public String getSerialNum(){
		if(qtscsj == null) return "Unknown";
		if(lsh<10) return qtscsj+"000"+lsh;
		else if(lsh < 100) return qtscsj+"00"+lsh;
		else if(lsh < 1000)  return qtscsj+"0"+lsh;
		return qtscsj+lsh;
	}
	
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}


	public long getSjdy() {
		return sjdy;
	}

	public void setSjdy(long sjdy) {
		this.sjdy = sjdy;
	}


	public byte[] getScrq() {
		return scrq;
	}

	public void setScrq(byte[] scrq) {
		this.scrq = scrq;
	}


	//6bytes
	private byte[] nbsz;  //内时钟  
	
	//12bytes
	private String zzsmc = "Unknown";  //制造商名称
	
	//12bytes
	private String dcxh = "Unknown";   //电池型号
	
	//12bytes
	private String dxxh = "Unknown";  //电芯型号
	
	//12bytes
	private String qtscsj;  //其他生产数据
	
	//32bytes
	private byte[]  d16dy;  //低16电压
	
	//32bytes
	private byte[]  g16dy; //高16电压
	
	//12bytes
	private byte[] jxsj;  //极限数据
	
	//12bytes
	private byte[] cwjs; //错误计数


	public long getAccess() {
		return access;
	}

	public void setAccess(long access) {
		this.access = access;
	}

	public float getWd() {
		return wd;
	}

	public void setWd(float wd) {
		this.wd = wd;
	}

	public long getZdy() {
		return zdy;
	}

	public void setZdy(long zdy) {
		this.zdy = zdy;
	}

	public long getDl() {
		return dl;
	}

	public void setDl(long dl) {
		this.dl = dl;
	}

	public long getSyrl_bfb() {
		return syrl_bfb;
	}

	public void setSyrl_bfb(long syrl_bfb) {
		this.syrl_bfb = syrl_bfb;
	}

	public long getJkzt_bfb() {
		return jkzt_bfb;
	}

	public void setJkzt_bfb(long jkzt_bfb) {
		this.jkzt_bfb = jkzt_bfb;
	}

	public long getSyrl() {
		return syrl;
	}

	public void setSyrl(long syrl) {
		this.syrl = syrl;
	}

	public long getMcrl() {
		return mcrl;
	}

	public void setMcrl(long mcrl) {
		this.mcrl = mcrl;
	}

	public long getDczt() {
		return dczt;
	}

	public void setDczt(long dczt) {
		this.dczt = dczt;
	}

	public long getXhcs() {
		return xhcs;
	}

	public void setXhcs(long xhcs) {
		this.xhcs = xhcs;
	}

	public long getSjrl() {
		return sjrl;
	}

	public void setSjrl(long sjrl) {
		this.sjrl = sjrl;
	}

	public long getLsh() {
		return lsh;
	}

	public void setLsh(long lsh) {
		this.lsh = lsh;
	}

	public byte[] getNbsz() {
		return nbsz;
	}

	public void setNbsz(byte[] nbsz) {
		this.nbsz = nbsz;
	}

	public String getZzsmc() {
		return zzsmc;
	}

	public void setZzsmc(String zzsmc) {
		this.zzsmc = zzsmc;
	}

	public String getDcxh() {
		return dcxh;
	}

	public void setDcxh(String dcxh) {
		this.dcxh = dcxh;
	}

	public String getDxxh() {
		return dxxh;
	}

	public void setDxxh(String dxxh) {
		this.dxxh = dxxh;
	}

	public String getQtscsj() {
		return qtscsj;
	}

	public void setQtscsj(String qtscsj) {
		this.qtscsj = qtscsj;
	}

	public byte[] getD16dy() {
		return d16dy;
	}

	public void setD16dy(byte[] d16dy) {
		this.d16dy = d16dy;
	}

	public byte[] getG16dy() {
		return g16dy;
	}

	public void setG16dy(byte[] g16dy) {
		this.g16dy = g16dy;
	}

	public byte[] getJxsj() {
		return jxsj;
	}

	public void setJxsj(byte[] jxsj) {
		this.jxsj = jxsj;
	}

	public byte[] getCwjs() {
		return cwjs;
	}

	public void setCwjs(byte[] cwjs) {
		this.cwjs = cwjs;
	}
	
	private static long convertUint(byte b1,byte b2,byte b3, byte b4){
		long r = ((b4<<24) & 0x00ff000000)|((b3<<16)&0x00ff0000)|((b2<<8)&0x00ff00)|(b1&0x00ff);
		long mask = 0x00000000ffffffffl;
		long r2 = r&mask;
		return r2;
		
	}
	
	private static int convertSint(byte b1,byte b2,byte b3, byte b4){
		return (b4<<24 & 0xff000000)|(b3<<16&0xff0000)|(b2<<8&0xff00)|(b1&0x00ff);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("综合信息:"+access+"\n");
		sb.append("电池温度:"+wd+"C\n");
		sb.append("电池包总电压:"+zdy+"mV\n");
		sb.append("实时电流:"+dl+"mA\n");
		sb.append("电池剩余容量百分比:"+syrl_bfb+"%\n");
		sb.append("电池健康状态百分比::"+jkzt_bfb+"%\n");
		sb.append("剩余容量:"+syrl+"mAh\n");
		sb.append("满充容量:"+mcrl+"mAh\n");
		sb.append("电池状态:"+dczt+"\n");
		sb.append("电池循环次数:"+xhcs+"\n");
		sb.append("设计容量:"+sjrl+"mA\n");
		sb.append("设计电压:"+sjdy+"mV\n");
		
		sb.append("生产日期:"+bytes2String(scrq)+"\n");
		sb.append("电池流水号:"+lsh+"\n");
		sb.append("内部时钟:"+bytes2String(nbsz)+"\n");
		sb.append("电池制造商:"+zzsmc+"\n");
		sb.append("电池型号:"+dcxh+"\n");
		sb.append("电芯型号:"+dxxh+"\n");
		sb.append("电池其他生产数据:"+qtscsj+"\n");
		sb.append("低16节电压:"+bytes2String(d16dy)+"\n");
		sb.append("高16节电压数据:"+bytes2String(g16dy)+"\n");
		sb.append("极限数据:"+bytes2String(jxsj)+"\n");
		sb.append("错误计数:"+bytes2String(cwjs)+"\n");
		return sb.toString();
	}
	
	
	public static BatteryStatusEntity convet(File f) throws IOException{
		try {
			@SuppressWarnings("resource")
			InputStream is = new FileInputStream(f);
			byte[] buffer = new byte[(0xc9&0x00ff)+1];
			is.read(buffer);
			return  convert(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private static BatteryStatusEntity convert(byte[] data){
		
		if(data.length < (0xc9&0x00ff)+1) return null;
		BatteryStatusEntity battery = new BatteryStatusEntity();
		
		battery.setAccess(convertUint(data[0x00], data[0x01], data[0x02], data[0x03]));  //综合信息
		battery.setWd((float)(convertSint(data[0x04], data[0x05], data[0x06], data[0x07])*0.1));  //电池温度
		battery.setZdy(convertUint(data[0x08],data[0x09],data[0x0a],data[0x0b]));  //电池包总电压
		battery.setDl(convertSint(data[0x0c], data[0x0d], data[0x0e], data[0x0f]));  //实时电流
		battery.setSyrl_bfb(convertUint(data[0x10], data[0x11], data[0x12], data[0x13])); //电池剩余容量百分比 
		battery.setJkzt_bfb(convertUint(data[0x14], data[0x15], data[0x16], data[0x17])); //电池状态健康比
		battery.setSyrl(convertUint(data[0x18], data[0x19], data[0x1a], data[0x1b])); //电池剩余容量
		battery.setMcrl(convertUint(data[0x1c], data[0x1d], data[0x1e], data[0x1f]));  //满充容量
		battery.setDczt(convertUint(data[0x20], data[0x21], data[0x22], data[0x23])); //电池状态
		battery.setXhcs(convertUint(data[0x24], data[0x25], data[0x26], data[0x27])); //循环次数
		battery.setSjrl(convertUint(data[0x28], data[0x29], data[0x2a], data[0x2b])); //设计容量
		
//		System.out.println(data[0x2c]+"\t"+data[0x2d]+"\t"+data[0x2e]+"\t"+data[0x2f]);
		battery.setSjdy(convertUint(data[0x2c], data[0x2d], data[0x2e], data[0x2f])); //设计电压
		battery.setScrq(new byte[]{data[0x30],data[0x31],data[0x32],data[0x33]}); //生产日期
		battery.setLsh(convertUint(data[0x34], data[0x35], data[0x36], data[0x37])); //生产序列号
		battery.setNbsz(new byte[]{data[0x38],data[0x39],data[0x3a],data[0x3b],data[0x3c],data[0x3d]}); //电池内部时钟
		
		battery.setZzsmc(new String(Arrays.copyOfRange(data, 0x3e, 0x3e+11)));   //电池制造商名称
		battery.setDcxh(new String(Arrays.copyOfRange(data, 0x4a, 0x4a+11))); //电池型号
		battery.setDxxh(new String(Arrays.copyOfRange(data, 0x56, 0x56+11))); //电芯型号
		battery.setQtscsj(new String(Arrays.copyOfRange(data, 0x63, 0x63+11))); // 其他生产数据
		battery.setD16dy(Arrays.copyOfRange(data, 0x62, 0x62+31)); //低16节电压数据
		battery.setG16dy(Arrays.copyOfRange(data, 0x62+32, 0x62+32+31)); //高16节电压数据
		battery.setJxsj(Arrays.copyOfRange(data, 0xae&0x00ff, (0xae&0x00ff)+11)); //极限数据
		battery.setCwjs(Arrays.copyOfRange(data, 0xba&0x00ff, (0xba&0x00ff)+11));
		
		
		return battery;
	}
	
	
	public static void main(String args[]) throws FileNotFoundException{
		File file = new File("g:/GREENWAY.GWI");
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[(0xc9&0x00ff)+1];
		int len = -1;
		try {
			int count = 0;
			while((len = is.read(buffer))!=-1){
				System.out.println(len+":"+convert(buffer));
				System.out.println();
				count++;
				break;
			}
			System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(ByteUtils.toUnsigned((byte)0xc9));
	}
	
	
	public static String bytes2String(byte[] b){
		if(b == null) return null;
		StringBuffer sb = new StringBuffer();
		for(byte bb:b){
			sb.append(ByteUtils.toUnsigned(bb)+" ");
		}
		return sb.toString();
	}
	

}
