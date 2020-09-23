package com.lpc.learn.nio.netty.http.server.handler.initializer;

import com.lpc.learn.nio.netty.http.server.handler.HttpFileServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/9/23
 * @Time: 11:19
 * @Description:
 */
public class HttpServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("http-decoder", new HttpRequestDecoder());
        socketChannel.pipeline()
                .addLast("http-aggregator", new HttpObjectAggregator(65536));
        socketChannel.pipeline()
                .addLast("http-encoder", new HttpResponseEncoder());
        socketChannel.pipeline()
                .addLast("chunked-handler", new ChunkedWriteHandler());
        socketChannel.pipeline()
                .addLast("file-handler", new HttpFileServerHandler());
    }
}
