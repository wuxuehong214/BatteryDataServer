package com.green.battery.server.msg;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.proxy.utils.ByteUtilities;

/**
 * abstract message 
 * @author wuxuehong
 *
 * @date 2012-9-10
 */
public abstract class AbstractMessage {
	
	public static  final byte TYPE_RECORD_REQUEST = 0;  //请求读取游标
	public static final byte TYPE_DATA_SEND = 1;  //请求数据包上传
	public static final byte TYPE_RECORD_RESPONSE=2; //请求读取游标返回
	public static final byte TYPE_STATUS_RESPONSE = 3; //身份验证返回
	
	/**
	 * 
	 * @return
	 */
	public abstract byte getRequestType();
	
	public static void main(String args[]){
//		String str = "Request Record Index=688DA14PS0001+1432";
		IoBuffer buffer = IoBuffer.allocate(100);
		System.out.println(buffer.position()+"\t"+buffer.limit()+"\t"+buffer.capacity()+"\t"+buffer.remaining());
		buffer.put((byte)0x01);
		buffer.put((byte)0x02);
		System.out.println(buffer.position()+"\t"+buffer.limit()+"\t"+buffer.capacity()+"\t"+buffer.remaining());
		buffer.flip();
		System.out.println(buffer.position()+"\t"+buffer.limit()+"\t"+buffer.capacity()+"\t"+buffer.remaining());
		int len = buffer.limit();
		byte[] d = new byte[len];
		buffer.get(d);
		System.out.println(buffer.position()+"\t"+buffer.limit()+"\t"+buffer.capacity()+"\t"+buffer.remaining());
		buffer.position(0);
		buffer.put(new byte[]{0x01,0x02});
		System.out.println(buffer.position()+"\t"+buffer.limit()+"\t"+buffer.capacity()+"\t"+buffer.remaining());
		
		
//		byte[] datas = new byte[]{(byte)0xf3,(byte)0x3e};
//		System.out.println(ByteUtilities.asHex(datas, " "));
//		
//		 int cursor = (0x00<<16 & 0x00ff0000)|(0xff<<8 & 0x0000ff00)|(0x01&0x000000ff);  //记录游标
//		 System.out.println(cursor);
	}
}
