package com.yumaolin.deepunderstand.mq.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.alibaba.fastjson.JSON;


/**
 * 自定义序列化类
 * 
 * @author yuml
 * @param <T>
 * @since 2019年8月26日
 */
public class CustomSerialize<T> implements Serializer<T> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public byte[] serialize(String topic, T data) {
		return JSON.toJSONBytes(data);
	}

	@Override
	public void close() {
	}

}
