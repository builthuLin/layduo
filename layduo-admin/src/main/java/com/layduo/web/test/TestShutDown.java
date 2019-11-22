package com.layduo.web.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
* @author layduo
* @createTime 2019年11月15日 上午10:28:12
*/

/**
 *  一、线程池： 提供一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，提高了响应的速度。

	二、线程池的体系结构：
	java.util.concurrent.Executor 负责线程的使用和调度的根接口
			|--ExecutorService 子接口： 线程池的主要接口
					|--ThreadPoolExecutor 线程池的实现类
					|--ScheduledExceutorService 子接口： 负责线程的调度
						|--ScheduledThreadPoolExecutor : 继承ThreadPoolExecutor，实现了ScheduledExecutorService
				
	
	三、工具类 ： Executors
	ExecutorService newFixedThreadPool() : 创建固定大小的线程池
	ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
	ExecutorService newSingleThreadExecutor() : 创建单个线程池。 线程池中只有一个线程
	
	ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务
 *
 */
public class TestShutDown {
	
	public static Runnable getTask(int threadNo) {
		final Random random = new Random();
		final int no = threadNo;
		
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				try {
                    System.out.println(no + "-->" + random.nextInt(10) + "-->" + Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("thread " + no + " has error: " + e);
                }
			}
		};		
		return task;
	}
	
	/**
	 * shutDown后可以使用awaitTermination等待所有线程执行完毕当前任务。
	 * @param startNo
	 * @throws InterruptedException
	 */
	public static void testShutDown(int startNo) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 5; i++) {
			executorService.execute(getTask(i + startNo));
		}
		//shutdown后不能submit任务，awaitTermination后可以submit，shutdown不阻塞，awaitTermination阻塞
		executorService.shutdown();
		//然后返回true（shutdown请求后所有已提交任务执行完毕）或false（已超时）
		boolean flag = executorService.awaitTermination(120, TimeUnit.SECONDS);
		System.out.println(flag == true ? "all task finished" : "timeout");
		System.out.println("shutDown->all thread shutdown");
	}
	
	/**
	 * shutDownNow就会迫使当前执行的所有任务停止工作。
	 * @param startNo
	 * @throws InterruptedException
	 */
	public static void testShutDownNow(int startNo) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 5; i++) {
			executorService.execute(getTask(i + startNo));
		}
		executorService.shutdownNow();
		executorService.awaitTermination(120, TimeUnit.SECONDS);
		System.out.println("shutDownNow->all thread shutdown");
	}
	
	public static void main(String[] args) {
		try {
			testShutDown(100);
			testShutDownNow(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
