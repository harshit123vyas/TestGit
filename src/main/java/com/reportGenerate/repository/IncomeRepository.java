package com.reportGenerate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.reportGenerate.entity.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer>{
	
	public List<Income> findByUserId(Integer userId); 
	
	@Query("SELECT SUM(i.finalAmount) FROM Income i WHERE i.user.id = :userId")
    public Double findTotalIncomeByUserId(@Param("userId") Integer userId);

}
