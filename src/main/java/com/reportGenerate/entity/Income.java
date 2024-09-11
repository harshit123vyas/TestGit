package com.reportGenerate.entity;

import com.reportGenerate.enums.IncomeCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "income")
public class Income {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "incomeCategory")
	private IncomeCategory incomeCategory;
	
	@Column(name = "amount")
	private Double amount; 
	
	@Column(name = "finalAmount")
	private Double finalAmount;
	
	@Column(name = "gst")
	private Double gst;
	
	@Column(name = "pst")
	private Double pst;
	
	@ManyToOne
	private User user;

}
