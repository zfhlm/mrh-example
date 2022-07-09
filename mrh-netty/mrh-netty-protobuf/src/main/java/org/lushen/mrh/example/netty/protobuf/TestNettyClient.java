package org.lushen.mrh.example.netty.protobuf;

import org.lushen.mrh.example.netty.protobuf.UserModel.User;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class TestNettyClient {

	public static void main(String[] args) throws Exception {

		EventLoopGroup group = new NioEventLoopGroup();

		try {

			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufDecoder(UserModel.User.getDefaultInstance()));
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(new ClientHandler());
				}
			});
			ChannelFuture future = bootstrap.connect("127.0.0.1", 8888).sync();

			Channel channel = future.channel();

			// 发送请求
			for(int i=0; i<10; i++) {

				UserModel.User msg = UserModel.User.newBuilder().setId(1).setName("I'm client.").build();
				channel.writeAndFlush(msg);

				Thread.sleep(1000L);

			}

			channel.close();

		} finally {

			group.shutdownGracefully();

		}
	}

	private static class ClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			User param = (User) msg;
			System.out.println(param.getId());
			System.out.println(param.getName());
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}

	}

}
