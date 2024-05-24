package com.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JournalVO {
	@JsonProperty("Jour ID")
	private String jourId;
	@JsonProperty("Author(s)")
	private String authors;
	@JsonProperty("NLM Title Abbreviation")
	private String nlmTitleAbbreviation;
	@JsonProperty("Title(s)")
	private String titles;
	@JsonProperty("Other Title(s)")
	private String otherTitles;
	@JsonProperty("Publication Start Year")
	private String publicationStartYear;
	@JsonProperty("Frequency")
	private String frequency;
	@JsonProperty("Country of Publication")
	private String countryOfPublication;
	@JsonProperty("Publisher")
	private String publisher;
	@JsonProperty("Latest Publisher")
	private String latestPublisher;
	@JsonProperty("Language")
	private String language;
	@JsonProperty("ISSN")
	private String issn;
	@JsonProperty("Coden")
	private String coden;
	@JsonProperty("LCCN")
	private String lccn;
	@JsonProperty("Electronic Links")
	private String electronicLinks;
	@JsonProperty("MeSH")
	private String mesh;
	@JsonProperty("Other ID")
	private String otherId;
	@JsonProperty("NLM ID")
	private String nlmId;
}
