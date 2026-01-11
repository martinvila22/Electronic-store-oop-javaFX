package integrationTesting;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class cashierBillStock {

    @Test
    void testCreateBillAndDecreaseItemStock() {
        Supplier supplier = new Supplier(true, "TestSupplier");
        Item item = new Item("Milk", 1.0, 1.5, supplier, 10);

        Cashier cashier = new Cashier(
                "Bob", "Cash", LocalDate.now(), 222,
                "Shop", "C002", "pass",
                "C2", "CASHIER", 800
        );

        Bill bill = cashier.createBill(
                List.of(item),
                List.of(2)
        );

        assertNotNull(bill);
        assertEquals(9, item.getQuantity());
        assertEquals(1, cashier.getBillsCount());
    }
}
