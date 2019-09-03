package com.yumaolin.deepunderstand.nosql.redis;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.params.SetParams;

/** 
 * jedis操作redis
 * @author yuml
 * @since 2019年8月15日
 */
public class RedisForJedis {
	
	private static JedisSentinelPool jedisPool;
	
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(25);
		config.setMaxIdle(20);
		config.setMinIdle(5);
		Set<String> sentinels = new HashSet<>();
		sentinels.add("cache01-center.lycheepay.org:26379");
		sentinels.add("cache02-center.lycheepay.org:26379");
		sentinels.add("cache03-center.lycheepay.org:26379");
		jedisPool = new JedisSentinelPool("mymaster", sentinels, config);
	}
	
	/**
	 * <p>获取jdeis</p>
	 * @return
	 */
	public static Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}
	
	/**
	 * <p>加锁</p>
	 * @param jedis
	 * @param lockKey
	 * @param lockValue
	 * @param timeOut
	 * @return
	 */
	public static boolean acquireLock(Jedis jedis,String lockKey,String lockValue,int timeOut) {
		SetParams setParams = new SetParams();
		setParams.px(timeOut);
		setParams.nx();
		String result = jedis.set(lockKey,lockValue,setParams);
		if("OK".equals(result)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * <p>释放锁</p>
	 * @param jedis
	 * @param lockKey
	 * @param lockValue
	 * @return
	 */
	public static boolean releaseLock(Jedis jedis,String lockKey,String lockValue) {
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Long result = (Long)jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockValue));
		System.out.println("result :"+result);
		if(result != null && result > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Jedis jedis = RedisForJedis.getJedis();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		boolean flag = RedisForJedis.acquireLock(jedis, "redis-lock", uuid, 1000000);
		System.out.println(flag);
		Thread.sleep(3000);
		flag = RedisForJedis.releaseLock(jedis, "redis-lock", uuid);
		System.out.println(flag);
	}
}
