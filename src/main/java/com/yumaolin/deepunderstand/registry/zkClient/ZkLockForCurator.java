package com.yumaolin.deepunderstand.registry.zkClient;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.yumaolin.deepunderstand.common.constant.BaseConstant;

/** 
 * zk基于Curator实现分布式锁
 * @author yuml
 * @since 2019年8月15日
 */
public class ZkLockForCurator {
	
	//可重入排它锁
	private InterProcessMutex multiLock;
	
	private static volatile CuratorFramework curatorFrameworktor; 
	
	/**
	 * <p>单例获取curatorFrameworktor</p>
	 * @return
	 */
	public static CuratorFramework getCuratorFrameworktor() {
		if(curatorFrameworktor == null) {
			synchronized (curatorFrameworktor) {
				if(curatorFrameworktor == null) {
					curatorFrameworktor = CuratorFrameworkFactory
							.newClient(BaseConstant.ZK_CONNECT_List, BaseConstant.TIME_OUT, 
									BaseConstant.TIME_OUT, new ExponentialBackoffRetry(1000,3));
					curatorFrameworktor.start();
				}
			}
		}
		return curatorFrameworktor;
	}
	
	/**
	 * <p>加锁</p>
	 * @param lockName
	 * @param time
	 * @param unit
	 */
	public void acqireLock(String lockName,long time, TimeUnit unit) {
		multiLock = new InterProcessMutex(curatorFrameworktor, BaseConstant.ZK_LOCK_ROOT_PATH+lockName);
		try {
			if(unit == null) {
					multiLock.acquire();
			}else {
				multiLock.acquire(time, unit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>释放锁</p>
	 * @param lockName
	 */
	public void releaseLock(String lockName) {
		if(multiLock != null && multiLock.isAcquiredInThisProcess()) {
			try {
				multiLock.release();
				curatorFrameworktor.delete().inBackground().forPath(BaseConstant.ZK_LOCK_ROOT_PATH+lockName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
