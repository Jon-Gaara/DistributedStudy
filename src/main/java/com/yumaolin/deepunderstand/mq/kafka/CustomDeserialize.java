package com.yumaolin.deepunderstand.mq.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.alibaba.fastjson.JSON;


/**
 * 自定义反序列化类
 * 
 * @author yuml
 * @param <T>
 * @since 2019年8月26日
 */
public class CustomDeserialize<T> implements Deserializer<T> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		return (T) JSON.parse(data);
	}

	@Override
	public void close() {
	}

}
