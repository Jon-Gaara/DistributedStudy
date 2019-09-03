package com.yumaolin.deepunderstand.nosql.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.yumaolin.deepunderstand.common.constant.BaseConstant;

/** 
 * redission 实现redis分布式锁
 * @author yuml
 * @since 2019年8月15日
 */
public class RedisForRedission {
	
	public static RedissonClient getRedissonClient() {
		Config config = new Config();
		config.useSentinelServers().setTimeout(BaseConstant.TIME_OUT)
		.addSentinelAddress("redis://cache01-center.lycheepay.org:26379",
				"redis://cache02-center.lycheepay.org:26379",
				"redis://cache03-center.lycheepay.org:6379")
		.setMasterName("masterName");
		/*config.useMasterSlaveServers().setTimeout(BaseConstant.TIME_OUT)
		.setMasterAddress("redis://cache01-center.lycheepay.org:26379")
		.addSlaveAddress("redis://cache02-center.lycheepay.org:26379");*/
		RedissonClient redissonClient = Redisson.create(config);
		return redissonClient;
	}
	
	public static void redisLock(RedissonClient redissonClient,Runnable runnable) throws Exception {
		Lock lock = redissonClient.getLock("myLock");
		lock.lock();
		runnable.run();
		lock.unlock();
	}
	
	public static void main(String[] args) throws Exception {
		RedissonClient redissonClient = RedisForRedission.getRedissonClient();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					RedisForRedission.redisLock(redissonClient,new Runnable() {
						@Override
						public void run() {
							System.out.println(Thread.currentThread()+" redis lock success");
							try {
								Thread.sleep(1000L);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
