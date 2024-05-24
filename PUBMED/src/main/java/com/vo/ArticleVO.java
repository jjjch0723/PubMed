package com.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ArticleVO {
	@JsonProperty("title")
	private String title;

	@JsonProperty("authors")
	private String authors;

	@JsonProperty("date")
	private String date;
	
	@JsonProperty("pmid")
	private String pmid;
	
	@JsonProperty("pmcid")
	private String pmcid;
	
	@JsonProperty("doi")
	private String doi;

	@JsonProperty("abstract")
	private String abstracts;

	@JsonProperty("keywords")
	private String keywords;

	@JsonProperty("s-titles")
	private String sTitles; // Similar Titles

	@JsonProperty("s-authors")
	private String sAuthors; // Similar Authors

	@JsonProperty("s-pmids")
	private String sPmids; // Similar PMIDs

	@JsonProperty("c-titles")
	private String cTitles; // Cited by Titles

	@JsonProperty("c-authors")
	private String cAuthors; // Cited by Authors

	@JsonProperty("c-pmids")
	private String cPmids; // Cited by PMIDs

	@JsonProperty("jourId")
	private String jourId;
}
