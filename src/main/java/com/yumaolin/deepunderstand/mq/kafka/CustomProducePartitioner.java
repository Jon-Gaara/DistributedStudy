package com.yumaolin.deepunderstand.mq.kafka;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;


/**
 * 自定义生产者分区器
 * 
 * @author yuml
 * @since 2019年8月28日
 */
public class CustomProducePartitioner implements Partitioner {

	private final ConcurrentMap<String, AtomicInteger> topicCounterMap = new ConcurrentHashMap<>();

	@Override
	public void configure(Map<String, ?> configs) {
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// 获取topic下所有的partition
		List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
		int numPartitions = partitions.size();
		/**
		 * 如果key为空 就判断是否存在可用的partition 有的话就随机从可用的partition选择一个 没有的话就随机从不可用的选择一个 如果key不为空
		 * 就根据key的murmur2Hash 选择一个partition
		 */
		if (keyBytes == null) {
			// 随机获取一个int值
			int nextValue = nextValue(topic);
			// 获取可用的partition
			List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
			if (availablePartitions.size() > 0) {
				int part = Utils.toPositive(nextValue) % availablePartitions.size();
				return availablePartitions.get(part).partition();
			} else {
				// 没有可用的partition 就选择一个不可用的
				return Utils.toPositive(nextValue) % numPartitions;
			}
		} else {
			// hash the keyBytes to choose a partition
			return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
		}
	}

	private int nextValue(String topic) {
		AtomicInteger counter = topicCounterMap.get(topic);
		if (null == counter) {
			counter = new AtomicInteger(ThreadLocalRandom.current().nextInt());
			AtomicInteger currentCounter = topicCounterMap.putIfAbsent(topic, counter);
			if (currentCounter != null) {
				counter = currentCounter;
			}
		}
		return counter.getAndIncrement();
	}

	@Override
	public void close() {
	}
}
