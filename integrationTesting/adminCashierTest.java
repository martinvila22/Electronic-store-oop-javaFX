package integrationTesting;


import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class adminCashierTest {

    private Administrator admin;
    private Cashier cashier;

    @BeforeEach
    void setup() {
        cashier = new Cashier(
                "Anna", "Smith", LocalDate.now(), 123,
                "Address", "C001", "pass",
                "EMP2", "CASHIER", 800
        );

        admin = new Administrator(
                "Admin", "One", LocalDate.now(), 555,
                "HQ", "A001", "admin",
                "EMP1", "ADMIN", 2000,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }

    @Test
    void testAdminAddsAndRemovesCashier() {
        admin.addCashier(cashier);
        assertEquals(1, admin.getCashiers().size());

        admin.removeCashier("C001");
        assertEquals(0, admin.getCashiers().size());
    }

    @Test
    void testAdminRevokesAndGivesPermissionToCashier() {
        admin.addCashier(cashier);

        admin.revokePermission("C001");
        assertFalse(admin.getCashiers().get(0).hasPermissionToWork());

        admin.givePermission("C001");
        assertTrue(admin.getCashiers().get(0).hasPermissionToWork());
    }

    @Test
    void testAdminModifiesCashierId() {
        admin.addCashier(cashier);

        admin.modifyCashierId("C002", "C001");

        assertEquals("C002", admin.getCashiers().get(0).getCashierId());
    }
}
