package com.service;

import java.util.HashMap;
import java.util.List;

import com.vo.ArticleVO;
import com.vo.PmidVO;

public interface ArticleService {
	
	List<PmidVO> loadPMIDsFromJsonFile(String jsonFilePath) throws Exception;
	void insertAbbstoPMID(List<PmidVO> pvoList) throws Exception;
	
	List<HashMap<String, Object>> selectPMID() throws Exception;
	
	List<ArticleVO> loadAbstractFromJsonFile(String jsonFilePath) throws Exception;
    void insertAbstract(List<ArticleVO> articleList) throws Exception;

}
