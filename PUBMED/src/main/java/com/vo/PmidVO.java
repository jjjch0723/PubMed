package com.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PmidVO {
	@JsonProperty("jour_id")
	private String jourid;
	@JsonProperty("abbr")
	private String abbr;
	@JsonProperty("title")
	private String title;
	@JsonProperty("pmid")
	private String pmid;
}
