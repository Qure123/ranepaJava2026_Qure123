package ru.ranepa.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class EmployeeServiceTest {
    private EmployeeService service;
    private EmployeeRepository repository;
    // изоляция тестов
    @BeforeEach
    void setUp() {
        repository = new EmployeeRepositoryImpl();
        service = new EmployeeService(repository);
    }
    //Тест по расчёту средней зарплаты
    @Test
    void shouldCalculateAverageSalary() {
        repository.save(new Employee("A", "Dev", 100, LocalDate.now()));
        repository.save(new Employee("B", "Dev", 200, LocalDate.now()));
        repository.save(new Employee("C", "Dev", 300, LocalDate.now()));
        BigDecimal avg = service.calculateAverageSalary();
        assertEquals(0, BigDecimal.valueOf(200.00).compareTo(avg));
    }
    //Тест по поиску самого высокооплачиваемого
    @Test
    void shouldFindTopEarner() {
        repository.save(new Employee("Low", "Dev", 100, LocalDate.now()));
        repository.save(new Employee("High", "Dev", 500, LocalDate.now()));
        var top = service.findTopEarner();
        assertTrue(top.isPresent(), "Топ-сотрудник должен быть найден");
        assertEquals("High", top.get().getName());
        assertEquals(0, BigDecimal.valueOf(500).compareTo(top.get().getSalary()));
    }
    //Пустой список → средняя зарплата = 0
    @Test
    void shouldReturnZeroForAverageWhenNoEmployees() {
        BigDecimal avg = service.calculateAverageSalary();
        assertEquals(BigDecimal.ZERO, avg);
    }
    // Тест сортировки по дате
    @Test
    void shouldSortByHireDate() {
        repository.save(new Employee("C", "Dev", 100, LocalDate.of(2024, 3, 1)));
        repository.save(new Employee("A", "Dev", 200, LocalDate.of(2024, 1, 1)));
        repository.save(new Employee("B", "Dev", 300, LocalDate.of(2024, 2, 1)));
        List<Employee> sorted = service.sortByHireDate();
        assertEquals("A", sorted.get(0).getName());
        assertEquals("B", sorted.get(1).getName());
        assertEquals("C", sorted.get(2).getName());
    }
}

