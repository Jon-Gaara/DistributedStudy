package com.yumaolin.deepunderstand.registry.zkClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yumaolin.deepunderstand.common.constant.BaseConstant;

public class ZkClientFindService {

	/**
	 * <p>根据接口全限定名查询zk上的dubbo服务</p>
	 * @param interfaceName
	 * @return
	 */
	public static String findServices(String interfaceName) {
		if (StringUtils.isEmpty(interfaceName)) {
			return "参数不能为空";
		}
		String connectString = BaseConstant.ZK_CONNECT_List;
		String serviceType = BaseConstant.ZK_SERVICE_TYPE_PRO_NODE;
		if (interfaceName.contains("-")) {
			String[] splitStr = interfaceName.split("-");
			interfaceName = splitStr[0];
			if (splitStr.length > 1) {
				if (StringUtils.isNotEmpty(splitStr[1])) {
					serviceType = getServiceType(splitStr[1]);
				}
			}
			if (splitStr.length > 2) {
				if (StringUtils.isNotEmpty(splitStr[2])) {
					connectString = splitStr[2];
				}
			}
		}
		String result = null;
		try {
			ZkClient zkClient = new ZkClient(connectString, BaseConstant.TIME_OUT);
			List<String> listChildren = zkClient.getChildren(BaseConstant.ZK_DUBBO_NODE);
			List<String> listAriesChildren = zkClient.getChildren(BaseConstant.ZK_ARIES_NODE);
			List<String> listResult = new ArrayList<>();
			for (String details : listChildren) {
				if (details.contains(interfaceName)) {
					List<String> providers = zkClient.getChildren(BaseConstant.ZK_DUBBO_NODE + "/" + interfaceName + serviceType);
					for (String provider : providers) {
						listResult.add(URLDecoder.decode(provider, BaseConstant.UTF_8));
					}
				}
			}

			for (String details : listAriesChildren) {
				if (details.contains(interfaceName)) {
					List<String> providers = zkClient.getChildren(BaseConstant.ZK_ARIES_NODE + "/" + interfaceName + serviceType);
					for (String provider : providers) {
						listResult.add(URLDecoder.decode(provider, BaseConstant.UTF_8));
					}
				}
			}

			if (CollectionUtils.isEmpty(listResult)) {
				result = null;
			} else {
				result = JSON.toJSONString(listResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				result = URLDecoder.decode(e.getMessage(), BaseConstant.UTF_8);
			} catch (UnsupportedEncodingException e1) {
			}
		}
		return result;
	}

	private static String getServiceType(String serviceTypeStr) {
		String serviceType = BaseConstant.ZK_SERVICE_TYPE_PRO_NODE;
		switch (serviceTypeStr) {
		case "1":
			serviceType = BaseConstant.ZK_SERVICE_TYPE_CUS_NODE;
			break;
		default:
			break;
		}
		return serviceType;
	}
}
