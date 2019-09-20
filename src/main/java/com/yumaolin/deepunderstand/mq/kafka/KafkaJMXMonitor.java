package com.yumaolin.deepunderstand.mq.kafka;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


/**
 * kafka JMX监控
 * 
 * @author yuml
 * @since 2019年9月12日
 */
public class KafkaJMXMonitor {

	private MBeanServerConnection connection;

	public boolean init(final String ipAndPort) {
		final String jmxUrl = "service:jmx:rmi:///jndi/rmi://" + ipAndPort + "/jmxrmi";
		try {
			JMXServiceURL serviceURL = new JMXServiceURL(jmxUrl);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL, null);
			connection = connector.getMBeanServerConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * <p>根据MBean名称和属性获取具体的值</p>
	 * 
	 * @param objName
	 * @param objAttr
	 * @return
	 */
	private Object getAttribute(String objName, String objAttr) {
		try {
			ObjectName objectName = new ObjectName(objName);
			return connection.getAttribute(objectName, objAttr);
		} catch (MalformedObjectNameException | IOException | AttributeNotFoundException | InstanceNotFoundException
				| MBeanException | ReflectionException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		KafkaJMXMonitor monitor = new KafkaJMXMonitor();
		monitor.init("localhost:9009");
		System.out.println(
				monitor.getAttribute("java.lang:type=GarbageCollector,name=G1 Old Generation", "CollectionTime"));
	}
}
