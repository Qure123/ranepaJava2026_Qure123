package ru.ranepa.hrm.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import ru.ranepa.hrm.dto.*;
import ru.ranepa.hrm.model.Employee;
import ru.ranepa.hrm.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  public List<EmployeeResponseDto> getAll(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy) {
    return service.getAllPaged(page, size, sortBy)
      .stream()
      .map(this::toDto)
      .toList();
  }
  @GetMapping("/{id}")
  public EmployeeResponseDto getById(@PathVariable Long id) {
    return toDto(service.getById(id));
  }

  @PostMapping
  public EmployeeResponseDto create(@Valid @RequestBody EmployeeRequestDto dto) {
    return toDto(service.create(toEntity(dto)));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  @GetMapping("/position/{position}")
  public List<EmployeeResponseDto> byPosition(@PathVariable String position) {
    return service.getByPosition(position).stream().map(this::toDto).toList();
  }

  @GetMapping("/stats")
  public EmployeeStatsDto stats() {
    Employee top = service.findTopEarner();
    return new EmployeeStatsDto(
      service.calculateAverageSalary(),
      top != null ? toDto(top) : null
    );
  }

  private Employee toEntity(EmployeeRequestDto dto) {
    return new Employee(dto.name, dto.position, dto.salary, dto.hireDate);
  }

  private EmployeeResponseDto toDto(Employee e) {
    if (e == null) return null;
    return new EmployeeResponseDto(
      e.getId(),
      e.getName(),
      e.getPosition(),
      e.getSalary(),
      e.getHireDate()
    );
  }
}
