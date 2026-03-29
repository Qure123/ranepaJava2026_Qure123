package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Employee {
    private Long id;
    private final String name;
    private final String position;
    private final BigDecimal salary;
    private final LocalDate hireDate;
    // Alt + insert - создание конструктора, alt+enter - подсказка
    public Employee(String name, String position, double salary, LocalDate hireDate) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Position must not be null");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        if (hireDate == null) {
            throw new IllegalArgumentException("Hire date must not be null");
        }
        this.name = name.trim();
        this.position = position.trim();
        this.salary = BigDecimal.valueOf(salary);
        this.hireDate = hireDate;
    }
    // toString() - в текст виде выводит в консоль данные объекта
    @Override //забрали из класса родителя и переопределили
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public String getPosition() {
        return position;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
}
