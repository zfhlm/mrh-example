package org.lushen.mrh.example.netty.http.server.netty;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * http server
 * 
 * @author hlm
 */
public class HttpServer implements Closeable, Runnable {

	private final Log log = LogFactory.getLog(getClass().getSimpleName());

	private EventLoopGroup parentGroup;						//IO监听线程组

	private EventLoopGroup childGroup;						//IO处理线程组

	private final HttpServerConfig serverConfig;			//server配置

	private final List<HttpFilter> serverFilters;			//server过滤器

	private final HttpBusinessHandler businessHandler;		//server业务处理器

	public HttpServer(HttpServerConfig serverConfig, List<HttpFilter> serverFilters, HttpBusinessHandler businessHandler) {
		super();
		this.serverConfig = serverConfig;
		this.serverFilters = serverFilters;
		this.businessHandler = businessHandler;
	}

	@Override
	public void run() {

		try {

			//初始化IO线程组和工作线程组
			this.parentGroup = new NioEventLoopGroup(this.serverConfig.getAcceptors());
			this.childGroup = new NioEventLoopGroup(this.serverConfig.getWorkers());

			//初始化代理
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(this.parentGroup, this.childGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.option(ChannelOption.SO_BACKLOG, 128);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.handler(new LoggingHandler(LogLevel.DEBUG));
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(new HttpServerCodec());
					channel.pipeline().addLast(new HttpObjectAggregator(65536));
					channel.pipeline().addLast(new HttpFilterHandler(serverFilters));
					channel.pipeline().addLast(businessHandler);
				}
			});
			ChannelFuture future = bootstrap.bind(this.serverConfig.getPort()).sync();
			future.channel().closeFuture().sync();

		} catch (Throwable e) {

			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);

		} finally {

			//异常处理
			this.childGroup.shutdownGracefully();
			this.parentGroup.shutdownGracefully();

		}

	}

	@Override
	public void close() throws IOException {
		if(this.childGroup != null && ! this.childGroup.isTerminated()) {
			this.childGroup.shutdownGracefully();
		}
		if(this.parentGroup != null && ! this.parentGroup.isTerminated()) {
			this.parentGroup.shutdownGracefully();
		}
	}

}
