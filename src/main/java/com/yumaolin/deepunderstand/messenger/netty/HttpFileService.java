package com.yumaolin.deepunderstand.messenger.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileService {
	
	private static final String DEFAULT_URL = "/src/";
	
	/**
	 * @param port
	 * @param url
	 */
	public void run(final int port,final String url){
		EventLoopGroup boosGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(boosGroup, workerGroup)
			.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
	                ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
	                ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
	                ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
					ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandle(url));
				}
			});
			ChannelFuture future = b.bind("10.36.160.28", port).sync();
			System.out.println("HTTP文件目录服务器启动，网址是:"+"http://10.36.160.28:"+port+url);
			future.channel().closeFuture().sync();
		}catch(Exception e){
			boosGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		int port = 9172;
		new HttpFileService().run(port,DEFAULT_URL);
	}
}
