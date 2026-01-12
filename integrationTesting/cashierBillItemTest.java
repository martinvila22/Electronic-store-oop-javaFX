package integrationTesting;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class cashierBillItemTest {

    private Cashier cashier;
    private Item item;
    private Supplier supplier;
    private Sector sector;

    @BeforeEach
    void setup() {
        supplier = new Supplier(true, "Supplier1");

        item = new Item(
                "Milk",
                0.6,
                1.2,
                supplier,
                10
        );

        sector = new Sector(
                List.of(item),
                "Dairy",
                new int[]{10},
                List.of(supplier)
        );

        cashier = new Cashier(
                "John",
                "Doe",
                LocalDate.now(),
                123,
                "Street 1",
                "C1",
                "pass",
                "EMP1",
                "Cashier",
                800,
                sector,
                List.of(item)
        );
    }

    // ===================== CREATE BILL =====================
    @Test
    void testCashierCreatesBillWithOneItem() {
        Bill bill = cashier.createBillOneItem(item, 2);

        assertNotNull(bill);
        assertEquals(cashier, bill.getCreatedBy());
    }

    // ===================== BILL TOTAL =====================
    @Test
    void testBillTotalAmountIsCorrect() {
        Bill bill = cashier.createBillOneItem(item, 2);

        assertEquals(2 * item.getSellingPrice(),
                bill.getTotalAmountOfBill(),
                0.001);
    }

    // ===================== ITEM STOCK UPDATE =====================
    @Test
    void testItemStockDecreasesAfterBillCreation() {
        cashier.createBill(List.of(item), List.of(3));

        assertEquals(7, item.getQuantity());
    }

    // ===================== CASHIER STATE UPDATE =====================
    @Test
    void testCashierTotalForDayUpdated() {
        cashier.createBillOneItem(item, 2);

        assertEquals(2 * item.getSellingPrice(),
                cashier.getTotalAmountForDay(),
                0.001);
    }

    // ===================== BILL COUNT =====================
    @Test
    void testCashierBillCountIncreases() {
        cashier.createBillOneItem(item, 1);
        cashier.createBillOneItem(item, 1);

        assertEquals(2, cashier.getBillsCount());
    }
}
