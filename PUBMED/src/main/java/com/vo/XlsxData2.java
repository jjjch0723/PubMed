package com.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class XlsxData2 {
	@JsonProperty("Author(s)")
    private String author;

    @JsonProperty("NLM Title Abbreviation")
    private String nlmTitleAbbreviation;

    @JsonProperty("Title(s)")
    private String title;

    @JsonProperty("Uniform Title")
    private String uniformTitle;

    @JsonProperty("Edition")
    private String edition;

    @JsonProperty("Publication Start Year")
    private String publicationStartYear;

    @JsonProperty("Publication End Year") // Publication End Year 필드 추가
    private String publicationEndYear;

    @JsonProperty("Frequency")
    private String frequency;

    @JsonProperty("Country of Publication")
    private String countryOfPublication;

    @JsonProperty("Latest Publisher")
    private String latestPublisher;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("ISSN")
    private String issn;

    @JsonProperty("Coden")
    private String coden;

    @JsonProperty("LCCN")
    private String lccn;

    @JsonProperty("Electronic Links")
    private Object electronicLinks;

    @JsonProperty("In")
    private String in;

    @JsonProperty("Current Indexing Status")
    private String currentIndexingStatus;

    @JsonProperty("Current Subset")
    private String currentSubset;

    @JsonProperty("MeSH")
    private String meSH;

    @JsonProperty("Broad Subject Term(s)")
    private String broadSubjectTerms;

    @JsonProperty("Publication Type(s)")
    private String publicationTypes;

    @JsonProperty("Notes")
    private String notes;

    @JsonProperty("Other ID")
    private String otherID;

    @JsonProperty("NLM ID")
    private String nlmID;

    @JsonProperty("Merger") // Merger 필드 추가
    private Object merger;
    
    @JsonProperty("Other Title(s)")
    private String othertitle;
    
    @JsonProperty("Publisher")
    private String publisher;
    
    @JsonProperty("Related Title")
    private Object relatedtitle;
    
    @JsonProperty("Continues")
    private String continues;
    
    @JsonProperty("Translated Title")
    private String translatedtitle;
    
    @JsonProperty("Collection Status")
    private String collectionstatus;

    // 생성자, getter, setter 등 필요한 메서드를 추가
}