package com.reportGenerate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reportGenerate.entity.Expenses;
import com.reportGenerate.entity.Income;
import com.reportGenerate.entity.User;
import com.reportGenerate.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
        	log.info("user : {} ",user);
            User createdUser = userService.createUser(user);
            log.info("createdUser : {} ",createdUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
        	log.error("exception : {}",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/incomes")
    public ResponseEntity<Income> addIncome(@PathVariable Integer userId, @RequestBody Income income) {
        try {
        	
            Income createdIncome = userService.addIncome(userId, income);
            return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/expenses")
    public ResponseEntity<Expenses> addExpense(@PathVariable Integer userId, @RequestBody Expenses expense) {
        try {
            Expenses createdExpense = userService.addExpense(userId, expense);
            return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/{userId}/incomes")
    public ResponseEntity<List<Income>> getIncomesByUserId(@PathVariable("userId") Integer userId) {
        try {
            List<Income> incomes = userService.getIncomesByUserId(userId);
            return new ResponseEntity<>(incomes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}/expenses")
    public ResponseEntity<List<Expenses>> getExpensesByUserId(@PathVariable("userId") Integer userId) {
        try {
            List<Expenses> expenses = userService.getExpeseByUserId(userId);
            return new ResponseEntity<>(expenses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
