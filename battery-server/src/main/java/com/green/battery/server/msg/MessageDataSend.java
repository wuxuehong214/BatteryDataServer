package com.green.battery.server.msg;


/**
 * 数据包上传命令：
 * 一帧完整的数据包含
 * 密钥代号、记录游标
 * （游标包含的信息为当前记录为第多少条记录，条数有可能已经超过了最大记录条数，
 * 则发送设备与服务器均需要考虑如何忽略那些未被读取就已经被覆盖了的数据）、
 * 密文；
 * @author Administrator
 *
 */
public class MessageDataSend extends AbstractMessage {
	
	private int index;     //秘钥代号
	private int cursor;  //记录游标
	private byte[] data;   //数据密文

	@Override
	public byte getRequestType() {
		return TYPE_DATA_SEND;
	}

	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public int getCursor() {
		return cursor;
	}


	public void setCursor(int cursor) {
		this.cursor = cursor;
	}


	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
