package com.yumaolin.deepunderstand.netty;

import com.yumaolin.deepunderstand.messenger.netty.TimeServerHandle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;

/** 
 *
 * @author yuml
 * @since 2019年9月20日
 */
public class TimeServerHandleTest {

	public static void main(String[] args) {
		EmbeddedChannel channel = new EmbeddedChannel(new TimeServerHandle());
		channel.writeInbound("QUERY TIME ORDER");
		ByteBuf msg = channel.readOutbound();
		byte[] bytes = new byte[msg.readableBytes()];
		msg.getBytes(0, bytes);
		System.out.println(new String(bytes));
	}
}
