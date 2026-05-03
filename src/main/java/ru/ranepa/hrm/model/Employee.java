package ru.ranepa.hrm.model;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String position;
  private BigDecimal salary;
  private LocalDate hireDate;

  protected Employee() {}

  public Employee(String name, String position, BigDecimal salary, LocalDate hireDate) {
    this.name = name;
    this.position = position;
    this.salary = salary;
    this.hireDate = hireDate;
  }

  @PrePersist
  public void prePersist() {
    if (hireDate == null) {
      hireDate = LocalDate.now();
    }
  }

  public Long getId() { return id; }
  public String getName() { return name; }
  public String getPosition() { return position; }
  public BigDecimal getSalary() { return salary; }
  public LocalDate getHireDate() { return hireDate; }
}
