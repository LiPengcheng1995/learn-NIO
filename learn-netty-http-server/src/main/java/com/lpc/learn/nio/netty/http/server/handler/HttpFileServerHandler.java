package com.lpc.learn.nio.netty.http.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2020/9/23
 * @Time: 11:20
 * @Description:
 */
@Slf4j
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String TEST_FILE_URI = "/testFile.txt";
    private static final String TEST_FILE_PATH = "/Users/lipengcheng3/IdeaProjects/learn-NIO/learn-netty-http-server/src/main/resources/static/nb.jpg";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        String uri = fullHttpRequest.uri();
        HttpHeaders headers = fullHttpRequest.headers();
        HttpMethod method = fullHttpRequest.method();
        ByteBuf content = fullHttpRequest.content();
        log.info("收到 http 请求，入参 method:{},uri:{},headers:{}", method.asciiName(), uri, headers.toString());

        if (!TEST_FILE_URI.equalsIgnoreCase(uri)) {
            String message = "uri和要下载的文件不匹配，直接返回!";
            log.info(message);
            sendMessage(channelHandlerContext, message);
            return;
        }
        sendFile(channelHandlerContext,TEST_FILE_PATH);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        // TODO 这里需要手动关闭，否则会一直保持连接
        ctx.close();
    }

    private void sendMessage(ChannelHandlerContext ctx, String message) {
        HttpResponse response = buildCommonResponse(message);
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_JSON);
        ctx.write(response);
    }

    private void sendFile(ChannelHandlerContext ctx, String filePath) {
        File originFile;
        RandomAccessFile randomAccessFile;
        ChunkedFile chunkedFile;
        long length;
        try {
            originFile = new File(filePath);
            randomAccessFile = new RandomAccessFile(originFile, "r");// 以只读的方式打开文件
            length = randomAccessFile.length();
            chunkedFile = new ChunkedFile(randomAccessFile, 0,
                    length, 1024);
        } catch (Throwable throwable) {
            String message = "文件发送失败，文件路径无效";
            log.error(message, throwable);
            sendMessage(ctx, message);
            return;
        }
        HttpResponse response = buildCommonResponse();
        response.headers().add(HttpHeaders.Names.CONTENT_LENGTH, length);
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE, new MimetypesFileTypeMap().getContentType(originFile));
        response.headers().add(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        // TODO 让他下载
        response.headers().add("Content-Disposition", "attachment");

        ctx.write(response);
        ctx.write(chunkedFile, ctx.newProgressivePromise());
    }

    private HttpResponse buildCommonResponse() {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        return response;
    }

    private HttpResponse buildCommonResponse(String message) {
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
        return response;
    }
}
