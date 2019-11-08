package com.yumaolin.deepunderstand.common.dto;

import java.util.List;

import lombok.Data;

/** 
 *
 * @author yuml
 * @since 2019年9月23日
 */
@Data
public class Issue {
	String title;
	String body;
	List<String> assignees;
	int milestone;
	List<String> labels;
}
