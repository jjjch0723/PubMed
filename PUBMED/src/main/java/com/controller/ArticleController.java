package com.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import com.service.ArticleService;
import com.service.JournalService;
import com.vo.ArticleVO;
import com.vo.PmidVO;

@Controller
public class ArticleController {

	@Autowired
	private JournalService journalService;
	
	@Autowired
	private ArticleService articleService;

	@GetMapping("/abbs-pmid")
	public String getNlmTitleAbbreviations() throws Exception {

		List<HashMap<String, Object>> abbrList = journalService.selectNlmTitleAbbreviations();

		for (HashMap<String, Object> abbrdata : abbrList) {
			String abbs = (String) abbrdata.get("nlm_title_abbreviation");
			String jourId = abbrdata.get("jour_id").toString();
			try {
				String pythonExecutablePath = "C:\\DevTool\\Python\\Python312\\python.exe";
				String pythonScriptPath = "C:\\DevTool\\VS-WORKSPACE\\py\\nlmabbs.py";
				String[] command = { pythonExecutablePath, pythonScriptPath, abbs.replace(" ", "+"), jourId };

				System.out.println(abbs+" "+jourId);

				ProcessBuilder processBuilder = new ProcessBuilder(command);
				Process process = processBuilder.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}

				process.waitFor();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "article";
	}

	@GetMapping("/insertpmid")
	public String loadPmidJsonAndInsertData() {
		try {
			String jsonFilePath = "C:\\DevTool\\VS-WORKSPACE\\json\\pubmed_articles.json";
			List<PmidVO> pmids = articleService.loadPMIDsFromJsonFile(jsonFilePath);
			articleService.insertAbbstoPMID(pmids);
			return "redirect:/journal";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error processing JSON file: " + e.getMessage();
		}
	}

	@GetMapping("/pmid-abstract")
	public String getAbstractfromPMID(Model model) throws Exception {

		List<HashMap<String, Object>> PMIDList = articleService.selectPMID();
		List<String> errorPMIDs = new ArrayList<>(); // 오류가 발생한 pmid와 jourId를 저장할 리스트

		for (HashMap<String, Object> pmidData : PMIDList) {
			String pmid = (String) pmidData.get("pmid");
			String jourId = pmidData.get("jour_id").toString();

			System.out.println("Processing PMID : " + pmid);
			boolean dumpDataFound = false;

			try {
				String pythonExecutablePath = "C:\\DevTool\\Python\\Python312\\python.exe";
				String pythonScriptPath = "C:\\DevTool\\VS-WORKSPACE\\py\\abstract.py";
				String[] command = { pythonExecutablePath, pythonScriptPath, pmid, jourId };

				ProcessBuilder processBuilder = new ProcessBuilder(command);
				Process process = processBuilder.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					if (line.contains("dump data")) {
						dumpDataFound = true;
						break; // "dump data"가 출력되면 루프를 종료합니다.
					}
				}

				process.waitFor();

				if (!dumpDataFound) {
					// "dump data"가 출력되지 않았다면 오류 리스트에 추가합니다.
					errorPMIDs.add(pmid + " " + jourId);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 오류 리스트를 JSON 파일로 저장합니다.
		saveErrorPMIDsToFile(errorPMIDs);

		model.addAttribute("errorPMIDs", errorPMIDs);
		return "article";
	}

	private void saveErrorPMIDsToFile(List<String> errorPMIDs) {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter("C:\\DevTool\\VS-WORKSPACE\\json\\error_pmids.json"))) {
			writer.write(new JSONArray(errorPMIDs).toString(4)); // JSON 형식으로 예쁘게 출력합니다.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/insertabstract")
	public String loadAbstractData() {
		try {
			String jsonFilePath = "C:\\DevTool\\VS-WORKSPACE\\json\\articles.json";
			List<ArticleVO> articles = articleService.loadAbstractFromJsonFile(jsonFilePath);
			articleService.insertAbstract(articles);
			
			return "redirect:/article";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error processing JSON file: " + e.getMessage();
		}
	}
}