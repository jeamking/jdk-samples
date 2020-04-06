package com.demo.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.demo.socket.BIOClient.ClientThread;

public class BIOServer {
	public static void main(String[] args) throws IOException {
		// TODO ����˴���ͻ�����������
		ServerSocket serverSocket = new ServerSocket(3333);
		// ���յ��ͻ�����������֮��Ϊÿ���ͻ��˴���һ���µ��߳̽�����·����
		int count = 1;
		while (true) {
			try {
				// ����������ȡ�µ�����
				Socket socket = serverSocket.accept();
				// ÿһ���µ����Ӷ�����һ���̣߳������ȡ����
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
				// ���ֽ�����ʽ��ȡ����
				while ((len = inputStream.read(data)) != -1) {
					System.out.println(new String(data, 0, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
