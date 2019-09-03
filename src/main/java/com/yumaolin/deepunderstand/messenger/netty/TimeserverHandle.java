package com.yumaolin.deepunderstand.messenger.netty;


import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

/** 
 *
 * @author yuml
 * @since 2019年3月1日
 */
public class TimeserverHandle implements ChannelInboundHandler{
	
	private int count;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
		/*ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");*/
		String body = (String)msg;
		System.out.println("The time server receive order:"+body+" count:"+(++count));
		String currentTime = ("QUERY TIME ORDER").equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
		//ByteBuf resp = Unpooled.copiedBuffer((currentTime+System.getProperty("line.separator")).getBytes());
		ByteBuf resp = Unpooled.copiedBuffer((currentTime).getBytes());
		ctx.write(resp);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
