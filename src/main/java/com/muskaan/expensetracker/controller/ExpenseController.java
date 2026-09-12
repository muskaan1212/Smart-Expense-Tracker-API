package com.muskaan.expensetracker.controller;

import com.muskaan.expensetracker.model.CreateExpenseRequest;
import com.muskaan.expensetracker.model.Expense;
import com.muskaan.expensetracker.model.TotalResponse;
import com.muskaan.expensetracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
  private final ExpenseService service;
  public ExpenseController(ExpenseService service) { this.service = service; }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create an expense")
  public Expense create(@Valid @RequestBody CreateExpenseRequest request) { return service.create(request); }

  @GetMapping
  @Operation(summary = "List expenses", description = "Optionally filter by category, case-insensitively")
  public List<Expense> list(@RequestParam(required = false) String category) { return service.list(category); }

  @GetMapping("/total")
  @Operation(summary = "Calculate total expense amount")
  public TotalResponse total(@RequestParam(required = false) String category) { return new TotalResponse(service.total(category)); }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Delete an expense", responses = @ApiResponse(responseCode = "404", description = "Expense not found"))
  public void delete(@PathVariable long id) { service.delete(id); }
}
