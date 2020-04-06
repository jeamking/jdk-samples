package com.demo.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.demo.socket.BIOClient.ClientThread;

public class BIOServer {
	public static void main(String[] args) throws IOException {
		// TODO 服务端处理客户端连接请求
		ServerSocket serverSocket = new ServerSocket(3333);
		// 接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理
		int count = 1;
		while (true) {
			try {
				// 阻塞方法获取新的连接
				Socket socket = serverSocket.accept();
				// 每一个新的连接都创建一个线程，负责读取数据
				Thread thread = new Thread(new BIOServer().new ServerThread(
						socket));
				thread.setName("server" + count);
				count++;
				new Thread(thread).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class ServerThread implements Runnable {
		private Socket socket;

		public ServerThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				int len;
				byte[] data = new byte[1024];
				InputStream inputStream = socket.getInputStream();
				// 按字节流方式读取数据
				while ((len = inputStream.read(data)) != -1) {
					System.out.println(new String(data, 0, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
