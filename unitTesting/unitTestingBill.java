package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class unitTestingBill {

    private Bill bill;
    private Item item1;
    private Item item2;
    private Cashier cashier;

    @BeforeEach
    void setUp() {
        cashier = new Cashier(
                "Anna",
                "Cashier",
                LocalDate.of(1998, 4, 4),
                123456789,
                "Address",
                "C001",
                "pass",
                "EMP10",
                "Cashier",
                800
        );

        item1 = new Item("Milk", 1.0, 2.0, null, 10);
        item2 = new Item("Bread", 0.5, 1.5, null, 20);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        List<Integer> quantities = new ArrayList<>();
        quantities.add(2);
        quantities.add(3);

        bill = new Bill(items, quantities, cashier);
    }

    @Test
    void testGetBillNumber() {
        assertTrue(bill.getBillNumber() >= 0);
    }

    @Test
    void testGetCreated() {
        Date created = bill.getCreated();
        assertNotNull(created);
    }

    @Test
    void testGetCreatedBy() {
        assertEquals(cashier, bill.getCreatedBy());
    }

    @Test
    void testGetTotalAmountOfBill() {
        double expected = (2.0 * 2) + (1.5 * 3);
        assertEquals(expected, bill.getTotalAmountOfBill(), 0.001);
    }

    @Test
    void testAddItem() {
        Item item3 = new Item("Cheese", 2.0, 4.0, null, 5);
        bill.addItem(item3, 2);

        double expectedTotal = (2.0 * 2) + (1.5 * 3) + (4.0 * 2);
        assertEquals(expectedTotal, bill.getTotalAmountOfBill(), 0.001);
    }

    @Test
    void testAddItemInvalidQuantity() {
        double before = bill.getTotalAmountOfBill();
        bill.addItem(item1, -1);
        assertEquals(before, bill.getTotalAmountOfBill(), 0.001);
    }

    @Test
    void testIsProductDiscountedFalse() {
        assertFalse(bill.isProductDiscounted("Milk"));
    }

    @Test
    void testIsProductDiscountedProductNotFound() {
        assertFalse(bill.isProductDiscounted("Chocolate"));
    }

    @Test
    void testApplyDiscountValid() {
        double discounted = bill.applyDiscount(10);
        double expected = bill.getTotalAmountOfBill() * 0.9;
        assertEquals(expected, discounted, 0.001);
    }

    @Test
    void testApplyDiscountZero() {
        double discounted = bill.applyDiscount(0);
        assertEquals(bill.getTotalAmountOfBill(), discounted, 0.001);
    }

    @Test
    void testApplyDiscountInvalidLow() {
        assertThrows(IllegalArgumentException.class, () -> bill.applyDiscount(-5));
    }

    @Test
    void testApplyDiscountInvalidHigh() {
        assertThrows(IllegalArgumentException.class, () -> bill.applyDiscount(150));
    }

    @Test
    void testAssignCashier() {
        Cashier newCashier = new Cashier(
                "Mark",
                "New",
                LocalDate.of(1997, 7, 7),
                987654321,
                "Addr",
                "C002",
                "pass",
                "EMP11",
                "Cashier",
                850
        );

        bill.assignCashier(newCashier);
        assertEquals(newCashier, bill.getCreatedBy());
    }

    @Test
    void testGetFile() {
        assertNotNull(bill.getFile());
        assertTrue(bill.getFile().exists());
    }

    @Test
    void testToString() {
        String result = bill.toString();
        assertNotNull(result);
        assertTrue(result.contains("totalAmount"));
    }
}
