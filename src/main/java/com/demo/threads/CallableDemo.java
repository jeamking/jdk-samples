package com.demo.threads;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Callable使用示例
 * @author Administrator
 *
 */
public class CallableDemo {

	public static void main(String[] args) {
		//start1CallableWithThread();
		//start1CallableWithExecutors();
		//start2CallableWithThread();
		start2CallableWithExecutors();
	}
	
	/**
	 * 通过Thread启动1个callable
	 */
	private static void start1CallableWithThread() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        FutureTask<String> futureTask = new FutureTask<String>(deployCallable);
        System.out.println(getDateStr() + "main thread before start");
        new Thread(futureTask).start();
        //主线程休眠，但不影响Callable的异步执行
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread after start");
        //阻塞主线程，直到Callable运行并返回结果
        String res = null;
		try {
			res = futureTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask get:" + res);
        System.out.println(getDateStr() + "main thread main() end");		
	}
	
	/**
	 * 通过Thread启动2个callable
	 */
	private static void start2CallableWithThread() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        FutureTask<String> futureTask1 = new FutureTask<String>(deployCallable);
        System.out.println(getDateStr() + "main thread before start");
        new Thread(futureTask1).start();
        //主线程休眠，但不影响Callable的异步执行
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        FutureTask<String> futureTask2 = new FutureTask<String>(deployCallable);
        new Thread(futureTask2).start();
        
        System.out.println(getDateStr() + "main thread after start");
        //阻塞主线程，直到Callable运行并返回结果
        String res1 = null;
		try {
			res1 = futureTask1.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask1 get:" + res1);
        //获取future2的结果
        String res2 = null;
		try {
			res2 = futureTask2.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask2 get:" + res2);        
        System.out.println(getDateStr() + "main thread main() end");		
	}	
	
	/**
	 * 通过线程池启动1个callable
	 */
	private static void start1CallableWithExecutors() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        System.out.println(getDateStr() + "main thread before start");
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<String> futureTask = pool.submit(deployCallable);
        //主线程休眠，但不影响Callable的异步执行
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread after start");
        //阻塞主线程，直到Callable运行并返回结果
        String res = null;
		try {
			res = futureTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        
        System.out.println(getDateStr() + "main thread futureTask get:" + res);
        System.out.println(getDateStr() + "main thread main() end");
        pool.shutdown();		
	}	
	
	/**
	 * 通过线程池启动2个callable
	 */
	private static void start2CallableWithExecutors() {
		CallableDemo callableDemo = new CallableDemo();
		DeployCallable deployCallable = callableDemo.new DeployCallable();  
        System.out.println(getDateStr() + "main thread before start");
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> futureTask1 = pool.submit(deployCallable);
        //主线程休眠，但不影响Callable的异步执行
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Future<String> futureTask2 = pool.submit(deployCallable);
        
        System.out.println(getDateStr() + "main thread after start");
        //阻塞主线程，直到Callable运行并返回结果
        String res1 = null;
		try {
			res1 = futureTask1.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask1 get:" + res1);
        
        String res2 = null;
		try {
			res2 = futureTask2.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(getDateStr() + "main thread futureTask2 get:" + res2);        
        
        System.out.println(getDateStr() + "main thread main() end");
        pool.shutdown();		
	}	
	
	private static String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime()) + " ";
	}

	class DeployCallable implements Callable<String>{
	    @Override
	    public String call() {
	        try {
	        	System.out.println(getDateStr() + Thread.currentThread().getName() + "-DeployCallable begin");
				Thread.sleep(5000);
				System.out.println(getDateStr() + Thread.currentThread().getName() + "-DeployCallable end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        return "deploy success";
	    }
	}
}
