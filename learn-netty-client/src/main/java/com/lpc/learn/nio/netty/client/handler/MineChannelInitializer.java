package com.lpc.learn.nio.netty.client.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/18
 * @Time: 17:10
 * @Description:
 */
public class MineChannelInitializer extends ChannelInitializer<SocketChannel> {
    byte[] clientBytes = "1234567890".getBytes();


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
//                .addLast(new LineBasedFrameDecoder(1024))
                .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(clientBytes)))
                .addLast(new StringDecoder())
                .addLast(new TimeClientHandler());

    }
}