package com.reportGenerate.dto;

import com.reportGenerate.enums.Category;

import lombok.Data;
@Data
public class ReportDto {
	
	private Category category;
	
	private Double amount;
	
	private Double gst;
	
	private Double pst;
	
	private Double total;

}
