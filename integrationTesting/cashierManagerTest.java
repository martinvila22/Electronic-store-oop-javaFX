package integrationTesting;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class cashierManagerTest {

    private Manager manager;
    private Cashier cashier;

    @BeforeEach
    void setUp() {

        cashier = new Cashier(
                "John",
                "Doe",
                LocalDate.now(),
                123456,
                "Main Street",
                "C001",
                "cashierPass",
                "EMP_C1",
                "CASHIER",
                800
        );

        manager = new Manager(
                "Anna",
                "Smith",
                LocalDate.now(),
                987654,
                "HQ Office",
                "EMP_M1",
                "M001",
                "managerPass",
                "MANAGER",
                1500,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    void testManagerAddsCashier() {
        manager.addCashier(cashier);

        assertEquals(1, manager.getCashiers().size());
        assertEquals("C001", manager.getCashiers().get(0).getCashierId());
    }

    @Test
    void testManagerRemovesCashier() {
        manager.addCashier(cashier);
        manager.removeCashier("C001");

        assertTrue(manager.getCashiers().isEmpty());
    }

    @Test
    void testManagerRatesCashierWithoutSales() {
        manager.addCashier(cashier);

        String rating = manager.rateCashier("C001");

        assertNotNull(rating);
        assertTrue(
                rating.equals("Amazing work done") ||
                        rating.equals("Good work done") ||
                        rating.equals("Bad work done")
        );
    }

    @Test
    void testManagerRatesCashierAfterSales() {
        manager.addCashier(cashier);

        cashier.addAmount(500);

        String rating = manager.rateCashier("C001");

        assertNotNull(rating);
    }

    @Test
    void testCashierSalesAffectManagerEvaluation() {
        manager.addCashier(cashier);

        cashier.addAmount(300);
        cashier.addAmount(200);

        assertTrue(cashier.getTotalAmountWon() > 0);

        String rating = manager.rateCashier("C001");
        assertNotNull(rating);
    }

    @Test
    void testManagerHandlesMultipleCashiers() {
        Cashier cashier2 = new Cashier(
                "Jane",
                "Brown",
                LocalDate.now(),
                555555,
                "Second Street",
                "C002",
                "pass2",
                "EMP_C2",
                "CASHIER",
                850
        );

        manager.addCashier(cashier);
        manager.addCashier(cashier2);

        manager.removeCashier("C001");

        assertEquals(1, manager.getCashiers().size());
        assertEquals("C002", manager.getCashiers().get(0).getCashierId());
    }
}

