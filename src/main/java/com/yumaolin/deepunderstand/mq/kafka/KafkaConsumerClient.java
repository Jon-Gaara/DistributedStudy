package com.yumaolin.deepunderstand.mq.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import com.yumaolin.deepunderstand.common.constant.BaseConstant;


/**
 *
 * @author yuml
 * @since 2019年8月16日
 */
public class KafkaConsumerClient {

	public static Map<String, Object> initConfingure() {
		Map<String, Object> properties = new HashMap<>();
		// kafka borker
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BaseConstant.KAFKA_BROCK_LIST);
		// 配置key 反序列化
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, CustomDeserialize.class.getName());
		// 配置value 反序列化
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserialize.class.getName());
		// properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "kafka.client.id.testTopic");
		// 是否自动提交
		// properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
		// 配置自定义拦截器
		properties.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomCostumerInterceptor.class.getName());
		// 配置自定义分区分配策略
		properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CustomPartitionAssignor.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-1");
		return properties;
	}

	public static void main(String[] args) {
		Map<String, Object> properties = initConfingure();
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList(BaseConstant.KAFKA_TOPIC_NAME));
		try {
			Set<TopicPartition> partitionInfoList = consumer.assignment();
			consumer.beginningOffsets(partitionInfoList);
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
				System.out.println("records :" + records.count());
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("key = " + record.key() + ", value = " + record.value());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}
}
