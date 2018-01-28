package com.green.battery.server.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.green.battery.server.config.ResourceDefined;
import com.green.battery.server.model.BatteryEntity;
import com.green.battery.server.model.BatteryRecordEntity;
import com.green.battery.server.msg.AbstractMessage;
import com.green.battery.server.msg.MessageDataSend;
import com.green.battery.server.msg.MessageRecordCursorRequest;
import com.green.battery.server.msg.MessageRecordCursorResponse;
import com.green.battery.server.msg.MessageStatusValidateResponse;
import com.green.battery.server.service.BatteryDataService;
import com.green.battery.server.service.BatteryService;
import com.green.battery.server.utils.KeyUtils;
import com.green.battery.server.utils.TEAUtils;

/**
 * sever handler
 * 
 * deal with all the logic operations
 * 
 * @author wuxuehong
 * 
 *         2012-5-3
 */
public class ServerHandler extends IoHandlerAdapter {

	private Logger logger = Logger.getLogger(ServerHandler.class);
	private BatteryService batteryService; // 电池服务
	private BatteryDataService dataService; // 数据服务

	/**
	 * 构造函数
	 * 
	 * @param dbService
	 */
	public ServerHandler() {
		batteryService = new BatteryService();
		dataService = new BatteryDataService();
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("【会话处理】有客户端连接成功!");

		// 连接上来了 则发送身份验证信息

		MessageStatusValidateResponse msvr = MessageStatusValidateResponse
				.generage();
		session.write(msvr);
		logger.info("【会话处理】给客户端发送身份验证请求并保存身份验证请求到会话:" + msvr.getMsg());
		// logger.info("Send status validation msg:"+msvr.getMsg());
		// logger.info("Status decoded:"+msvr.getStatusDecoded());
		session.setAttribute(ResourceDefined.SESSION_STATUS_VALIDATION, msvr);

	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		AbstractMessage msg = (AbstractMessage) message;
		byte requestType = msg.getRequestType();
		switch (requestType) {
		// report the ip of the device
		case AbstractMessage.TYPE_RECORD_REQUEST: // 请求获取电池游标记录
			processRecordRequest(session, msg);
			break;
		case AbstractMessage.TYPE_DATA_SEND: // 请求发送数据
			processDataSend(session, msg);
			break;
		default:
			logger.warn("Unknown message request type:" + requestType);
			break;
		}
	}

	/**
	 * 处理记录数请求
	 * 
	 * @param session
	 * @param message
	 */
	public void processRecordRequest(IoSession session, AbstractMessage message) {
		MessageRecordCursorRequest request = (MessageRecordCursorRequest) message;

		// 验证身份验证信息
		Object attach = session
				.getAttribute(ResourceDefined.SESSION_STATUS_VALIDATION);

		if (attach == null) {
			logger.info("It is stranger, it will never come here !");
			session.close(true);
		}

		MessageStatusValidateResponse msvr = (MessageStatusValidateResponse) attach;
		String status_base64 = msvr.getStatusDecoded(); // 获取会话中保存的身份信息加密密文

		logger.info("【身份认证和游标交换处理】电池序列号[" + request.getSerialNum() + "]客户端身份["
				+ request.getStatus() + "]服务端身份:[" + status_base64 + "]");
		if (!status_base64.equals(request.getStatus())) {
			logger.info("【身份认证和游标交换处理】电池序列号[" + request.getSerialNum()
					+ "]身份认证失败，断开连接!");
			session.close(true);
			return;
		}

		logger.info("【身份认证和游标交换处理】电池序列号[" + request.getSerialNum()
				+ "]身份认证通过,准备反馈服务端存储游标!");

		// 身份验证通过 则反馈记录游标信息
		try {

			BatteryEntity battery = batteryService.getBattery(request
					.getSerialNum());
			int cursor = battery.getCursors();

			logger.info("【身份认证和游标交换处理】电池序列号[" + request.getSerialNum()
					+ "]当前服务端存储游标:" + cursor);

			// 如果存储游标>记录次数
			// 始存储电池信息。服务器应具备纠错功能，收到记录次
			// 数小于存储游标时，采用静默方式，不回复存储游标读
			// 取指令，直到连续收到 3 次请求，且要求收到 3 次请求
			// 中的记录次数相差在 3 以内。
			if (cursor > request.getRecords()) {
				logger.info("【身份认证和游标交换处理】电池序列号[" + request.getSerialNum()
						+ "]当前服务端存储游标:" + cursor + "大于客户端记录次数:"
						+ request.getRecords());
				// logger.info("Client records is less than server cursor!");
				Object obj = session
						.getAttribute(ResourceDefined.SESSION_RECORDS_REQUEST);
				if (obj == null) { // 第一次请求
					logger.info("【身份认证和游标交换处理】电池序列号[" + request.getSerialNum()
							+ "]服务端存储游标大于客户端记录次数需要等待三次请求，当前次数1");
					request.setCount(1);
					session.setAttribute(
							ResourceDefined.SESSION_RECORDS_REQUEST, request);
					return; // 静默
				} else {
					MessageRecordCursorRequest r = (MessageRecordCursorRequest) obj;
					if (r.getCount() < 3) { // 次数增加
						r.setCount(r.getCount() + 1);
						logger.info("【身份认证和游标交换处理】电池序列号["
								+ request.getSerialNum()
								+ "]服务端存储游标大于客户端记录次数需要等待三次请求，当前次数"
								+ r.getCount());
						return; // 静默
					} else {
						logger.info("【身份认证和游标交换处理】电池序列号["
								+ request.getSerialNum()
								+ "]服务端存储游标大于客户端记录次数，并且连续请求达到3次!");
						if (Math.abs(request.getRecords() - r.getRecords()) <= 3) {
							logger.info("【身份认证和游标交换处理】电池序列号["
									+ request.getSerialNum()
									+ "]服务端存储游标大于客户端记录次数，连续请求达到3次,并且三次记录次数相差在3内，重置存储游标0!");
							cursor = 0; // 电池数据清零了 要求从新开始
						} else {
							logger.info("【身份认证和游标交换处理】电池序列号["
									+ request.getSerialNum()
									+ "]服务端存储游标大于客户端记录次数，连续请求达到3次,单三次记录次数相差在3外，无效，断开客户端连接!");
							session.close(true);
							return; // 纠错 无效
						}
					}
				}
			}

			// 保存其请求到 会话
			// 保存身份验证通过的信息
			session.setAttribute(ResourceDefined.SESSION_VALIDATION, battery);

			MessageRecordCursorResponse response = new MessageRecordCursorResponse();
			response.setContent("Record Index=" + cursor);
			response.setData(response.getContent().getBytes());
			response.setCursor(cursor);

			session.write(response);
		} catch (Exception e) {
			logger.info("【身份认证和游标交换处理】处理异常:"+e.getMessage());
		}
	}

	/**
	 * 处理数据发送
	 * 
	 * @param session
	 * @param message
	 */
	public void processDataSend(IoSession session, AbstractMessage message) {

		Object attach = session
				.getAttribute(ResourceDefined.SESSION_VALIDATION);
		if (attach == null) {
			logger.warn("Invalid connection for it did not send record request, do not know which battery it is! close it!");
			session.close(true);
		}

		BatteryEntity battery = (BatteryEntity) attach;
		if (battery.getInserts() == null)
			battery.setInserts(new Date());

		// 获取请求的数据
		MessageDataSend send = (MessageDataSend) message;
		int index = send.getIndex(); // 密钥代号
		int cursor = send.getCursor(); // 游标
		byte[] data = send.getData(); // 数据

		// 获取数据条数 一般默认是16条一发
		int size = data.length / 64;

		logger.info("【数据传输处理】当前电池序列号["
								+ battery.getSerial()
								+ "]准备解码并解析电池记录数据同时进行存储,记录条数:"+size);
		List<BatteryRecordEntity> records = new ArrayList<BatteryRecordEntity>();
		for (int i = 0; i < size; i++) {
			byte[] tmp = Arrays.copyOfRange(data, 64 * i, 64 * i + 64); // 加密后的电池记录数据

			// 解密
			byte[] bd = TEAUtils.decrypt(tmp, KeyUtils.getKey(index));

			// 电池记录对象转换
			BatteryRecordEntity record = BatteryRecordEntity.convet(bd);
			record.setIndexes(cursor++);
			record.setBid(battery.getId());
			records.add(record);
		}

		logger.info("【数据传输处理】当前电池序列号["
				+ battery.getSerial()
				+ "]解码解析完毕准备存储...");
		try {
			dataService.addBatchBatteryRecords(records);

			battery.setUpdates(new Date()); // 更新battery信息
			battery.setCursors(cursor-1); // 更新游标
			logger.info("【数据传输处理】当前电池序列号["
					+ battery.getSerial()
					+ "]电池记录存储完毕，更新电池当前存储游标:"+battery.getCursors());
			try {
				batteryService.updateBattery(battery);
			} catch (Exception e) {
				logger.info("【数据传输处理】当前电池序列号["
						+ battery.getSerial()
						+ "]更新电池存储游标异常:"+e.getMessage());
			}
		} catch (Exception e) {
			logger.info("【数据传输处理】当前电池序列号["
					+ battery.getSerial()
					+ "]电池记录存储异常:"+e.getMessage());
		}
		session.close(true);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		String serial = "未知";
		Object attach = session
				.getAttribute(ResourceDefined.SESSION_VALIDATION);
		if(attach != null){
			BatteryEntity battery = (BatteryEntity) attach;
			serial = battery.getSerial();
		}
		logger.info("【会话处理】电池序列号["+serial+"]断开!");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		String serial = "未知";
		Object attach = session
				.getAttribute(ResourceDefined.SESSION_VALIDATION);
		if(attach != null){
			BatteryEntity battery = (BatteryEntity) attach;
			serial = battery.getSerial();
		}
		logger.info("【会话处理】电池序列号["+serial+"],20s内无数据传输，断开连接!");
		session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		String serial = "未知";
		Object attach = session
				.getAttribute(ResourceDefined.SESSION_VALIDATION);
		if(attach != null){
			BatteryEntity battery = (BatteryEntity) attach;
			serial = battery.getSerial();
		}
		logger.info("【会话处理】电池序列号["+serial+"],异常，断开连接:"+cause.getMessage());
		session.close(true);
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

	/**
	 * str to byte arrays
	 * 
	 * @param ip
	 * @return
	 */
	public byte[] str2Ip(String ip) {
		byte[] def = new byte[] { 0x00, 0x00, 0x00, 0x00 };
		try {
			if (ip == null || "".equals(ip))
				return def;
			String[] str = ip.split("\\.");
			if (str.length != 4)
				return def;
			byte[] d = new byte[4];
			for (int i = 0; i < 4; i++)
				d[i] = (byte) Integer.parseInt(str[i]);
			return d;
		} catch (Exception e) {
			return def;
		}
	}
}
