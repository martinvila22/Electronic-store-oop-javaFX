package integrationTesting;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class inventoryItemCashierTest {

    private Inventory inventory;
    private Item item;
    private Supplier supplier;
    private Cashier cashier;
    private Sector sector;

    @BeforeEach
    void setup() {
        supplier = new Supplier(true, "Supplier1");

        item = new Item(
                "Sugar",
                0.7,
                1.5,
                supplier,
                10
        );

        inventory = new Inventory(List.of(item));

        sector = new Sector(
                List.of(item),
                "Food",
                new int[]{10},
                List.of(supplier)
        );

        cashier = new Cashier(
                "Anna",
                "Smith",
                LocalDate.now(),
                111,
                "Street 5",
                "C5",
                "pass",
                "EMP5",
                "Cashier",
                900,
                sector,
                List.of(item)
        );
    }

    // ===================== INVENTORY CREATION =====================
    @Test
    void testInventoryInitialState() {
        assertEquals(1, inventory.getNrofItems());
        assertEquals(0, inventory.getSoldQuantityTotal());
        assertEquals(1, inventory.getItems().size());
    }

    // ===================== SALE INTEGRATION =====================
    @Test
    void testInventoryAfterCashierSale() {
        cashier.createBill(List.of(item), List.of(3));

        // Item stock decreased → shared object
        assertEquals(7, item.getQuantity());

        // Inventory still contains the item
        assertTrue(inventory.getItems().contains(item));
    }

    // ===================== REPORT AFTER PERIOD =====================
    @Test
    void testInventoryReportAfterDate() {
        cashier.createBillOneItem(item, 2);

        Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        Date tomorrow = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);

        assertEquals(0.0, inventory.giveReportAfterPeriod(yesterday));
        assertEquals(0.0, inventory.giveReportAfterPeriod(tomorrow));
    }
}
