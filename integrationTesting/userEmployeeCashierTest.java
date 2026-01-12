package integrationTesting;

import model.Cashier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class userEmployeeCashierTest {

    private Cashier cashier;

    @BeforeEach
    void setup() {
        cashier = new Cashier(
                "Anna",
                "Smith",
                LocalDate.of(2000, 5, 10),
                123456789,
                "Main Street",
                "anna01",
                "pass123",
                "EMP01",
                "Cashier",
                900,
                null,
                null
        );
    }

    // ===================== LOGIN INTEGRATION =====================
    @Test
    void testUserLoginIntegration() {
        assertTrue(cashier.logIn("anna01", "pass123"));
        assertFalse(cashier.logIn("anna01", "wrong"));
    }

    // ===================== MODIFY PASSWORD =====================
    @Test
    void testChangePasswordIntegration() {
        boolean changed = cashier.changePass("pass123", "newPass", "newPass");

        assertTrue(changed);
        assertTrue(cashier.logIn("anna01", "newPass"));
    }

    // ===================== MODIFY USER DATA =====================
    @Test
    void testModifyUserPersonalData() {
        cashier.setAddress("New Address");
        cashier.setphoneNr(999999999);
        cashier.changeSurname("Johnson");

        assertEquals("New Address", cashier.getAddress());
        assertEquals(999999999, cashier.getPhoneNr());
        assertEquals("Johnson", cashier.getSurname());
    }

    // ===================== MODIFY EMPLOYEE DATA =====================
    @Test
    void testModifyEmployeeData() {
        cashier.setSalary(1100);
        cashier.setRole("Senior Cashier");
        cashier.setEmployeeId("EMP99");

        assertEquals(1100, cashier.getSalary());
        assertEquals("Senior Cashier", cashier.getRole());
        assertEquals("EMP99", cashier.getEmployeeId());
    }

    // ===================== EMPLOYEE TASK INTEGRATION =====================
    @Test
    void testEmployeeTaskIntegration() {
        String task = cashier.employeeTask();

        assertNotNull(task);
        assertFalse(task.isEmpty());
    }
}
