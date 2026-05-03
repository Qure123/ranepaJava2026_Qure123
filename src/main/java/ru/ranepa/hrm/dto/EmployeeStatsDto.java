package ru.ranepa.hrm.dto;

import java.math.BigDecimal;

public class EmployeeStatsDto {
  public BigDecimal averageSalary;
  public EmployeeResponseDto topEarner;

  public EmployeeStatsDto(BigDecimal avg, EmployeeResponseDto top) {
    this.averageSalary = avg;
    this.topEarner = top;
  }
}
