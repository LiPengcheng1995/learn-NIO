package com.lpc.learn.nio.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/18
 * @Time: 17:11
 * @Description:
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] queryBytes = "请求当前时间\r\n".getBytes();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i=0;i<300;i++){
            ByteBuf message= Unpooled.buffer(queryBytes.length);
            message.writeBytes(queryBytes);
            ctx.writeAndFlush(message);
            log.info("完成发送一次请求");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        log.info("收到查询结果,body:{}", body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
