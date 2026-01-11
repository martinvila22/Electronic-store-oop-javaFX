package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Inventory;
import model.Item;

class unitTestingInventory {

    private Inventory inventory;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        item1 = new Item("Milk", 1.0, 2.0, null, 10);
        item2 = new Item("Bread", 0.5, 1.5, null, 20);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        inventory = new Inventory(items);
    }

    @Test
    void testInitialNumberOfItems() {
        assertEquals(2, inventory.getNrofItems());
    }

    @Test
    void testInitialSoldQuantityTotal() {
        assertEquals(0, inventory.getSoldQuantityTotal());
    }

    @Test
    void testGetItemsUnmodifiable() {
        assertEquals(2, inventory.getItems().size());
        assertThrows(UnsupportedOperationException.class,
                () -> inventory.getItems().add(item1));
    }

    @Test
    void testAddNewItem() {
        Item item3 = new Item("Cheese", 2.0, 4.0, null, 5);
        inventory.addNewItem(item3);

        assertEquals(3, inventory.getNrofItems());
        assertEquals(3, inventory.getItems().size());
    }

    @Test
    void testAddNewItemNull() {
        inventory.addNewItem(null);
        assertEquals(2, inventory.getNrofItems());
    }

    @Test
    void testGiveReportAfterPeriodBeforeCreationDate() {
        Date pastDate = new Date(System.currentTimeMillis() - 100000);
        assertEquals(0.0, inventory.giveReportAfterPeriod(pastDate), 0.001);
    }

    @Test
    void testGiveReportAfterPeriodAfterCreationDate() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000);
        assertEquals(0.0, inventory.giveReportAfterPeriod(futureDate), 0.001);
    }
}
