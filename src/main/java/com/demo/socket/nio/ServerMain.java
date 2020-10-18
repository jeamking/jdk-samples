package com.demo.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		// 创建选择器
		Selector selector = Selector.open();
		// 打开监听信道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// 与本地端口绑定
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		// 设置为非阻塞模式
		serverSocketChannel.configureBlocking(false);
		// 将选择器绑定到监听信道,只有非阻塞信道才可以注册选择器.并在注册过程中指出该信道可以进行Accept操作
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			// 等待某信道就绪
			// selector.select()会一直阻塞到有一个通道在你注册的事件上就绪了
			// selector.select(1000)会阻塞到1s后然后接着执行，相当于1s轮询检查		
			int selectInt = selector.select(1000);
			if (selectInt == 0) {
				continue;				
			}
			// 取得迭代器.selectedKeys()中包含了每个准备好某一I/O操作的信道的SelectionKey
			Iterator<SelectionKey> iterator = selector.selectedKeys()
					.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				// 有客户端连接请求时
				if (selectionKey.isAcceptable()) {
					handleAccept(selectionKey);					
				}
				// 从客户端读取数据
				if (selectionKey.isReadable()) {
					handleRead(selectionKey);					
				}
				// 向客户端发送数据
				if (selectionKey.isWritable()) {
					handleWrite(selectionKey);					
				}
				iterator.remove();
			}
		}
	}

	public static void handleAccept(SelectionKey selectionKey) throws Exception {
		System.out.println("handleAccept=");
		// 返回创建此键的通道，接受客户端建立连接的请求，并返回 SocketChannel 对象
		ServerSocketChannel ServerSocketChannel = (ServerSocketChannel) selectionKey
				.channel();
		SocketChannel clientChannel = ServerSocketChannel.accept();
		// 非阻塞式
		clientChannel.configureBlocking(false);
		// 注册到selector
		clientChannel.register(selectionKey.selector(), SelectionKey.OP_READ);

		doWrite(clientChannel, "server.handleAccept.doWrite");
	}

	public static void handleRead(SelectionKey key) throws Exception {
		System.out.println("handleRead=");
		SocketChannel channel = (SocketChannel) key.channel();
		String msg = "";
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (channel.read(buffer) > 0) {
			buffer.flip();
			while (buffer.hasRemaining())
				msg += new String(buffer.get(new byte[buffer.limit()]).array());
			buffer.clear();
		}

		System.err.println("收到客户端消息:" + msg);

		for (int i=1;i<=10;i++) {
			doWrite(channel, "server.handleRead.doWrite" + i);
			Thread.sleep(5000);
		}
	}

	public static void handleWrite(SelectionKey key) throws IOException {
		System.out.println("handleWrite=");
		SocketChannel channel = (SocketChannel) key.channel();
		String msg = "服务端发送信息!";
		channel.write(ByteBuffer.wrap(msg.getBytes()));
	}

	public static void doWrite(SocketChannel clientChannel, String msg)
			throws Exception {
		System.out.println("doWrite=" + msg);
		// 向客户端发送连接成功信息
		clientChannel.write(ByteBuffer.wrap(msg.getBytes()));
	}

}
