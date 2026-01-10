package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Item;
import model.Sector;
import model.Supplier;

class unitTestingSector {

    private Sector sector;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setup() {
        Supplier supplier = new Supplier(true, "TechSupplier");

        item1 = new Item("Laptop", 500, 700, supplier, 5);
        item2 = new Item("Mouse", 10, 20, supplier, 0);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        int[] quantities = {5, 0};

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier);

        sector = new Sector(items, "Electronics", quantities, suppliers);
    }

    @Test
    void testIsSectorEmpty_false() {
        assertFalse(sector.isSectorEmpty());
    }

    @Test
    void testIsItemOutOfStock_true() {
        assertTrue(sector.isItemOutOfStock(item2));
    }

    @Test
    void testIsItemOutOfStock_false() {
        assertFalse(sector.isItemOutOfStock(item1));
    }

    @Test
    void testFirstOutOfStockItem() {
        Item outOfStock = sector.firstOutOfStockItem();
        assertEquals(item2, outOfStock);
    }

    @Test
    void testAddNewItem() {
        Supplier supplier = new Supplier(false, "OfficeSupplier");
        Item newItem = new Item("Keyboard", 20, 40, supplier, 3);

        sector.addNewItem(newItem);

        assertTrue(sector.getItems().contains(newItem));
        assertEquals(8, sector.getNrOfItems());
    }

    @Test
    void testDeleteItem() {
        sector.deleteItem(item1);

        assertFalse(sector.getItems().contains(item1));
        assertEquals(0, sector.getNrOfItems());
    }

    @Test
    void testUpdateItemQuantity() {
        sector.updateItemQuantity(item1, 10);

        int index = sector.getItems().indexOf(item1);
        assertEquals(10, sector.getQuantities().get(index));
        assertEquals(10, sector.getNrOfItems());
    }

    @Test
    void testGetSectorName() {
        assertEquals("Electronics", sector.getSectorName());
    }
}
