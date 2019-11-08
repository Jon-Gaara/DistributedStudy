package com.yumaolin.deepunderstand.rpc.feign;

import java.util.List;

import com.yumaolin.deepunderstand.common.dto.Contributor;

import feign.Feign;
import feign.Request.Options;
import feign.gson.GsonDecoder;

/** 
 *
 * @author yuml
 * @since 2019年9月23日
 */
public class FeignDemo {

	public static void main(String[] args) {
		GitHub github = Feign.builder()
				.decoder(new GsonDecoder())
				//.client(RibbonClient.create())
				.target(GitHub.class, "https://api.github.com");
	    List<Contributor> contributors = github.contributors("OpenFeign", "feign");
		for (Contributor contributor : contributors) {
		    System.out.println(contributor.getLogin() + " (" + contributor.getContributions() + ")");
		}
	}
}
