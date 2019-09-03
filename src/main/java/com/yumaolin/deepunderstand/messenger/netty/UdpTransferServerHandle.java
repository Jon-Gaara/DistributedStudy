package com.yumaolin.deepunderstand.messenger.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

public class UdpTransferServerHandle extends SimpleChannelInboundHandler<DatagramPacket>{

	private static final String[] DICTIONDRY = {"美国队长","钢铁侠","绿巨人","雷神"};
	
	private String nextQuote(){
		int quoteId = ThreadLocalRandom.current().nextInt(DICTIONDRY.length);
		return DICTIONDRY[quoteId];
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket pocket) throws Exception {
		String req = pocket.content().toString(CharsetUtil.UTF_8);
		System.out.println(req);
		if(("漫威人物").equals(req)){
			ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("结果:"+nextQuote(),CharsetUtil.UTF_8), pocket.sender()));
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		ctx.close();
		cause.printStackTrace();
	}
}
