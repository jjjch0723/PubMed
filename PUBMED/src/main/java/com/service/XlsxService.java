package com.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vo.XlsxData;

public interface XlsxService {

	public void xlsxtoDB(MultipartFile file) throws Exception;
	
	public List<XlsxData> getData() throws Exception;

}
