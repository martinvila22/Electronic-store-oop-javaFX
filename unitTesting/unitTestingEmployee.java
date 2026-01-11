package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Employee;

class unitTestingEmployee {

    private Employee employee;

    static class TestEmployee extends Employee {

        public TestEmployee(String name, String surname, LocalDate dob, int phone,
                            String address, String username, String password,
                            String employeeId, String role, double salary) {
            super(name, surname, dob, phone, address, username, password, employeeId, role, salary);
        }

        @Override
        public String employeeTask() {
            return "Test task";
        }

        @Override
        public String EmployeeTask() {
            return "Test Task Description";
        }

        @Override
        public boolean logIn(String username, String password) {
            return false;
        }
    }

    @BeforeEach
    void setUp() {
        employee = new TestEmployee(
                "John",
                "Doe",
                LocalDate.of(1995, 5, 5),
                123456789,
                "Address",
                "john",
                "pass",
                "EMP01",
                "Tester",
                1200
        );
    }

    @Test
    void testGetEmployeeId() {
        assertEquals("EMP01", employee.getEmployeeId());
    }

    @Test
    void testSetEmployeeId() {
        employee.setEmployeeId("EMP99");
        assertEquals("EMP99", employee.getEmployeeId());
    }

    @Test
    void testGetRole() {
        assertEquals("Tester", employee.getRole());
    }

    @Test
    void testSetRole() {
        employee.setRole("Senior Tester");
        assertEquals("Senior Tester", employee.getRole());
    }

    @Test
    void testGetSalary() {
        assertEquals(1200, employee.getSalary(), 0.001);
    }

    @Test
    void testSetSalary() {
        employee.setSalary(1500);
        assertEquals(1500, employee.getSalary(), 0.001);
    }

    @Test
    void testEmployeeTask() {
        assertEquals("Test task", employee.employeeTask());
    }

    @Test
    void testEmployeeTaskCapitalized() {
        assertEquals("Test Task Description", employee.EmployeeTask());
    }

    @Test
    void testToString() {
        String result = employee.toString();
        assertNotNull(result);
        assertTrue(result.contains("employeeId"));
        assertTrue(result.contains("role"));
        assertTrue(result.contains("salary"));
    }
}
