package com.yumaolin.deepunderstand.messenger.netty;


import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

/** 
 * 获取当前服务时间
 * @author yuml
 * @since 2019年3月1日
 */
public class TimeServerHandle implements ChannelInboundHandler{
	
	private static final AtomicInteger count = new AtomicInteger();
	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
		System.out.println("channelRead");
		/*ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");*/
		String body = (String)msg;
		System.out.println("The time server receive order: "+body+" count: "+count.incrementAndGet());
		String currentTime = ("QUERY TIME ORDER").equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
		//ByteBuf resp = Unpooled.copiedBuffer((currentTime+System.getProperty("line.separator")).getBytes());
		ByteBuf resp = Unpooled.copiedBuffer((currentTime).getBytes());
		ctx.write(resp);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		System.out.println("channelReadComplete");
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.out.println("exceptionCaught");
		ctx.close();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerAdded");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved");
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelUnregistered");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("userEventTriggered");
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelWritabilityChanged");
	}
}
