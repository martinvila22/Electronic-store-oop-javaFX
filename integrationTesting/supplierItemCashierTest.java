package integrationTesting;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class supplierItemCashierTest {

    private Supplier supplier;
    private Item item;
    private Cashier cashier;
    private Sector sector;

    @BeforeEach
    void setup() {
        supplier = new Supplier(true, "Local Supplier");

        item = new Item(
                "Milk",
                0.8,
                1.5,
                supplier,
                20
        );

        sector = new Sector(
                List.of(item),
                "Dairy",
                new int[]{20},
                List.of(supplier)
        );

        cashier = new Cashier(
                "John",
                "Doe",
                LocalDate.now(),
                222,
                "Main Street",
                "C1",
                "pass",
                "EMP10",
                "Cashier",
                850,
                sector,
                List.of(item)
        );
    }
    @Test
    void testSupplierLinkedToItem() {
        assertEquals(supplier, item.getSupplier());
        assertTrue(supplier.getIsSupplierForStore());
    }

    @Test
    void testSupplierUpdatedAfterSale() {
        cashier.createBillOneItem(item, 5);
        assertEquals(15, item.getQuantity());
        supplier.addTotalNrOfProductSold(5);
        assertDoesNotThrow(() -> supplier.addTotalNrOfProductSold(1));
    }

    @Test
    void testModifySupplierData() {
        supplier.setName("Updated Supplier");
        supplier.setIsSupplierForStore(false);

        assertEquals("Updated Supplier", supplier.getName());
        assertFalse(supplier.getIsSupplierForStore());
    }

}

