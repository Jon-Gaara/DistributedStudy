package com.yumaolin.deepunderstand.common.constant;

/**
 *
 * @author yuml
 * @since 2019年8月15日
 */
public class BaseConstant {
	
	public static final String ZK_CONNECT_List = "zookeeper01-center.lycheepay.org:19555,zookeeper02-center.lycheepay.org:19555";

	public static final int TIME_OUT = 4000;

	public static final String ZK_DUBBO_NODE = "/dubbo";

	public static final String ZK_ARIES_NODE = "/aries";

	public static final String ZK_SERVICE_TYPE_PRO_NODE = "/providers";

	public static final String ZK_SERVICE_TYPE_CUS_NODE = "/consumers";

	public static final String UTF_8 = "UTF-8";
	
	public static final String ZK_LOCK_ROOT_PATH = "/distribute/root";
	
	public static final String KAFKA_TOPIC_NAME = "testTopic";
	
	public static final String KAFKA_BROCK_LIST = "localhost:9092";
}
