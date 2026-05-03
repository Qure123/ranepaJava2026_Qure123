package ru.ranepa.hrm.dto;


import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeRequestDto {

  @NotBlank
  public String name;

  @NotBlank
  public String position;

  @NotNull
  @Positive
  public BigDecimal salary;

  @NotNull
  @PastOrPresent(message = "Hire date cannot be in the future")
  public LocalDate hireDate;
}
