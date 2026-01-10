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
        item1 = new Item("Milk", 1.0, 1.5, null, 10);
        item2 = new Item("Bread", 0.5, 1.0, null, 20);

        List<Item> items = new ArrayList<>();
        items.add(item1);

        inventory = new Inventory(items);
    }

    @Test
    void getNrofItemsTest() {
        assertEquals(1, inventory.getNrofItems());
    }

    @Test
    void getSoldQuantityTotalInitiallyZeroTest() {
        assertEquals(0, inventory.getSoldQuantityTotal());
    }

    @Test
    void getItemsReturnsUnmodifiableListTest() {
        List<Item> items = inventory.getItems();
        assertThrows(UnsupportedOperationException.class,
                () -> items.add(item2));
    }

    @Test
    void addNewItemIncreasesItemCountTest() {
        inventory.addNewItem(item2);
        assertEquals(2, inventory.getNrofItems());
    }

    @Test
    void addNewItemNullDoesNothingTest() {
        inventory.addNewItem(null);
        assertEquals(1, inventory.getNrofItems());
    }

    @Test
    void giveReportBeforeCreationDateReturnsZero() {
        Date pastDate = new Date(0); // 1970
        assertEquals(0.0, inventory.giveReportAfterPeriod(pastDate));
    }

    @Test
    void giveReportAfterCreationDateReturnsTotalSales() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000);
        assertEquals(0.0, inventory.giveReportAfterPeriod(futureDate));
    }
}
