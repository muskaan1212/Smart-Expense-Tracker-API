package com.muskaan.expensetracker.service;

public class ExpenseNotFoundException extends RuntimeException {
  public ExpenseNotFoundException(long id) { super("Expense with id " + id + " was not found"); }
}
