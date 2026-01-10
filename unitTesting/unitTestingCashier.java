package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class unitTestingCashier{

    private Cashier cashier;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        item1 = new Item("Milk", 1.0, 1.5, null, 10);
        item2 = new Item("Bread", 0.5, 1.0, null, 20);

        cashier = new Cashier(
                "John",
                "Doe",
                LocalDate.of(2000, 1, 1),
                123456789,
                "Street 1",
                "cash01",
                "pass123",
                "emp01",
                "CASHIER",
                500.0
        );
    }

    @Test
    void constructorTest() {
        assertEquals("cash01", cashier.getCashierId());
        assertTrue(cashier.hasPermissionToWork());
        assertEquals(0, cashier.getBillsCount());
        assertEquals(0.0, cashier.getTotalAmountForDay());
    }

    @Test
    void permissionTest() {
        cashier.setPermission(false);
        assertFalse(cashier.hasPermissionToWork());
    }

    @Test
    void loginSuccessTest() {
        assertTrue(cashier.logIn("cash01", "pass123"));
    }

    @Test
    void loginFailTest() {
        assertFalse(cashier.logIn("cash01", "wrong"));
    }

    @Test
    void createBillOneItemTest() {
        Bill bill = cashier.createBillOneItem(item1, 2);

        assertNotNull(bill);
        assertEquals(1, cashier.getBillsCount());
        assertTrue(cashier.getTotalAmountForDay() > 0);
    }

    @Test
    void createBillMultipleItemsTest() {
        List<Item> items = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        items.add(item1);
        items.add(item2);
        quantities.add(1);
        quantities.add(2);

        Bill bill = cashier.createBill(items, quantities);

        assertNotNull(bill);
        assertEquals(1, cashier.getBillsCount());
    }

    @Test
    void createBillInvalidListsTest() {
        List<Item> items = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        items.add(item1);
        quantities.add(1);
        quantities.add(2);

        assertThrows(IllegalArgumentException.class,
                () -> cashier.createBill(items, quantities));
    }

    @Test
    void addAmountTest() {
        cashier.addAmount(50.0);

        assertEquals(50.0, cashier.getTotalAmountForDay());
        assertEquals(50.0, cashier.getTotalAmountWon());
    }

    @Test
    void resetTotalForDayTest() {
        cashier.addAmount(30.0);
        cashier.resetTotalForDay();

        assertEquals(0.0, cashier.getTotalAmountForDay());
    }

    @Test
    void startShiftTest() {
        cashier.addAmount(100.0);
        cashier.startShift();

        assertEquals(0.0, cashier.getTotalAmountForDay());
    }

    @Test
    void endShiftTest() {
        int dayBefore = cashier.getDayOfWork();
        cashier.endShift();

        assertEquals(dayBefore + 1, cashier.getDayOfWork());
    }
}

