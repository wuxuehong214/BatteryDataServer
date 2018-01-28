package com.green.battery.server.codec;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.green.battery.server.msg.AbstractMessage;
import com.green.battery.server.msg.MessageRecordCursorResponse;
import com.green.battery.server.msg.MessageStatusValidateResponse;

/**
 * 协议编码器
 * @author wuxuehong
 *
 * @date 2012-9-10
 */
public class MyMessageEncoder implements MessageEncoder<AbstractMessage> {
	
	private Logger logger = Logger.getLogger(MyMessageEncoder.class);

	public MyMessageEncoder(Charset charset) {
	}

	
	public void encode(IoSession session, AbstractMessage message,
			ProtocolEncoderOutput out) throws Exception {
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		AbstractMessage msg = (AbstractMessage)message;
		byte key = msg.getRequestType();
		switch (key) {
		case AbstractMessage.TYPE_RECORD_RESPONSE:
			MessageRecordCursorResponse mrr=(MessageRecordCursorResponse)message;
			buf.put(mrr.getData());
			buf.flip();
			out.write(buf);
			break;
		case AbstractMessage.TYPE_STATUS_RESPONSE:
			MessageStatusValidateResponse msvr = (MessageStatusValidateResponse)message;
			buf.put(msvr.getMsg().getBytes());
			buf.flip();
			out.write(buf);
			break;
		default:
			logger.warn("Unkown responsed messages!");
			break;
		}
	}

}
