package com.demo.threads.threadPools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutorʾ���������������н�����о�Ϊʲô�߳�6��7�����ִ�С�
 * 
 * ��Һã������̣߳�1
��Һã������̣߳�2
��Һã������̣߳�3
��Һã������̣߳�4
��Һã������̣߳�5
��Һã������̣߳�8
��Һã������̣߳�9
��Һã������̣߳�10
��Һã������̣߳�11
��Һã������̣߳�12
��Һã������̣߳�6
��Һã������̣߳�7

����һ�����永����ִ�����̣�

1������ͨ�� ThreadPoolExecutor ���캯�������̳߳أ�
2��ִ�� for ѭ�����ύ 12 ������Ϊʲô��12�������أ�����
3��ͨ�� threadPoolExecutor.submit �ύ Runnable �ӿ�ʵ�ֵ�ִ������
4���ύ��1������ʱ�����ڵ�ǰ�̳߳�������ִ�е�����Ϊ 0 ��С�� 5��corePoolSize ָ���������Իᴴ��һ���߳�����ִ���ύ������1��
5���ύ�� 2�� 3�� 4�� 5 �������ʱ�����ڵ�ǰ�̳߳�������ִ�е���������С�ڵ��� 5 ��corePoolSize ָ���������Ի�Ϊÿһ���ύ�����񴴽�һ���߳���ִ������
6�����ύ��6�������ʱ�����ڵ�ǰ����ִ�е���������Ϊ 5 ����Ϊÿ���߳�����ִ��ʱ��Ϊ10s�������ύ��6�������ʱ��ǰ��5���̶߳�����ִ���У�����ʱ�Ὣ��6�������ŵ� workQueue �����еȴ�ִ�У�
7������ workQueue ���еĴ�СΪ 2 �����Ըö�����Ҳ��ֻ�ܱ��� 2 ���ȴ�ִ�е��������Ե�7������Ҳ�ᱣ�浽��������У�
8�����ύ��8�������ʱ����Ϊ��ǰ�̳߳�����ִ�е���������Ϊ5��workQueue �����д洢����������Ҳ���ˣ���ʱ���жϵ�ǰ�̳߳�������ִ�е�����������Ƿ�С��10��maximumPoolSizeָ������
9�����С�� 10 ����ô�ͻ��´���һ���߳���ִ���ύ������ 8��
10��ִ�е�9,10,11,12�������ʱ��ҲҪ�жϵ�ǰ�̳߳�������ִ�е��������Ƿ�С��10��maximumPoolSizeָ���������С��10����ôҲ�������½��߳���ִ����Щ�ύ������
11����ʱ��12�������Ѿ��ύ��ϣ��� workQueue �����еĵȴ� ����6 �� ����7 ʲôʱ��ִ���أ�
12��������1ִ����Ϻ�10s�󣩣�ִ������1���̲߳�û�б����ٵ������ǻ�ȡ workQueue �е�����6��ִ�У�
13��������2ִ����Ϻ�ִ������2���߳�Ҳû�б����٣����ǻ�ȡ workQueue �е�����7��ִ�У�

ͨ���������̵ķ�����Ҳ��֪����֮ǰ��������������ԭ����ʵ���̳߳��л��߳�ִ����Ϻ󣬲����ᱻ�������٣��̳߳��лᱣ�� corePoolSize �������̣߳��� workQueue �����д���������������ύ����ʱ����ô��ͨ���̳߳������е��߳���ִ�����񣬱�����Ƶ�����̴߳��������٣������� corePoolSize С�ڵ��� maximumPoolSize �������̣߳�������ָ��ʱ�䣨keepAliveTime������л��ա�
�����и����⣬Ϊʲô���������Ҫ�ύ12�������أ�����13�������в��У�

���Կ�������13�������ύ��ʱ�򱨴��ˣ���Ϊ�̳߳����������е��߳�����Ϊ10����ǰ workQueue������Ϊ2�� �е�����Ҳ�Ѿ����ˣ����ύ��13�������ʱ�򣬾ͻ��ύʧ�ܣ����� RejectedExecutionException �쳣���������洴���������У�ͬʱ��������������Ϊ12��

 *
 */
public class ThreadPoolExecutorDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
        //�̳߳��еĺ����߳�����
        int corePoolSize = 5;
        //��ǰ�̳߳��������е�����߳�����
        int maximumPoolSize = 10;
        //���� corePoolSize �߳��������߳�������ʱ��
        long keepAliveTime = 2;
        //����Ϊʱ�䵥λ
        TimeUnit unit = TimeUnit.SECONDS;
        //�����������У����ڴ���ύ�ĵȴ�ִ������
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);
        //�����̳߳�
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        unit,
                        workQueue);

        //ѭ���ύ����
        for(int i = 0; i < 12; i++) {
            //�ύ���������
            final int index = (i + 1);
            threadPoolExecutor.submit(() -> {
                //�̴߳�ӡ���
                System.out.println("��Һã������̣߳�" + index);
                try {
                    //ģ���߳�ִ��ʱ�䣬10s
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            //ÿ�������ύ������500ms���ύ��һ���������ڱ�֤�ύ˳��
            Thread.sleep(500);
        }
	}

}
