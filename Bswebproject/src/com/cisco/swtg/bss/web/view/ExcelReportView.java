package com.cisco.swtg.bss.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.cisco.swtg.bss.web.domain.BugSearchResults;

/**
 * This class is configured as view for ExcelView and used to create Excel sheet
 * with BugResult
 * 
 * @author teprasad
 * 
 */
public class ExcelReportView extends AbstractExcelView {

	private static final Log logger = LogFactory.getLog(ExcelReportView.class);
	private static final String EMPTY_STRING = "";

	/*
	 * This method is used to build the excel sheet and write the bugDetails on
	 * it.
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Build Excel document start");
		BugSearchResults bugSearchResults = (BugSearchResults) model.get("bugSearchResults");
		if (bugSearchResults != null) {
			JSONArray bugs = bugSearchResults.getBugDetailJSON();
			if (bugs != null) {
				int records = bugs.length();
				// create a wordsheet
				HSSFSheet sheet = workbook.createSheet("Search Results");
				logger.info("Setting of excel header details start ");
				HSSFRow header = sheet.createRow(0);
				header.createCell((short) 0).setCellValue("BUGID");
				header.createCell((short) 1).setCellValue("HEADLINE");
				header.createCell((short) 2).setCellValue("CUSTOMER REPORTED");
				header.createCell((short) 3).setCellValue("STATUS");
				header.createCell((short) 4).setCellValue("SEVERITY");
				logger.info("Setting of excel header details end ");
				int rowNum = 1;
				logger.info("Start writing the records to excel file");
				for (int i = 0; i < records; ++i) {
					JSONObject bug = bugs.getJSONObject(i);
					HSSFRow row = sheet.createRow(rowNum++);
					row.createCell((short) 0).setCellValue(bug.getString("bugId"));
					row.createCell((short) 1).setCellValue(bug.optString("headLine", EMPTY_STRING));
					row.createCell((short) 2).setCellValue(bug.optInt("srNumberCount", 0));
					row.createCell((short) 3).setCellValue(bug.optString("statusGroup", EMPTY_STRING));
					row.createCell((short) 4).setCellValue(bug.optString("severityCode", EMPTY_STRING));
				}
				logger.info("End writing the records to excel file");
			}
		}
		logger.info("Build Excel document end");
	}
}