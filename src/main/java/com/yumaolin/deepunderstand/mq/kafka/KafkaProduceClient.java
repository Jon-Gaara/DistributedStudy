package com.yumaolin.deepunderstand.mq.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.yumaolin.deepunderstand.common.constant.BaseConstant;

/** 
 *
 * @author yuml
 * @since 2019年8月16日
 */
public class KafkaProduceClient {
	
	public static Map<String, Object> initConfingure() {
		Map<String, Object> properties = new HashMap<>();
		//kafka borker
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BaseConstant.KAFKA_BROCK_LIST);
		//key 序列化
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, CustomSerialize.class.getName());
		//value 序列化
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerialize.class.getName());
		//properties.put(ProducerConfig.CLIENT_ID_CONFIG, "kafka.client.id.testTopic");
		//设置 拦截器
		properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, CustomProduceInterceptor.class.getName());
		//设置 幂等 客户端确保 retries>0,acks=-1,max.in.flight.request.per.connection<=5 
		properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
		//开启事务
		properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactionalId");
		return properties;
	}
	
	public static void main(String[] args) {
		Map<String, Object> properties = initConfingure();
		KafkaProducer<String,String> producer = new KafkaProducer<>(properties);
		try {
			for(int i=0;i<10;i++) {
				ProducerRecord<String,String> record = new ProducerRecord<>(BaseConstant.KAFKA_TOPIC_NAME,"number "+i);
				Future<RecordMetadata> future = producer.send(record);
				future.get();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			producer.close();
		}
	}
}
