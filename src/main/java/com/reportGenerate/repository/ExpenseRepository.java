package com.reportGenerate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.reportGenerate.entity.Expenses;

@Repository
public interface ExpenseRepository extends JpaRepository<Expenses, Integer> {

	public List<Expenses> findByUserId(Integer userId);

	@Query("SELECT SUM(e.finalAmount) FROM Expenses e WHERE e.user.id = :userId")
	public Double findTotalExpenseByUserId(@Param("userId") Integer userId);

	@Query(value = "SELECT expense_category AS category, SUM(amount) AS totalAmount FROM expenses GROUP BY expense_category", nativeQuery = true)
    
	List<Object[]> findTotalAmountByExpenseCategoryNative();
}
