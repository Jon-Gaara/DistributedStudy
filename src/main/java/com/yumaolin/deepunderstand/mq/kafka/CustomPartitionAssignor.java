package com.yumaolin.deepunderstand.mq.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignor;
import org.apache.kafka.clients.consumer.internals.PartitionAssignor;
import org.apache.kafka.common.TopicPartition;


/**
 * 自定义分区策略 kafka 默认三个分区策略 @see StickyAssignor @see RoundRobinAssignor @see RangeAssignor
 * 
 * @author yuml
 * @since 2019年9月5日
 */
public class CustomPartitionAssignor extends AbstractPartitionAssignor {

	/**
	 * 分配的策略名称
	 * 
	 * @see PartitionAssignor#name()
	 */
	@Override
	public String name() {
		return null;
	}

	/**
	 * 分区策略实现
	 * 
	 * @see AbstractPartitionAssignor#assign(java.util.Map, java.util.Map)
	 */
	@Override
	public Map<String, List<TopicPartition>> assign(Map<String, Integer> partitionsPerTopic,
			Map<String, Subscription> subscriptions) {
		// 获取topic下的consumer
		Map<String, List<String>> consumerPerTopic = this.consumersPerTopic(subscriptions);
		Map<String, List<TopicPartition>> assignment = new HashMap<>();
		for (String memberId : subscriptions.keySet()) {
			assignment.put(memberId, new ArrayList<>());
		}

		int consumerSize = consumerPerTopic.size();

		// 针对每一个主题进行分区分配
		for (Map.Entry<String, List<String>> topicEntry : consumerPerTopic.entrySet()) {
			String topic = topicEntry.getKey();
			List<String> consumersForTopic = topicEntry.getValue();

			Integer numPartitionsForTopic = partitionsPerTopic.get(topic);
			if (numPartitionsForTopic == null) {
				continue;
			}

			// 当前主题下的所有分区
			List<TopicPartition> partitions = AbstractPartitionAssignor.partitions(topic, numPartitionsForTopic);
			// 将每个分区随机分配给一个消费者
			for (TopicPartition partition : partitions) {
				int rand = new Random().nextInt(consumerSize);
				String randomConsumer = consumersForTopic.get(rand);
				assignment.get(randomConsumer).add(partition);
			}
		}
		return assignment;
	}

	/**
	 * 处理消费者订阅的信息
	 * 
	 * @see AbstractPartitionAssignor#subscription(java.util.Set)
	 */
	@Override
	public Subscription subscription(Set<String> topics) {
		return null;
	}

	/**
	 * <p>获取每个主题对应的消费者列表</p>
	 * 
	 * @param consumerMetadata
	 * @return
	 */
	private Map<String, List<String>> consumersPerTopic(Map<String, Subscription> consumerMetadata) {
		Map<String, List<String>> res = new HashMap<>();
		for (Map.Entry<String, Subscription> subscriptionEntry : consumerMetadata.entrySet()) {
			String consumerId = subscriptionEntry.getKey();
			for (String topic : subscriptionEntry.getValue().topics()) {
				List<String> consumersForTopic = res.get(topic);
				if (consumersForTopic == null || consumersForTopic.size() == 0) {
					consumersForTopic = new ArrayList<>();
				}
				consumersForTopic.add(consumerId);
				res.put(topic, consumersForTopic);
			}
		}
		return res;
	}

}
