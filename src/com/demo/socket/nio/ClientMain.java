package com.demo.socket.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ClientMain {

	public static void main(String[] args) throws Exception {
		// ����ѡ����
		Selector selector = Selector.open();
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("localhost", 9999));
		socketChannel.register(selector, SelectionKey.OP_CONNECT);

		while (true) {
			// selector.select()��һֱ��������һ��ͨ������ע����¼��Ͼ�����
			// selector.select(1000)��������1s��Ȼ�����ִ�У��൱��1s��ѯ���				
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

		System.err.println("�յ��������Ϣ:" + msg);

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
		// ��ͻ��˷������ӳɹ���Ϣ
		clientChannel.write(ByteBuffer.wrap(msg.getBytes()));
	}
}
