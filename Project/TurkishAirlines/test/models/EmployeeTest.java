
package models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class EmployeeTest {

    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee("John Doe", "john.doe@example.com", "Manager");
    }

    @Test
    public void testNameInitialization() {
        assertEquals("John Doe", employee.name);
    }

    @Test
    public void testEmailInitialization() {
        assertEquals("john.doe@example.com", employee.email);
    }

    @Test
    public void testEmployeeDesignationInitialization() {
        assertEquals("Manager", employee.employeeDesignation);
    }
}
