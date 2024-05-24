package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import com.mapper.JournalMapper;
import com.service.JournalService;
import com.vo.JournalVO;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {

	@Autowired
	private JournalMapper journalMapper;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<HashMap<String, Object>> selectNlmTitleAbbreviations() throws Exception {
		return journalMapper.selectNlmTitleAbbreviations();
	}

	@Override
	public int getCountMaster() {
		return journalMapper.getCountMaster();
	}

	@Override
	public List<JournalVO> loadJournalsFromJsonFile(String jsonFilePath) throws Exception {
		return objectMapper.readValue(Paths.get(jsonFilePath).toFile(), new TypeReference<List<JournalVO>>() {
		});
	}

	@Override
	public void insertJournals(List<JournalVO> journals) {
		for (JournalVO journal : journals) {
			journalMapper.insertJournals(journal);
		}
	}

	@Override
	public List<HashMap<String, Object>> selectISSNfromMaster() throws Exception {
		return journalMapper.selectISSNfromMaster();
	}

}