package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class unitTestingCashier {

    private Cashier cashier;
    private Item item1;
    private Item item2;
    private Sector sector;

    @BeforeEach
    void setUp() {
        List<Item> sectorItems = new ArrayList<>();


        item1 = new Item("Milk", 1.0, 2.0, null, 10);
        item2 = new Item("Bread", 0.5, 1.5, null, 20);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        cashier = new Cashier(
                "Anna",
                "Smith",
                LocalDate.of(1998, 5, 5),
                123456789,
                "Address",
                "C001",
                "pass",
                "EMP01",
                "Cashier",
                800,
                sector,
                items
        );
    }

    @Test
    void testGetCashierId() {
        assertEquals("C001", cashier.getCashierId());
    }

    @Test
    void testPermissionDefaultTrue() {
        assertTrue(cashier.hasPermissionToWork());
    }

    @Test
    void testSetPermissionFalse() {
        cashier.setPermission(false);
        assertFalse(cashier.hasPermissionToWork());
    }

    @Test
    void testAddAmount() {
        cashier.addAmount(100);
        assertEquals(100, cashier.getTotalAmountForDay(), 0.001);
        assertEquals(100, cashier.getTotalAmountWon(), 0.001);
    }

    @Test
    void testGetItems() {
        assertEquals(2, cashier.getItem().size());
    }

    @Test
    void testAddItem() {
        Item item3 = new Item("Cheese", 2.0, 4.0, null, 5);
        cashier.addItem(item3);
        assertEquals(3, cashier.getItem().size());
    }

    @Test
    void testCreateBillOneItem() {
        Bill bill = cashier.createBillOneItem(item1, 2);
        assertNotNull(bill);
        assertEquals(1, cashier.getBillsCount());
        assertTrue(cashier.getTotalAmountForDay() > 0);
    }

    @Test
    void testCreateBillOneItemInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> cashier.createBillOneItem(null, 1));
    }

    @Test
    void testCreateBillMultipleItems() {
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        List<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);

        Bill bill = cashier.createBill(items, quantities);
        assertNotNull(bill);
        assertEquals(1, cashier.getBillsCount());
    }

    @Test
    void testCreateBillInvalidLists() {
        assertThrows(IllegalArgumentException.class,
                () -> cashier.createBill(null, null));
    }

    @Test
    void testResetTotalForDay() {
        cashier.addAmount(200);
        cashier.resetTotalForDay();
        assertEquals(0, cashier.getTotalAmountForDay(), 0.001);
    }

    @Test
    void testStartShift() {
        cashier.addAmount(150);
        cashier.startShift();
        assertEquals(0, cashier.getTotalAmountForDay(), 0.001);
    }

    @Test
    void testEndShift() {
        int before = cashier.getDayOfWork();
        cashier.endShift();
        assertEquals(before + 1, cashier.getDayOfWork());
    }

    @Test
    void testGetBillsList() {
        assertNotNull(cashier.getbills());
    }

    @Test
    void testLogInSuccess() {
        assertTrue(cashier.logIn("EMP01", "pass"));
    }

    @Test
    void testLogInFail() {
        assertFalse(cashier.logIn("EMP01", "wrong"));
    }

    @Test
    void testEmployeeTask() {
        assertNotNull(cashier.EmployeeTask());
    }

    @Test
    void testToString() {
        assertNotNull(cashier.toString());
    }
}
