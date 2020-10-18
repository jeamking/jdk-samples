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
		// ����ѡ����
		Selector selector = Selector.open();
		// �򿪼����ŵ�
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// �뱾�ض˿ڰ�
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		// ����Ϊ������ģʽ
		serverSocketChannel.configureBlocking(false);
		// ��ѡ�����󶨵������ŵ�,ֻ�з������ŵ��ſ���ע��ѡ����.����ע�������ָ�����ŵ����Խ���Accept����
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			// �ȴ�ĳ�ŵ�����
			// selector.select()��һֱ��������һ��ͨ������ע����¼��Ͼ�����
			// selector.select(1000)��������1s��Ȼ�����ִ�У��൱��1s��ѯ���		
			int selectInt = selector.select(1000);
			if (selectInt == 0) {
				continue;				
			}
			// ȡ�õ�����.selectedKeys()�а�����ÿ��׼����ĳһI/O�������ŵ���SelectionKey
			Iterator<SelectionKey> iterator = selector.selectedKeys()
					.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				// �пͻ�����������ʱ
				if (selectionKey.isAcceptable()) {
					handleAccept(selectionKey);					
				}
				// �ӿͻ��˶�ȡ����
				if (selectionKey.isReadable()) {
					handleRead(selectionKey);					
				}
				// ��ͻ��˷�������
				if (selectionKey.isWritable()) {
					handleWrite(selectionKey);					
				}
				iterator.remove();
			}
		}
	}

	public static void handleAccept(SelectionKey selectionKey) throws Exception {
		System.out.println("handleAccept=");
		// ���ش����˼���ͨ�������ܿͻ��˽������ӵ����󣬲����� SocketChannel ����
		ServerSocketChannel ServerSocketChannel = (ServerSocketChannel) selectionKey
				.channel();
		SocketChannel clientChannel = ServerSocketChannel.accept();
		// ������ʽ
		clientChannel.configureBlocking(false);
		// ע�ᵽselector
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

		System.err.println("�յ��ͻ�����Ϣ:" + msg);

		for (int i=1;i<=10;i++) {
			doWrite(channel, "server.handleRead.doWrite" + i);
			Thread.sleep(5000);
		}
	}

	public static void handleWrite(SelectionKey key) throws IOException {
		System.out.println("handleWrite=");
		SocketChannel channel = (SocketChannel) key.channel();
		String msg = "����˷�����Ϣ!";
		channel.write(ByteBuffer.wrap(msg.getBytes()));
	}

	public static void doWrite(SocketChannel clientChannel, String msg)
			throws Exception {
		System.out.println("doWrite=" + msg);
		// ��ͻ��˷������ӳɹ���Ϣ
		clientChannel.write(ByteBuffer.wrap(msg.getBytes()));
	}

}
