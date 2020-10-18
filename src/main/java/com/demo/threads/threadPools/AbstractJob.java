package com.demo.threads.threadPools;

public abstract class AbstractJob {
	public abstract void execute(Integer normalJobId);
	public void destroy(Integer normalJobId) {}
}
