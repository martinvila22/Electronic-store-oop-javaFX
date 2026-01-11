package integrationTesting;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class supplierNotifyForStock{

    @Test
    void testNotifySupplierAndIncreaseStock() {
        Supplier supplier = new Supplier(true, "SupplierX");
        Item item = new Item("Sugar", 1.0, 1.5, supplier, 2); // low stock

        Sector sector = new Sector(
                List.of(item),
                "Food",
                new int[]{2},
                List.of(supplier)
        );

        Manager manager = new Manager(
                "John", "Manager", LocalDate.now(), 111,
                "HQ", "EMP10", "M10", "pass",
                "Manager", 1500,
                List.of(sector),
                new ArrayList<>(),
                List.of(supplier),
                List.of(item)
        );

        manager.notifySupplierToIncreaseStock(5);

        assertTrue(item.getQuantity() >= 7);
    }
}

