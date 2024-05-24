package com.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vo.JournalVO;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface JournalMapper {

	List<HashMap<String, Object>> selectISSNfromMaster();
	
	int getCountMaster();
	
	void insertJournals(JournalVO journalVO);
	
	List<HashMap<String, Object>> selectNlmTitleAbbreviations();

}
