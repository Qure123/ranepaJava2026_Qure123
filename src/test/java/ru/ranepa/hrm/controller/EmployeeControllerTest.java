package ru.ranepa.hrm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldCreateEmployee() throws Exception {
    String json = """
        {
          "name":"Ivan",
          "position":"Dev",
          "salary":100000,
          "hireDate":"2024-01-01"
        }
        """;

    mockMvc.perform(post("/api/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(status().isOk());
  }

  @Test
  void shouldReturnEmployees() throws Exception {
    mockMvc.perform(get("/api/employees"))
      .andExpect(status().isOk());
  }

  @Test
  void shouldReturnStats() throws Exception {
    mockMvc.perform(get("/api/employees/stats"))
      .andExpect(status().isOk());
  }
  @Test
  void shouldReturnNotFoundForUnknownId() throws Exception {
    mockMvc.perform(get("/api/employees/999"))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnStatsWithEmptyDatabase() throws Exception {
    // БД пуста после @SpringBootTest с create-drop
    mockMvc.perform(get("/api/employees/stats"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.averageSalary").value(0.0))
            .andExpect(jsonPath("$.topEarner").doesNotExist());
  }
}
