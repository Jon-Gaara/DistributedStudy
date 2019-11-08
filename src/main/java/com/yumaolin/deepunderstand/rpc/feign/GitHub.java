package com.yumaolin.deepunderstand.rpc.feign;

import java.util.List;

import com.yumaolin.deepunderstand.common.dto.Contributor;
import com.yumaolin.deepunderstand.common.dto.Issue;

import feign.Param;
import feign.RequestLine;

/**
 *
 * @author yuml
 * @since 2019年9月23日
 */
public interface GitHub {
	@RequestLine("GET /repos/{owner}/{repo}/contributors")
	List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

	@RequestLine("POST /repos/{owner}/{repo}/issues")
	void createIssue(Issue issue, @Param("owner") String owner, @Param("repo") String repo);
}
