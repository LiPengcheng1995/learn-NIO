package learn.nio.block.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2021/1/18
 * @Time: 14:41
 * @Description:
 */

public class BlockClient {

    public static void main(String[] args) throws IOException {

        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8090));

        // 2. 发送一张图片给服务端吧
        FileChannel fileChannel = FileChannel.open(Paths.get("/export/resource/1.jpeg"), StandardOpenOption.READ);

        // 3.要使用NIO，有了Channel，就必然要有Buffer，Buffer是与数据打交道的呢
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 4.读取本地文件(图片)，发送到服务器
        while (fileChannel.read(buffer) != -1) {

            // 在读之前都要切换成读模式
            buffer.flip();
            socketChannel.write(buffer);

            // 读完切换成写模式，能让管道继续读取文件的数据
            buffer.clear();
        }

        // 5. 通知服务端，写完了
        socketChannel.shutdownOutput();

        // 6. 接受服务端返回信息
        while (socketChannel.read(buffer) != -1) {

            // 在读之前都要切换成读模式
            buffer.flip();
            System.out.println(new String(buffer.array(),0,buffer.limit()));
            // 读完切换成写模式，能让管道继续读取文件的数据
            buffer.clear();
        }

        // 7. 关闭流
        fileChannel.close();
        socketChannel.close();
    }
}