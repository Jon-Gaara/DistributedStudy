package com.yumaolin.deepunderstand.messenger.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/** 
 *
 * @author yuml
 * @since 2019年3月1日
 */
public class TimeServer {
	
	public void bind(int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap sbs = new ServerBootstrap();
			sbs.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());
			//绑定端口，同步等待成功
			ChannelFuture future = sbs.bind(port).sync();
			//等到服务端监听端口关闭
			future.channel().closeFuture().sync();
		}finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			//ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			ch.pipeline().addLast(new FixedLengthFrameDecoder(16));
			/*ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
			ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));*/
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new TimeserverHandle());
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		int post = 9001;
		new TimeServer().bind(post);
	}
}
