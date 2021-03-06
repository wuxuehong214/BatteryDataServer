package com.green.battery.server.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.green.battery.server.msg.AbstractMessage;

public class MyMessageCodecFactory extends DemuxingProtocolCodecFactory {
	private MessageDecoder decoder;

	private MessageEncoder<AbstractMessage> encoder;
	// 注册编解码器
	public MyMessageCodecFactory(MessageDecoder decoder,
			MessageEncoder<AbstractMessage> encoder) {
		this.decoder = decoder;
		this.encoder = encoder;
		addMessageDecoder(this.decoder);
		addMessageEncoder(AbstractMessage.class, this.encoder);
	}
} 
