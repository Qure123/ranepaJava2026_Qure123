package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.time.LocalDate;

public class HrmApplication {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        Employee emp = new Employee(
                "Solovyev Sergey Sergeevich",
                "java-developer",
                300_000.0,
                LocalDate.of(2026, 2, 3)
        );
        System.out.println(emp);
        System.out.println(emp.getSalary());

        EmployeeRepositoryImpl employeeRepository= new EmployeeRepositoryImpl(); //объект репозитория
        System.out.println("-------");
        System.out.println(employeeRepository.save(emp));
        System.out.println("-------");
        var emp1 = employeeRepository.findById(1L)
                .orElseThrow();
        System.out.println("Employee was found: " + emp1);
    }
}