package com.yumaolin.deepunderstand.mq.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


/**
 * 自定义生产者拦截器
 * 
 * @author yuml
 * @since 2019年8月27日
 */
public class CustomProduceInterceptor implements ProducerInterceptor<String, String> {

	private volatile long sendSuccess = 0;

	private volatile long failSuccess = 0;

	@Override
	public void configure(Map<String, ?> configs) {
	}

	/**
	 * 序列化和计算分区之前调用
	 * 
	 * @see ProducerInterceptor#onSend(ProducerRecord)
	 */
	@Override
	public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
		String value = "prefix_" + record.value();
		ProducerRecord<String, String> newRecord = new ProducerRecord<String, String>(record.topic(),
				record.partition(), record.timestamp(), record.key(), value, record.headers());
		return newRecord;
	}

	/**
	 * 消息应答之前和消息发送失败
	 * 
	 * @see ProducerInterceptor#onAcknowledgement(RecordMetadata, Exception)
	 */
	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		if (exception != null) {
			failSuccess++;
		} else {
			sendSuccess++;
		}
	}

	/**
	 * 资源关闭时执行清理操作
	 * 
	 * @see ProducerInterceptor#close()
	 */
	@Override
	public void close() {
		double rate = (double) sendSuccess / (sendSuccess + failSuccess);
		System.out.println("[info] 发送成功率:" + String.format("%.2f", rate * 100) + "%");
	}

}
