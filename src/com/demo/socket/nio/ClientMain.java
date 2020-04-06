package com.demo.socket.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ClientMain {

	public static void main(String[] args) throws Exception {
		// 创建选择器
		Selector selector = Selector.open();
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("localhost", 9999));
		socketChannel.register(selector, SelectionKey.OP_CONNECT);

		while (true) {
			// selector.select()会一直阻塞到有一个通道在你注册的事件上就绪了
			// selector.select(1000)会阻塞到1s后然后接着执行，相当于1s轮询检查				
			int selectInt = selector.select(1000);
			if (selectInt == 0)
				continue;
			Iterator<SelectionKey> iterator = selector.selectedKeys()
					.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				if (key.isConnectable()){
					handleConnect(key);					
				}
				if (key.isReadable()) {
					handleRead(key);					
				}
				if (key.isWritable()) {
					handleWrite(key);					
				}
				iterator.remove();
			}
		}
	}

	public static void handleConnect(SelectionKey key) throws Exception {
		SocketChannel channel = (SocketChannel) key.channel();
		if (channel.isConnectionPending())
			channel.finishConnect();
		channel.configureBlocking(false);
		channel.register(key.selector(), SelectionKey.OP_READ);
		System.out.println("handleConnect=");

		doWrite(channel, "client.handleConnect.doWrite");
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

		System.err.println("收到服务端消息:" + msg);

	}

	public static void handleWrite(SelectionKey key) throws Exception {
		System.out.println("handleWrite=");
		SocketChannel clientChannel = (SocketChannel) key.channel();
		String msg = "handleWrite=";		
		clientChannel.write(ByteBuffer.wrap(msg.getBytes()));
	}

	public static void doWrite(SocketChannel clientChannel, String msg)
			throws Exception {
		System.out.println("doWrite=");
		// 向客户端发送连接成功信息
		clientChannel.write(ByteBuffer.wrap(msg.getBytes()));
	}
}
