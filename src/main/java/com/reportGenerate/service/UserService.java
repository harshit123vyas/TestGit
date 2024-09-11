package com.reportGenerate.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.reportGenerate.entity.Expenses;
import com.reportGenerate.entity.Income;
import com.reportGenerate.entity.User;
import net.sf.jasperreports.engine.JRException;

@Service
public interface UserService {

//	public byte[] generateReport(Integer userId,List<?> data1,List<?> data2,List<?> data3, String templatePath) throws JRException;
	
	public byte[] generateReport(Integer userId,List<?> data1,List<?> data2, String templatePath) throws JRException;

    public User createUser(User user) ;

    public Income addIncome(Integer userId, Income income);

    public Expenses addExpense(Integer userId, Expenses expense);
    
    public List<Income> getIncomesByUserId(Integer userId);
    
    public List<Expenses> getExpeseByUserId(Integer userId);
    
    public List<Object[]> getTotalAmountByExpenseCategoryNative();
    
}
