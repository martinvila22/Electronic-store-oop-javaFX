package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Bill;
import model.Cashier;
import model.Item;
import model.Sector;

class unitTestingBill
{

    private Bill bill;
    private Cashier cashier;
    private List<Item> items;
    private List<Integer> quantities;

    @BeforeEach
    void setUp() {
        // Prepare items
        items = new ArrayList<>();
        quantities = new ArrayList<>();

        Item item1 = new Item("TV", 100, 150, null, 10);
        Item item2 = new Item("Laptop", 500, 700, null, 5);

        items.add(item1);
        items.add(item2);

        quantities.add(2);
        quantities.add(1);

        // Minimal sector for cashier
        Sector sector = new Sector(new ArrayList<>(), "Electronics", new int[5], new ArrayList<>());

        cashier = new Cashier(
                "John", "Doe",
                LocalDate.of(1995, 5, 5),
                123456789,
                "Address",
                "c1",
                "pass",
                "eC1",
                "Cashier",
                900,
                sector,
                items
        );

        bill = new Bill(items, quantities, cashier);
    }

    // ---------------- CONSTRUCTOR BEHAVIOR ----------------

    @Test
    void testBillCreatedSuccessfully() {
        assertNotNull(bill);
    }

    @Test
    void testCreatedDateNotNull() {
        assertNotNull(bill.getCreated());
    }

    @Test
    void testCreatedByCashier() {
        assertEquals(cashier, bill.getCreatedBy());
    }

    @Test
    void testTotalAmountCalculatedCorrectly() {
        // (2 * 150) + (1 * 700) = 1000
        assertEquals(1000.0, bill.getTotalAmountOfBill());
    }

    // ---------------- GETTERS ----------------

    @Test
    void testGetBillNumber() {
        assertTrue(bill.getBillNumber() > 0);
    }

    @Test
    void testGetFileNotNull() {
        assertNotNull(bill.getFile());
    }

    // ---------------- addItem ----------------

    @Test
    void testAddItemIncreasesTotal() {
        Item newItem = new Item("Mouse", 20, 30, null, 10);
        bill.addItem(newItem, 2);

        assertEquals(1060.0, bill.getTotalAmountOfBill());
    }

    @Test
    void testAddItemWithNullDoesNothing() {
        double before = bill.getTotalAmountOfBill();
        bill.addItem(null, 2);
        assertEquals(before, bill.getTotalAmountOfBill());
    }

    @Test
    void testAddItemWithZeroQuantityDoesNothing() {
        double before = bill.getTotalAmountOfBill();
        bill.addItem(new Item("Keyboard", 30, 50, null, 5), 0);
        assertEquals(before, bill.getTotalAmountOfBill());
    }

    // ---------------- isProductDiscounted ----------------

    @Test
    void testIsProductDiscountedFalse() {
        assertFalse(bill.isProductDiscounted("TV"));
    }

    @Test
    void testIsProductDiscountedUnknownProduct() {
        assertFalse(bill.isProductDiscounted("Unknown"));
    }

    // ---------------- applyDiscount ----------------

    @Test
    void testApplyDiscountValid() {
        double discounted = bill.applyDiscount(10); // 10%
        assertEquals(900.0, discounted);
    }

    @Test
    void testApplyDiscountZeroPercent() {
        assertEquals(1000.0, bill.applyDiscount(0));
    }

    @Test
    void testApplyDiscountThrowsExceptionWhenNegative() {
        assertThrows(IllegalArgumentException.class, () -> bill.applyDiscount(-5));
    }

    @Test
    void testApplyDiscountThrowsExceptionWhenOver100() {
        assertThrows(IllegalArgumentException.class, () -> bill.applyDiscount(150));
    }

    // ---------------- assignCashier ----------------

    @Test
    void testAssignCashier() {
        Cashier newCashier = new Cashier(
                "Anna", "Smith",
                LocalDate.of(1998, 8, 8),
                999999,
                "New Address",
                "c2",
                "pass2",
                "eC2",
                "Cashier",
                950,
                null,
                new ArrayList<>()
        );

        bill.assignCashier(newCashier);
        assertEquals(newCashier, bill.getCreatedBy());
    }

    // ---------------- toString ----------------

    @Test
    void testToStringNotNull() {
        assertNotNull(bill.toString());
    }
}
