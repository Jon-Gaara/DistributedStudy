package com.yumaolin.deepunderstand.messenger.netty;


import com.yumaolin.deepunderstand.registry.zkClient.ZkClientFindService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class WebSocketServerHandle extends SimpleChannelInboundHandler<Object>{

	private WebSocketServerHandshaker handshaker;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof FullHttpRequest){
			this.handleHttpRequest(ctx, (FullHttpRequest)msg);
		}else if(msg instanceof WebSocketFrame){
			this.handleWebSocketFrame(ctx, (WebSocketFrame)msg);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
		ctx.flush();
	}
	
	private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest request){
		if(!request.decoderResult().isSuccess()
				|| !"websocket".equals(request.headers().get("Upgrade"))){
			sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK));
			return;
		}
		WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
		handshaker = factory.newHandshaker(request);
		if(handshaker == null){
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		}else{
			handshaker.handshake(ctx.channel(), request);
		}
	}
	
	private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
		System.out.println("websocket :"+frame);
		//是否为关闭命令
		if(frame instanceof CloseWebSocketFrame){
			handshaker.close(ctx.channel(),(CloseWebSocketFrame)frame.retain());
			return;
		}
		//是否为ping命令
		if(frame instanceof PingWebSocketFrame){
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		//只支持文本消息，不支持二进制消息
		if(!(frame instanceof TextWebSocketFrame)){
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}
		String text = ((TextWebSocketFrame)frame).text();
		//ctx.channel().write(new TextWebSocketFrame(text+",欢迎使用Netty websocket 服务，现在时刻:"+new Date().toString()));
		ctx.channel().write(new TextWebSocketFrame(ZkClientFindService.findServices(text)));
	}
	
	private void sendHttpResponse(ChannelHandlerContext ctx,FullHttpRequest request,FullHttpResponse response){
		if(response.status().code() != 200){
			ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(),CharsetUtil.UTF_8);
			response.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(response, response.content().readableBytes());
		}
		
		ChannelFuture future = ctx.channel().writeAndFlush(response);
		if(!HttpUtil.isKeepAlive(response)
				|| response.status().code() != 200){
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable able){
		able.printStackTrace();
		ctx.close();
	}
}
