package com.green.battery.server.msg;

import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.apache.mina.proxy.utils.ByteUtilities;

import com.green.battery.server.utils.TEAUtils;
import com.mchange.lang.ByteUtils;

public class MessageStatusValidateResponse extends AbstractMessage {

	private byte[] status;  // 随机八字节
	private String msg;    //随机认证信息    :010203040A0B0C0D
	
	@Override
	public byte getRequestType() {
		// TODO Auto-generated method stub
		return TYPE_STATUS_RESPONSE;
	}

	public byte[] getStatus() {
		return status;
	}

	public void setStatus(byte[] status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	private static Random r = new Random();
	/**
	 * 生成随机身份认证信息
	 * 
	 * 身份认证信息由：信息头、随机 16 进制数组成，信息头为“:”，
	 * 随机数长度为 8 字节，采用 ASCII 码表示。
	 * 如若发送随机数的 16 进制格式为：01 02 03 04 0A 0B 0C 0D，
	 * 则信息内容为（字符串）：“:010203040A0B0C0D”
	 * @return
	 */
	public static MessageStatusValidateResponse generage(){
		MessageStatusValidateResponse msvr = new MessageStatusValidateResponse();
		StringBuffer sb = new StringBuffer();
		sb.append(":");
		byte[] status = new byte[8];
		for(int i=0;i<8;i++){
			status[i] = (byte)r.nextInt(256);
			sb.append(ByteUtils.toHexAscii(status[i]));
		}
		msvr.setStatus(status);
		msvr.setMsg(sb.toString());
		return msvr;
	}
	
	/**
	 * 身份信息加密
	 * @return
	 */
	public String getStatusDecoded(){
		//基础密钥
		byte[] basekey = new byte[]{0x64, 0x65, 0x73, 0x69, 0x67, 0x6E, 0x65, 0x64, 0x20, 0x62, 0x79, 0x20, 0x77, 0x75, 0x71, 0x69};
		//加密密钥
		//将随机数依次与基础密钥奇数字节进行异或后得到加密密钥
		byte[] encodedkey = new byte[16];
		int index = 0;
		for(int i=0;i<16;i++){
			encodedkey[i] = basekey[i];
			 if((i+1)%2 != 0){
					  encodedkey[i] = (byte) (encodedkey[i]^status[index++]);
			 }
		}
//		System.out.println(ByteUtilities.asHex(encodedkey, " "));
		// TEA 加密
		byte[] encodeddata = TEAUtils.encrypt(status, encodedkey);
		
//		System.out.println(Hex.encodeHexString(encodeddata));
		// base64编码
		String base64 = org.apache.commons.codec.binary.Base64.encodeBase64String(encodeddata);
		return base64;
	}
	
	public static void main(String args[]){
		MessageStatusValidateResponse m = MessageStatusValidateResponse.generage();
		m.setStatus(new byte[]{(byte)0xDB,0x43,0x11,0x45,(byte)0x96,(byte)0xFB,0x01,0x2A});
		System.out.println(m.getMsg());
		System.out.println(ByteUtilities.asHex(m.getStatus(), " "));
		System.out.println(m.getStatusDecoded());
	}

}
