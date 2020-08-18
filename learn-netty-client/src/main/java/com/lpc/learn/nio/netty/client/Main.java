package com.lpc.learn.nio.netty.client;

import com.lpc.learn.nio.netty.client.handler.MineChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/14
 * @Time: 17:14
 * @Description:
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new MineChannelInitializer());
            // 启动
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8081).sync();

            // 等待服务监听端口关闭
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error("服务运行失败");
        } finally {
            group.shutdownGracefully();
        }
    }
}
