package com.lpc.learn.nio.netty.server.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/18
 * @Time: 16:15
 * @Description:
 */
@Slf4j
public class TimeHandler extends ChannelInboundHandlerAdapter {

    private int count = 0;
    String serverStr = "1234567890";


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count++;
        String reqBody = (String) msg;
        log.error("收到请求,count:{},body:{}", count, reqBody);
        String respBody = "当前时间" + String.valueOf(System.currentTimeMillis()+serverStr);
        ByteBuf resp = Unpooled.copiedBuffer(respBody.getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接关闭");
        super.channelInactive(ctx);
    }
}
