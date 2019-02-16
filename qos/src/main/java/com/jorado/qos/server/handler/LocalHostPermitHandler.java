package com.jorado.qos.server.handler;

import com.jorado.qos.Constants;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class LocalHostPermitHandler extends ChannelHandlerAdapter {

    // true means to accept foreign IP
    private  boolean acceptForeignIp;

    public LocalHostPermitHandler(boolean acceptForeignIp) {
        this.acceptForeignIp = acceptForeignIp;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (!acceptForeignIp) {
            if (!((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().isLoopbackAddress()) {
                ByteBuf cb = Unpooled.wrappedBuffer((Constants.BR_STR + "Foreign Ip Not Permitted."
                        + Constants.BR_STR).getBytes());
                ctx.writeAndFlush(cb).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
