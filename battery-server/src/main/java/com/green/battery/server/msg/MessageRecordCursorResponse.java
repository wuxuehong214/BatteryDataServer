package com.green.battery.server.msg;

/**
 * 
 * c.存储游标返回：服务器应响应客户端的存储记录读取命令， 响应方式为将存储游标直接返回，返回格式为： “Record Index=xxxxxx”。
 * 
 * @author Administrator
 * 
 */
public class MessageRecordCursorResponse extends AbstractMessage {

	private int cursor; // 记录数 xxxxxx
	private String content; // 消息内容 Record Index=xxxxxx
	private byte[] data; // 消息内容转字节数组

	@Override
	public byte getRequestType() {
		return TYPE_RECORD_RESPONSE;
	}


	public int getCursor() {
		return cursor;
	}


	public void setCursor(int cursor) {
		this.cursor = cursor;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
