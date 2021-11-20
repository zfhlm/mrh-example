package org.lushen.mrh.example.netty.http.server.boot;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.lushen.mrh.example.netty.http.server.netty.HttpBusinessHandler;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

@Component
public class DemoBusinessHandler extends HttpBusinessHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		// 读取请求信息
		if(msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			HttpMethod method = request.method();
			String uri = request.uri();
			log.info(String.format("Http %s : %s", method, uri));
			if(msg instanceof FullHttpRequest) {
				FullHttpRequest fullHttpRequest = (FullHttpRequest)msg;
				ByteBuf in = fullHttpRequest.content();
				try {
					log.info("body : " + new String(ByteBufUtil.getBytes(in)));
				} finally {
					in.release();
				}
			}
		}

		// 随机出错
		if(new Random().nextInt(2) == 0) {
			throw new RuntimeException("出错了!");
		}

		// 输出响应信息
		ByteBuf buf = Unpooled.wrappedBuffer("success!".getBytes());
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(CONTENT_LENGTH, buf.readableBytes());
		response.headers().set(CONTENT_TYPE, "text/*;charset=utf-8");
		ctx.writeAndFlush(response);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		// 读取异常信息
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bos);
		cause.printStackTrace(out);
		out.flush();
		out.close();
		byte[] buffer = bos.toByteArray();

		// 输出异常信息
		ByteBuf buf = Unpooled.wrappedBuffer(buffer);
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(CONTENT_TYPE, "text/*;charset=utf-8");
		response.headers().set(CONTENT_LENGTH, buf.readableBytes());
		ctx.writeAndFlush(response);
		ctx.close();

	}

}
