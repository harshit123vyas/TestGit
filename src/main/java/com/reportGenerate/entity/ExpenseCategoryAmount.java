package com.reportGenerate.entity;

import com.reportGenerate.enums.ExpenseCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategoryAmount {

	private String category;
    private Double totalAmount;
}
