package com.yumaolin.deepunderstand.mq.kafka;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

/** 
 *
 * @author yuml
 * @since 2019年8月27日
 */
public class CustomCostumerInterceptor implements ConsumerInterceptor<String,String>{

	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 调用poll返回值之前调用
	 * @see org.apache.kafka.clients.consumer.ConsumerInterceptor#onConsume(ConsumerRecords)
	 */
	@Override
	public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
		
		return records;
	}

	/**
	 * 提交完offest之后调用
	 * @see org.apache.kafka.clients.consumer.ConsumerInterceptor#onCommit(Map)
	 */
	@Override
	public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
		offsets.forEach((tp,offset)->{
			System.out.println(tp+":"+offset.offset());
		});
	}
	

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
