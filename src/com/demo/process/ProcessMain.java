package com.demo.process;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ProcessMain {

	public static void main(String[] args) {
		//pingBaidu();
		//dir();
		kill();
	}
	
	private static void kill(){
		String slaveJarPath = "/homepa1/test/slave-test/7";
		String killShell = String.format("cd %s && sh restart-auto.sh user onlyKill", slaveJarPath);
		try {
			System.out.println("=====kill begin");
			Runtime run = Runtime.getRuntime();
			Process process = run.exec(new String[]{"/bin/sh","-c",killShell}, null);
			InputStream inputStream = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while((line = reader.readLine()) != null) {
				System.out.println("kill slave shell out=" + line);
			}
			System.out.println("=====kill end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private static void dir(){
		BufferedReader br = null;
		try {
			//二种方式等同
			Process p = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","dir","d:"});
			//Process p = Runtime.getRuntime().exec("cmd.exe /c dir d:");
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("====");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
	}

	private static void pingBaidu(){
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec("ping www.baidu.com");
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println("====");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
	}
}
