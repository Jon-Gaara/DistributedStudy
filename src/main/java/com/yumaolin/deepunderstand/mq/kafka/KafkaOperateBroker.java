package com.yumaolin.deepunderstand.mq.kafka;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;

import com.yumaolin.deepunderstand.common.constant.BaseConstant;


/**
 * kafka操作broker
 * 
 * @author yuml
 * @since 2019年8月28日
 */
public class KafkaOperateBroker {

	/**
	 * <p>初始化配置</p>
	 * @return
	 */
	public static Properties initConfingure() {
		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BaseConstant.KAFKA_BROCK_LIST);
		props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, BaseConstant.TIME_OUT);
		return props;
	}

	/**
	 * <p>获取所有的topic</p>
	 * 
	 * @param adminClient
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static Map<String, TopicListing> getTopicList(AdminClient adminClient)
			throws InterruptedException, ExecutionException {
		ListTopicsResult listTopicsResult = adminClient.listTopics();
		KafkaFuture<Map<String, TopicListing>> future = listTopicsResult.namesToListings();
		Map<String, TopicListing> topicMap = future.get();
		for (Map.Entry<String, TopicListing> entry : topicMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue().isInternal());
		}
		return topicMap;
	}

	/**
	 * <p>创建topic</p>
	 * 
	 * @param adminClient
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void createTopic(AdminClient adminClient) throws InterruptedException, ExecutionException {
		NewTopic newTopic = new NewTopic(BaseConstant.KAFKA_TOPIC_NAME, (short) 1, (short) 1);
		CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
		Map<String, KafkaFuture<Void>> topicMap = createTopicsResult.values();
		KafkaFuture<Void> future = topicMap.get(BaseConstant.KAFKA_TOPIC_NAME);
		future.get();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Properties props = initConfingure();
		AdminClient adminClient = AdminClient.create(props);
		createTopic(adminClient);
		getTopicList(adminClient);
		adminClient.close();
	}
}
