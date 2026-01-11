package integrationTesting;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class cashierPermission {

    @Test
    void testRevokeAndGiveCashierPermission() {
        Cashier cashier = new Cashier(
                "Ana", "Smith", LocalDate.now(), 111,
                "Street", "C001", "pass",
                "C1", "CASHIER", 700
        );

        Administrator admin = new Administrator(
                "Admin", "One", LocalDate.now(), 555,
                "HQ", "A001", "admin",
                "EMP1", "ADMIN", 2000,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );

        admin.addCashier(cashier);
        admin.revokePermission("C1");

        assertFalse(cashier.hasPermissionToWork());

        admin.givePermission("C1");
        assertTrue(cashier.hasPermissionToWork());
    }
}
