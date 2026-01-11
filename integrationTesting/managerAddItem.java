package integrationTesting;

import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class managerAddItem {

    @Test
    void testManagerAddsItemToSector() {
        Supplier supplier = new Supplier(true, "Supplier1");
        Item item = new Item("Bread", 0.5, 1.0, supplier, 5);

        Sector sector = new Sector(
                new ArrayList<>(),
                "Food",
                new int[]{},
                new ArrayList<>()
        );

        Manager manager = new Manager(
                "Eva", "Boss", java.time.LocalDate.now(), 999,
                "HQ", "E9", "M9", "pass",
                "Manager", 1500,
                List.of(sector), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()
        );

        manager.addProduct(item);

        assertEquals(1, sector.getItems().size());
        assertEquals("Bread", sector.getItems().get(0).getItemName());
    }
}
