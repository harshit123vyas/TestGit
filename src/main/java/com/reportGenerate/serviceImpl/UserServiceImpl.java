package com.reportGenerate.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reportGenerate.entity.Expenses;
import com.reportGenerate.entity.Income;
import com.reportGenerate.entity.User;
import com.reportGenerate.repository.ExpenseRepository;
import com.reportGenerate.repository.IncomeRepository;
import com.reportGenerate.repository.UserRepository;
import com.reportGenerate.service.UserService;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public byte[] generateReport(Integer userId, List<?> data1, List<?> data2, String templatePath) throws JRException {

		 // Load the .jrxml template
        JasperReport jasperReport = JasperCompileManager.compileReport(templatePath);

        // Create data sources from the lists of data
        JRBeanCollectionDataSource expenseReportDataSource = new JRBeanCollectionDataSource(data1);
        JRBeanCollectionDataSource incomeReportDataSource = new JRBeanCollectionDataSource(data2);
//        JRBeanCollectionDataSource pieChartDataSource = new JRBeanCollectionDataSource(data1);

        // Calculate profit
        Double income = incomeRepository.findTotalIncomeByUserId(userId);
        Double expense =  expenseRepository.findTotalExpenseByUserId(userId);
        Double profit = income - expense;
        Optional<User> optionalUser = userRepository.findById(userId);
        User user =optionalUser.get();
        
        // Set parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", user);
        parameters.put("income", income);
        parameters.put("expense", expense);
        parameters.put("profit", profit);
        parameters.put("ExpenseReportDataSource", expenseReportDataSource);
        parameters.put("IncomeReportDataSource", incomeReportDataSource);
//        parameters.put("PieChartDataSource", pieChartDataSource);

        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        // Export the report to a byte array
        return JasperExportManager.exportReportToPdf(jasperPrint);

	
	}

	private byte[] exportReportToPdf(JasperPrint jasperPrint) throws JRException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		return outputStream.toByteArray();
	}

	public User createUser(User user) {
		try {
			if (user == null) {
				throw new IllegalArgumentException("User cannot be null");
			}
			return userRepository.save(user);
		} catch (Exception e) {

			throw new RuntimeException("Error creating user", e);
		}
	}

	public Income addIncome(Integer userId, Income income) {
		log.info("income : {}", income);
		try {
			if (userId == null || income == null) {
				throw new IllegalArgumentException("User ID and Income cannot be null");
			}

			Optional<User> userOpt = userRepository.findById(userId);
			if (userOpt.isPresent()) {
				log.info("userOpt is not null ");
				User user = userOpt.get();
				double amount = income.getAmount();
				double gst = income.getGst();
				double pst = income.getPst();
				Double finalAmount = amount + gst / 100.0 + pst;
				income.setUser(user);
				income.setFinalAmount(finalAmount);
				return incomeRepository.save(income);
			} else {
				throw new RuntimeException("User not found");
			}
		} catch (Exception e) {
			log.error("exception : {} ", e);
			throw new RuntimeException("Error adding income", e);
		}
	}

	public Expenses addExpense(Integer userId, Expenses expense) {
		try {
			if (userId == null || expense == null) {
				throw new IllegalArgumentException("User ID and Expense cannot be null");
			}

			Optional<User> userOpt = userRepository.findById(userId);
			if (userOpt.isPresent()) {
				User user = userOpt.get();
				Double finalAmount = expense.getAmount() + expense.getGst() / 100 + expense.getPst();
				expense.setUser(user);
				expense.setFinalAmount(finalAmount);
				return expenseRepository.save(expense);
			} else {
				throw new RuntimeException("User not found");
			}
		} catch (Exception e) {

			throw new RuntimeException("Error adding expense", e);
		}
	}

	public List<Income> getIncomesByUserId(Integer userId) {
		try {
			if (userId == null) {
				throw new IllegalArgumentException("User ID cannot be null");
			}
			return incomeRepository.findByUserId(userId);
		} catch (Exception e) {
			throw new RuntimeException("Error fetching incomes", e);
		}
	}

	public List<Expenses> getExpeseByUserId(Integer userId) {
		try {
			if (userId == null) {
				throw new IllegalArgumentException("User ID cannot be null");
			}
			return expenseRepository.findByUserId(userId);
		} catch (Exception e) {
			throw new RuntimeException("Error fetching expenses", e);
		}
	}

	public List<Object[]> getTotalAmountByExpenseCategoryNative() {
		return expenseRepository.findTotalAmountByExpenseCategoryNative();

	}
}
