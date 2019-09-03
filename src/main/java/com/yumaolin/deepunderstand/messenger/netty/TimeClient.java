package com.yumaolin.deepunderstand.messenger.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;

/** 
 *
 * @author yuml
 * @since 2019年3月1日
 */
public class TimeClient {
	
	public void connect(int port,String host) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap strap = new Bootstrap();
			strap.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true).handler(
			new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//ch.pipeline().addLast(new LineBasedFrameDecoder(1024));//以'\n''\r\n'结尾为一条完整数据
					ch.pipeline().addLast(new FixedLengthFrameDecoder(16));//以固定长度为一条完整数据
					/*ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));//根据特殊符号为一条完整数据*/
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new TimeClientHandle());
				}
			});
			ChannelFuture future = strap.connect(host, port).sync();
			future.channel().closeFuture().sync();
		}finally {
			Future<?> future = group.shutdownGracefully();
			future.syncUninterruptibly();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new TimeClient().connect(9001,"127.0.0.1");
	}
}
