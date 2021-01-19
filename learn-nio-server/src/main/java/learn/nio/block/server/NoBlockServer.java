package learn.nio.block.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * @author: 李鹏程
 * @email: lipengcheng3@jd.com
 * @date: 2021/1/18
 * @Time: 15:09
 * @Description: 依靠 NIO 的 Selector 实现了非阻塞的调用，但是从实际执行上来说，轮询线程和处理线程没有做到分离，和 BIO 差不太多。
 * 另外，存在读半包的操作。可以看一下。
 */
// TODO 客户端的SocketChannel支持 OP_CONNECT, OP_READ, OP_WRITE三个操作。
// TODO 服务端ServerSocketChannel只支持OP_ACCEPT操作，在服务端由ServerSocketChannel的accept()方法产生的SocketChannel只支持OP_READ, OP_WRITE操作。
public class NoBlockServer {

    public static void main(String[] args) throws IOException {

        // 1.获取通道
        ServerSocketChannel server = ServerSocketChannel.open();

        // 2.切换成非阻塞模式
        server.configureBlocking(false);

        // 3. 绑定连接
        server.bind(new InetSocketAddress(8090));

        // 4. 获取选择器
        Selector selector = Selector.open();

        // 4.1将通道注册到选择器上，指定接收“监听通道”事件
        server.register(selector, SelectionKey.OP_ACCEPT);

        // 5. 轮训地获取选择器上已“就绪”的事件--->只要select()>0，说明已就绪
        while (selector.select() > 0) {
            // 6. 获取当前选择器所有注册的“选择键”(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            // 7. 获取已“就绪”的事件，(不同的事件做不同的事)
            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();

                // 接收事件就绪
                if (selectionKey.isAcceptable()) {

                    // 8. 获取客户端的链接
                    SocketChannel client = server.accept();

                    // 8.1 切换成非阻塞状态
                    client.configureBlocking(false);

                    // 8.2 注册到选择器上-->拿到客户端的连接为了读取通道的数据(监听读就绪事件)
                    client.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) { // 读事件就绪

                    // 9. 获取当前选择器读就绪状态的通道
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    // 9.1读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    // 9.2得到文件通道，将客户端传递过来的图片写到本地项目下(写模式、没有则创建)
                    // TODO 可以使用注释行来验证一下读半包的操作
//                    FileChannel outChannel = FileChannel.open(Paths.get("/export/resource/"+System.currentTimeMillis()+".jpeg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                    FileChannel outChannel = FileChannel.open(Paths.get("/export/resource/2.jpeg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

                    while (client.read(buffer) > 0) {
                        // 在读之前都要切换成读模式
                        buffer.flip();
                        outChannel.write(buffer);

                        // 读完切换成写模式，能让管道继续读取文件的数据
                        buffer.clear();
                    }

                    // 9.3 写返回结果
                    buffer.put("server have received the data".getBytes());
                    buffer.flip();
                    client.write(buffer);
                    buffer.clear();
                }
                // 10. 取消选择键(已经处理过的事件，就应该取消掉了)
                iterator.remove();
            }
        }

    }
}
