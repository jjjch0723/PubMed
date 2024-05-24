package com.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mapper.XlsxMapper;
import com.service.XlsxService;
import com.vo.XlsxData;

@Service
public class XlsxServiceImpl implements XlsxService{

	@Autowired
	private XlsxMapper mapper;

	public void xlsxtoDB(MultipartFile file) throws Exception {
		FileInputStream excelFile = new FileInputStream(convertMultipartFileToFile(file));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheetAt(0);

		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);

			Cell cell1 = row.getCell(0); // 열 1
			Cell cell2 = row.getCell(3); // 열 2
			Cell cell3 = row.getCell(4); // 열 3

			// 엑셀에서 읽은 데이터를 자바 객체로 변환
			String JOURNAL_NAME = cell1.toString();
			String ISSN = cell2.toString();
			String EISSN = cell3.toString();

			XlsxData data = new XlsxData();

			data.setJournal_nm(JOURNAL_NAME);
			data.setIssn(ISSN);
			data.setEissn(EISSN);

			mapper.xlsxtoDB(data);

			workbook.close();
			excelFile.close();
		}
	}

	private File convertMultipartFileToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		file.transferTo(convertedFile);
		return convertedFile;
	}
	
	public List<XlsxData> getData() throws Exception {
		return mapper.getData();
	}
	
}
