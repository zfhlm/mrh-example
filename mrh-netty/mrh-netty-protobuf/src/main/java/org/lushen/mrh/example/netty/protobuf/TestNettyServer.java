package org.lushen.mrh.example.netty.protobuf;

import org.lushen.mrh.example.netty.protobuf.UserModel.User;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestNettyServer {

	public static void main(String[] args) throws Exception {

		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();

		try {

			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(parentGroup, childGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 512);
			bootstrap.handler(new LoggingHandler(LogLevel.INFO));
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(new ProtobufDecoder(UserModel.User.getDefaultInstance()));
					ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
					ch.pipeline().addLast(new ProtobufEncoder());
					ch.pipeline().addLast(new PrintHandler());
				}
			});
			bootstrap.bind(8888).sync().channel().closeFuture().sync();

		} finally {

			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();

		}

	}

	private static class PrintHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

			User param = (User) msg;
			System.out.println(param.getId());
			System.out.println(param.getName());

			UserModel.User bean = UserModel.User.newBuilder().setId(2).setName("I'm server answer.").build();
			ctx.writeAndFlush(bean);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			UserModel.User bean = UserModel.User.newBuilder().setId(2).setName("server error: " + cause.getMessage()).build();
			ctx.write(bean);
			ctx.flush();
			ctx.close();
		}

	}

}
