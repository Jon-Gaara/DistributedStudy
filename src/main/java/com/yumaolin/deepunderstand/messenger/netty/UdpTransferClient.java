package com.yumaolin.deepunderstand.messenger.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class UdpTransferClient {

	public void run(int port) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
					.handler(new UdpTransferClientHandle());
			Channel ch = b.bind(0).sync().channel();
			ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("漫威人物", CharsetUtil.UTF_8),
					new InetSocketAddress("255.255.255.255", port))).sync();
			if (!ch.closeFuture().await(15000)) {
				System.out.println("超时查询!");
			}
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int port = 8009;
		new UdpTransferClient().run(port);
	}
}
