package com.demo.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class BIOClient {
	public static void main(String[] args) {
		// TODO 创建多个线程，模拟多个客户端连接服务端
		for (int i=1;i<10;i++) {
			Thread thread = new Thread(new BIOClient().new ClientThread());
			thread.setName("clinet"+i);
			thread.start();
		}
	}
	
	class ClientThread implements Runnable {
		@Override
		public void run() {
			try {
				Socket socket = new Socket("127.0.0.1", 3333);
				while (true) {
					try {
						socket.getOutputStream().write(
								(Thread.currentThread().getName() + ":" 
						  + new Date() + ": hello world").getBytes());
						Thread.sleep(2000);
					} catch (Exception e) {
					}
				}
			} catch (IOException e) {
			}
		}
	}	
}
