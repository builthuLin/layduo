package com.layduo.web.test;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
* @author layduo
* @createTime 2019年11月19日 上午10:42:39
* 
* @resource by https://www.cnblogs.com/sunhaoyu/articles/6955923.html
*/

/**
 * 1.优势: (1)降低资源消耗。通过重复利用已创建的线程降低线程创建、销毁线程造成的消耗。
 * (2)提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。
 * (3)提高线程的可管理性。线程是稀缺资源，如果入限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配、调优和监控。
 * 
 * 2.线程池的创建 new ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,long
 * keepAliveTime, TimeUnit unit,BlockingQueue workQueue,RejectedExecutionHandler
 * handler) (1)corePoolSize： 线程池维护线程的最少数量 （core : 核心） (2)maximumPoolSize：
 * 线程池维护线程的最大数量 ，如果当线程池中的运行的线程数量到达这个数字时，新来的任务会抛出异常。 (3)keepAliveTime：
 * 线程池维护线程所允许的空闲时间 ，表示线程没有任务执行时最多能保持多少时间会停止，然后线程池维护的线程数目维持在corePoolSize。
 * (4)unit： 线程池维护线程所允许的空闲时间的单位 。 (5)workQueue：
 * 线程池所使用的阻塞队列，用来缓存等待执行的任务，如果当前对线程的需求超过了corePoolSize大小，才会放在这里。 (6)handler：
 * 如果执行线程已满，线程池对拒绝任务的处理策略。
 * 
 *
 */
public class MyThreadPoolExecutor {

	private static int produceTaskSleepTime = 1000;

	private static int consumeTaskSleepTime = 5000;

	private static int produceTaskMaxNumber = 10; // 定义最大添10个线程到线程池中

	private static int corePoolSize = 3;

	private static int maximumPoolSize = 5;

	private static long keepAliveTime = 3;

	public static void main(String[] args) throws InterruptedException {
		// 创建线程池
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), new ThreadPoolExecutor.CallerRunsPolicy());

		for (int i = 1; i <= produceTaskMaxNumber; i++) {
			try {
				// 一个任务，并将其加入到线程池
				System.out.println("===================================================");
				String work = "task@ " + i;
				System.out.println("execute : " + work);

				threadPoolExecutor.execute(new ThreadPoolTask(work, threadPoolExecutor));
				// 便于观察，等待一段时间
				Thread.sleep(produceTaskSleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		boolean flag = true;
		while (flag) {

			if (threadPoolExecutor.getQueue().size() <= 0 && threadPoolExecutor.getActiveCount() <= 0) {
				flag = false;
				System.out.println();
				System.out.println("====>线程池线程任务执行完毕");
				printlnThread(threadPoolExecutor);
				// 关闭线程池
				threadPoolExecutor.shutdown();
				// 检测没有关闭线程池，则强制关闭
				if (threadPoolExecutor.isTerminated()) {
					threadPoolExecutor.shutdownNow();
					System.out.println("========强制关闭线程池========");
				}
			}

			Thread.sleep(3000);
		}
	}

	// 线程池执行的任务
	public static class ThreadPoolTask implements Runnable, Serializable {

		private static final long serialVersionUID = 0;
		// 保存任务所需要的数据
		private Object threadPoolTaskData;
		private ThreadPoolExecutor threadPool;

		public ThreadPoolTask(Object works, ThreadPoolExecutor threadPoolExecutor) {
			this.threadPoolTaskData = works;
			this.threadPool = threadPoolExecutor;
		}

		@Override
		public void run() {

			printlnThread(threadPool);

			System.out.println();
			System.out.println("run ------> " + threadPoolTaskData + " by " + Thread.currentThread().getName());
			System.out.println("===================================================");
			try {
				// 便于观察，等待一段时间
				Thread.sleep(consumeTaskSleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			threadPoolTaskData = null;
		}

		public Object getTask() {
			return this.threadPoolTaskData;
		}
	}

	public static void printlnThread(ThreadPoolExecutor threadPool) {
		System.out.println();
		int queueSize = threadPool.getQueue().size();
		System.out.println("====>当前排队任务数：" + queueSize);

		int activeCount = threadPool.getActiveCount();
		System.out.println("====>当前活动任务数：" + activeCount);

		long completedTaskCount = threadPool.getCompletedTaskCount();
		System.out.println("====>执行完成任务数：" + completedTaskCount);

		long taskCount = threadPool.getTaskCount();
		System.out.println("====>线程池执行任务数：" + taskCount);

		// 通过这个数据可以知道线程池是否曾经满过。如该数值等于线程池的最大大小，则表示线程池曾经满过。
		int largest = threadPool.getLargestPoolSize();
		System.out.println("====>创建最大线程数：" + largest);

		System.out.println("====>线程池创建线程是否已满：" + (largest == maximumPoolSize ? "是" : "否"));

		long poolSize = threadPool.getPoolSize();
		System.out.println("====>总线程数：" + poolSize);
	}

}
