package com.lpc.learn.nio.netty.webSocket.server.handler.initializer;

import com.lpc.learn.nio.netty.webSocket.server.handler.WebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/9/24
 * @Time: 15:27
 * @Description:
 */
public class WebSocketHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 这个支持 http 的编解码
        socketChannel.pipeline()
                .addLast("http-decoder", new HttpServerCodec());
        // 将1个 HTTP 的请求的多个部分合并成一个完整的 http 消息
        socketChannel.pipeline()
                .addLast("http-aggregator", new HttpObjectAggregator(65536));
        socketChannel.pipeline()
                .addLast("chunked-handler", new ChunkedWriteHandler());
        socketChannel.pipeline()
                .addLast("webSocket-handler", new WebSocketHandler());
    }
}
