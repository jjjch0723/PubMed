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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.service.JournalService;
import com.vo.JournalVO;

@Controller
public class JournalController {

	@Autowired
	private JournalService journalService;

	@GetMapping("/outline")
	public String crawlbyISSN(Model model) throws Exception {
		int count = journalService.getCountMaster();
		model.addAttribute("count", count);

		List<HashMap<String, Object>> issnList = journalService.selectISSNfromMaster(); // offset 파라미터 없이 변경
		List<String> errorISSNs = new ArrayList<>();

		for (HashMap<String, Object> issnData : issnList) {
			String issn = (String) issnData.get("issn");
			String jourId = issnData.get("jour_id").toString();

			try {
				String pythonExecutablePath = "C:\\DevTool\\Python\\Python312\\python.exe";;
				String pythonScriptPath = "C:\\DevTool\\VS-WORKSPACE\\py\\outline.py"; // master.py
				String[] command = { pythonExecutablePath, pythonScriptPath, issn, jourId };

				ProcessBuilder processBuilder = new ProcessBuilder(command);
				Process process = processBuilder.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				boolean dumpDataFound = false;

				while ((line = reader.readLine()) != null) {
					if (line.contains("4. dump data")) {
						dumpDataFound = true;
					}
					if (line.startsWith("Error:")) {
						errorISSNs.add(line.replace("Error: ", ""));
					}
				}

				process.waitFor();

				if (!dumpDataFound) {
					errorISSNs.add(issn + " " + jourId);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		saveErrorISSNsToFile(errorISSNs);
		model.addAttribute("errorISSNs", errorISSNs);

		return "journal";
	}

	private void saveErrorISSNsToFile(List<String> errorISSNs) {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter("C:\\DevTool\\VS-WORKSPACE\\json\\error_issn.json"))) {
			JSONArray jsonArray = new JSONArray(errorISSNs);
			writer.write(jsonArray.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/insertoutline")
	public String loadJsonAndInsertData() {
		try {
			String jsonFilePath = "C:\\DevTool\\VS-WORKSPACE\\json\\outline.json"; // master-outline.json
			List<JournalVO> journals = journalService.loadJournalsFromJsonFile(jsonFilePath);
			journalService.insertJournals(journals);
			return "/journal";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error processing JSON file: " + e.getMessage();
		}
	}
}
