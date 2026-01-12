package integrationTesting;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class managerItemTest {

    private Manager manager;
    private Sector sector;
    private Item item;
    private Supplier supplier;

    @BeforeEach
    void setup() {
        supplier = new Supplier(true, "Supplier1");

        item = new Item(
                "Bread",
                0.5,
                1.0,
                supplier,
                10
        );

        sector = new Sector(
                new ArrayList<>(),
                "Food",
                new int[]{},
                new ArrayList<>()
        );

        manager = new Manager(
                "Eva",
                "Boss",
                LocalDate.now(),
                999,
                "HQ",
                "EMP1",
                "M1",
                "pass",
                "Manager",
                1500,
                List.of(sector),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    // ===================== ADD =====================
    @Test
    void testManagerAddsItemToSector() {
        manager.addProduct(item);

        assertEquals(1, sector.getItems().size());
        assertEquals("Bread", sector.getItems().get(0).getItemName());
    }

    // ===================== MODIFY =====================
    @Test
    void testManagerUpdatesItemQuantityInSector() {
        manager.addProduct(item);

        sector.updateItemQuantity(item, 5);

        int index = sector.getItems().indexOf(item);
        assertEquals(5, sector.getQuantities().get(index));
    }

    // ===================== REMOVE =====================
    @Test
    void testManagerRemovesItemFromSector() {
        manager.addProduct(item);

        manager.removeProductFromSector("Bread");

        assertTrue(sector.getItems().isEmpty());
    }
}
