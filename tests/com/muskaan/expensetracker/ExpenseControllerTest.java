package com.muskaan.expensetracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "expense.storage.file=target/test-expenses.json")
class ExpenseControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper mapper;
  private final Path storage = Path.of("target/test-expenses.json");

  @BeforeEach void reset() throws Exception {
    for (long id = 1; id <= 20; id++) mvc.perform(delete("/api/expenses/" + id));
    Files.deleteIfExists(storage);
  }

  private String expense(String amount, String category, String description, String date) {
    return "{\"amount\":" + amount + ",\"category\":\"" + category + "\",\"description\":\"" + description + "\",\"date\":\"" + date + "\"}";
  }
  private void create(String body) throws Exception { mvc.perform(post("/api/expenses").contentType(APPLICATION_JSON).content(body)).andExpect(status().isCreated()); }

  @Test void createsAndListsExpense() throws Exception {
    create(expense("12.50", "Food", "Lunch", "2026-01-15"));
    mvc.perform(get("/api/expenses")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].amount", is(12.50)));
  }
  @Test void filtersCategoryCaseInsensitively() throws Exception {
    create(expense("10", "Food", "A", "2026-01-15")); create(expense("20", "Travel", "B", "2026-01-15"));
    mvc.perform(get("/api/expenses?category=food")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
  }
  @Test void calculatesOverallAndCategoryTotals() throws Exception {
    create(expense("10.25", "Food", "A", "2026-01-15")); create(expense("2.75", "Travel", "B", "2026-01-15"));
    mvc.perform(get("/api/expenses/total")).andExpect(jsonPath("$.total", is(13.00)));
    mvc.perform(get("/api/expenses/total?category=FOOD")).andExpect(jsonPath("$.total", is(10.25)));
  }
  @Test void deletesExpenseAndReturnsNotFoundWhenMissing() throws Exception {
    create(expense("10", "Food", "A", "2026-01-15"));
    mvc.perform(delete("/api/expenses/1")).andExpect(status().isNoContent());
    mvc.perform(delete("/api/expenses/1")).andExpect(status().isNotFound()).andExpect(jsonPath("$.error", is("Expense with id 1 was not found")));
  }
  @Test void rejectsInvalidAmountAndMissingRequiredFields() throws Exception {
    mvc.perform(post("/api/expenses").contentType(APPLICATION_JSON).content(expense("0", "", "", "2026-01-15"))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.fields", org.hamcrest.Matchers.aMapWithSize(4)));
  }
}
