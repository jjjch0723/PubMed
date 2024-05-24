package com.service;
import java.util.HashMap;
import java.util.List;

import com.vo.JournalVO;

public interface JournalService {

	List<HashMap<String, Object>> selectISSNfromMaster() throws Exception;
	
	int getCountMaster();
	List<JournalVO> loadJournalsFromJsonFile(String jsonFilePath) throws Exception;
	
	void insertJournals(List<JournalVO> journalVO) throws Exception;
	
	List<HashMap<String, Object>> selectNlmTitleAbbreviations() throws Exception;

}