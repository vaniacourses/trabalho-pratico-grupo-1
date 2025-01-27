package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("John Doe", "john.doe@example.com", "Manager");
    }

    // Testa se o nome é inicializado corretamente
    @Test
    void testNameInitialization() {
        assertEquals("John Doe", employee.name);
    }

    // Testa se o email é inicializado corretamente
    @Test
    void testEmailInitialization() {
        assertEquals("john.doe@example.com", employee.email);
    }

    // Testa se a designação do empregado é inicializada corretamente
    @Test
    void testEmployeeDesignationInitialization() {
        assertEquals("Manager", employee.employeeDesignation);
    }
}