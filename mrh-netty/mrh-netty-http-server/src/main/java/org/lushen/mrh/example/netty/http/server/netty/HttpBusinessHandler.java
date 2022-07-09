package org.lushen.mrh.example.netty.http.server.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * http server resolver handler
 * 
 * @author hlm
 */
@Sharable
public abstract class HttpBusinessHandler extends ChannelInboundHandlerAdapter {

	protected final Log log = LogFactory.getLog(getClass().getSimpleName());

}
