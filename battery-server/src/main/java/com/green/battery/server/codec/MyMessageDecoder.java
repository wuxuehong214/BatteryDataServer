package com.green.battery.server.codec;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.apache.mina.proxy.utils.ByteUtilities;
import org.apache.mina.util.Base64;
import org.hibernate.util.BytesHelper;

import com.green.battery.server.config.ResourceDefined;
import com.green.battery.server.model.BatteryRecordEntity;
import com.green.battery.server.msg.MessageDataSend;
import com.green.battery.server.msg.MessageRecordCursorRequest;
import com.green.battery.server.utils.KeyUtils;
import com.green.battery.server.utils.TEAUtils;
import com.mchange.lang.ByteUtils;

/**
 * decoder
 * 
 * @author wuxuehong
 * 
 * @date 2012-9-10
 */
public class MyMessageDecoder implements MessageDecoder {

	private Logger logger = Logger.getLogger(MyMessageDecoder.class);

	public static boolean showAviBag = false;

	public MyMessageDecoder() {
	}

	public MyMessageDecoder(Charset charset) {
	}

	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		return MessageDecoderResult.OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		
		int len = in.limit();
		byte[] data = new byte[len];
		in.get(data);
		
		//首先：看该连接是否包含电池信息
		Object attach = session.getAttribute(ResourceDefined.SESSION_VALIDATION);
		logger.info("【原始数据信息】是否已验证:"+(attach!=null));
		
		if(attach == null){
			//如果不包含电池信息 那么它的第一条信息必须得是  存储游标请求信息
			String msg = new String(data);
			logger.info("【原始数据信息】需要身份认证信息,当前接收信息:"+ msg);
			if (msg.startsWith("Request")) {
				logger.info("【原始数据信息】是身份认证及游标交换信息，继续下一步处理!");
				boolean r = processRecordRequest(msg, out, session);
				if(!r){
					logger.info("【原始数据信息】身份认证及游标交换信息处理失败，关闭客户端连接！");
					session.close(true);
				}
				return MessageDecoderResult.OK;
			}else{
				logger.info("【原始数据信息】未知身份认证信息，关闭客户端连接!");
				session.close(true);
				return MessageDecoderResult.OK;
			}
		}else{
			logger.info("【原始数据信息】需要传输的数据信息,当前接收到的信息:"+new String(data));
			byte[] d = Base64.decodeBase64(data);
			
			
			if(d.length == 1028){
				logger.info("【原始数据信息】接收到传输的数据长度为1028正常,进行数据解析!");
				boolean r = processDataSend(d, out, session);
				if(!r){
					logger.info("【原始数据信息】传输数据解析处理异常，关闭客户端连接！");
					session.close(true);
				}
				return MessageDecoderResult.OK;
			}else if(d.length < 1028){
				logger.info("【原始数据信息】接收到传输的数据长度不足1028,当前接收长度["+d.length+"]继续接收更多数据!");
				in.position(0);
				in.put(data);
				in.flip();
				return MessageDecoderResult.NEED_DATA;
			}else{
				logger.info("【原始数据信息】接收到传输的数据长度大于1028, 关闭客户端连接!");
				session.close(true);
				return MessageDecoderResult.OK;
			}
		}
	}

	/**
	 * 处理 请求存储游标 命令 //Request Record Index=688DA14PS0001+1432
	 * 
	 * @param msg
	 * @param out
	 * @param session
	 */
	public boolean processRecordRequest(String msg, ProtocolDecoderOutput out,
			IoSession session) {
		try {
			int index1 = msg.indexOf('=');
			int index2 = msg.indexOf('+');
			int index3 = msg.indexOf(',');
			if (index1 != -1 && index2 != -1 && index3 != -1) {
				String serialNum = msg.substring(index1 + 1, index2);
				String index = msg.substring(index2 + 1,index3);
				String status = msg.substring(index3+1);
				logger.info("【身份认证和游标交换解析】电池序列号:"+serialNum);
				logger.info("【身份认证和游标交换解析】记录次数:"+index);
				logger.info("【身份认证和游标交换解析】反馈认证信息:"+status);
				MessageRecordCursorRequest request = new MessageRecordCursorRequest();
				request.setSerialNum(serialNum);
				request.setRecords(Integer.parseInt(index));
				request.setStatus(status.trim());
				out.write(request);
				return true;
			} else{
				logger.warn("【身份认证和游标交换】无效认证反馈:" + msg);
				return false;
			}
		} catch (Exception e) {
			logger.warn("【身份认证和游标交换】处理异常:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 处理 请求发送数据命令
	 * 
	 * @param data
	 * @param out
	 * @param session
	 */
	public boolean processDataSend(byte[] d, ProtocolDecoderOutput out,
			IoSession session) {
		try {
				int index = ByteUtils.toUnsigned(d[0]); // 密钥代号
				int cursor = (d[3] << 16 & 0x00ff0000)
						| (d[2] << 8 & 0x0000ff00) | (d[1] & 0x000000ff); // 记录游标
				logger.info("【数据传输解析】密钥代号:"+index);
				byte[] dd = Arrays.copyOfRange(d, 4, d.length); // 数据密文
				logger.info("【数据传输解析】记录游标:"+ByteUtilities.asHex(new byte[]{d[1],d[2],d[3]}," "));
				logger.info("【数据传输解析】记录游标整型值:"+cursor);
				logger.info("【数据传输解析】数据密文:"+ ByteUtilities.asHex(dd, " "));
				MessageDataSend msg = new MessageDataSend();
				msg.setIndex(index);
				msg.setCursor(cursor);
				msg.setData(dd);
				out.write(msg);
				return true;
		} catch (Exception e) {
			logger.info("【数据传输解析】异常:"+e.getMessage());
			return false;
		}

	}

	public static void main(String args[]) {

		String str = "rbp2AwQaGgcICQoLDA0ODxAREhMaFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDEyMzQ1Njc4OTo7PD0+P0BBQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWltcXV5fYGFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6e3x9fn+AgYKDhIWGh4iJiouMjY6PkJGSk5SVlpeYmZqbnJ2en6ChoqOkpaanqKmqq6ytrq+wsbKztLW2t7i5uru8vb6/wMHCw8TFxsfIycrLzM3Oz9DR0tPU1dbX2Nna29zd3t/g4eLj5OXm5+jp6uvs7e7v8PHy8/T19vf4+fr7/P14eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHg=";

		byte[] data = str.getBytes();
		System.out.println(str.length());
		byte[] d = Base64.decodeBase64(data);
		
		int index = ByteUtils.toUnsigned(d[0]); // 密钥代号
		int cursor = (d[3] << 16 & 0x00ff0000)
				| (d[2] << 8 & 0x0000ff00) | (d[1] & 0x000000ff); // 记录游标
		System.out
				.println("Base64 decode after:" + ByteUtilities.asHex(d, " "));
		System.out.println("index:"+index);
		System.out.println("Cursor:"+cursor);
		byte[] dd = Arrays.copyOfRange(d, 4, d.length); // 数据密文
		System.out.println(ByteUtilities.asHex(dd, " "));
		
		byte[] record = Arrays.copyOfRange(dd, 0, 64);
		System.out.println(record.length);
		System.out.println(ByteUtilities.asHex(record," "));
		System.out.println(Arrays.toString(record));
		
		byte[] bd = TEAUtils.decrypt(record, KeyUtils.getKey(index));
		System.out.println("解密后:"+ByteUtilities.asHex(bd, " "));
		System.out.println(Arrays.toString(bd));
		
		BatteryRecordEntity r = BatteryRecordEntity.convet(bd);
		System.out.println(r.toString());
		
		int test = (0x01 << 8 & 0x00ff0000);
		
		System.out.println(test);
				
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
	}

	/**
	 * byte arrays to str
	 * 
	 * @param ip
	 * @return
	 */
	public String ip2Str(byte[] ip) {
		if (ip == null || ip.length != 4)
			return "";
		String r;
		r = "" + (ip[0] & 0xff) + "." + (ip[1] & 0xff) + "." + (ip[2] & 0xff)
				+ "." + (ip[3] & 0xff);
		return r;
	}
}
