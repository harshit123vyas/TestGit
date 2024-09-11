package com.reportGenerate.controller;

import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.reportGenerate.entity.Expenses;
import com.reportGenerate.entity.Income;
import com.reportGenerate.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportController {

	@Autowired
	private UserService userService;

	@GetMapping("/generateReport/{userId}")
	public void generateReport(HttpServletResponse response, @PathVariable("userId") Integer userId) {
		try {
			// Example data to be included in the report
			List<Expenses> expenses = userService.getExpeseByUserId(userId);
			List<Income> incomes = userService.getIncomesByUserId(userId);
//			  List<Object[]> groupedData = userService.getTotalAmountByExpenseCategoryNative();

		        // Transform the grouped data into a suitable format for the report
//		        List<ExpenseCategoryAmount> dataList = new ArrayList<>();
//		        for (Object[] record : groupedData) {
//		            ExpenseCategoryAmount data = new ExpenseCategoryAmount((String) record[0], (Double) record[1]);
//		            dataList.add(data);
//		        }
			
			// Path to the .jrxml file
			String templatePath = "src/main/resources/check.jrxml";
//		    String templatePath = "src/main/resources/report.jrxml";

			// Generate the report
//			byte[] pdfReport = userService.generateReport(userId, expenses, incomes,dataList, templatePath);
			byte[] pdfReport = userService.generateReport(userId, expenses, incomes, templatePath);

			// Set the response content type and headers
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=userReport.pdf");

			// Write the PDF report to the response output stream
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(pdfReport);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
