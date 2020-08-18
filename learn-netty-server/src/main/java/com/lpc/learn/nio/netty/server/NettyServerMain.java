package com.lpc.learn.nio.netty.server;

import com.lpc.learn.nio.netty.server.handler.MineChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/8/14
 * @Time: 17:14
 * @Description:
 */
@Slf4j
public class NettyServerMain {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new MineChannelInitializer());
            // 启动
            ChannelFuture future = bootstrap.bind(8081).sync();
            // 等待服务监听端口关闭
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error("服务运行失败");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
