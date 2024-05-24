package com.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vo.ArticleVO;
import com.vo.PmidVO;

@Mapper
public interface ArticleMapper {

	void insertAbbstoPMID(PmidVO pvo);

	List<HashMap<String, Object>> selectPMID();
	void insertArticles(ArticleVO articleVO);
	
}
