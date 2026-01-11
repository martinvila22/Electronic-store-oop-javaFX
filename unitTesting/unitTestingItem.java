package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Item;
import model.Supplier;

class unitTestingItem {

    private Item item;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        item = new Item("Laptop", 500.0, 800.0, supplier, 10);
    }

    @Test
    void testIncreaseStockValid() {
        item.increaseStock(5);
        assertEquals(15, item.getQuantity());
        assertFalse(item.isItemOutOfStock());
    }

    @Test
    void testIncreaseStockInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> item.increaseStock(0));
    }

    @Test
    void testDecreaseStockValid() {
        item.decreaseStock(3);
        assertEquals(7, item.getQuantity());
        assertEquals(3, item.getNrOfItemsSold());
    }

    @Test
    void testDecreaseStockInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> item.decreaseStock(20));
    }

    @Test
    void testDecreaseStockNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> item.decreaseStock(-1));
    }

    @Test
    void testOutOfStockAfterDecrease() {
        item.decreaseStock(10);
        assertTrue(item.isItemOutOfStock());
    }

    @Test
    void testApplyDiscountValid() {
        item.applyDiscount(25);
        assertEquals(600.0, item.getSellingPrice(), 0.001);
    }

    @Test
    void testApplyDiscountInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> item.applyDiscount(120));
    }

    @Test
    void testItemsWithSamePrice() {
        Item item2 = new Item("Phone", 300.0, 800.0, supplier, 5);
        Item item3 = new Item("Tablet", 200.0, 600.0, supplier, 8);

        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);
        items.add(item3);

        int result = Item.itemsWithTheSamePrice(items, 800.0);
        assertEquals(2, result);
    }

    @Test
    void testGetPurchaseDateDefensiveCopy() {
        assertNotSame(item.getPurchaseDate(), item.getPurchaseDate());
    }

    @Test
    void testSetQuantity() {
        item.setQuantity(2);
        assertEquals(2, item.getQuantity());
    }
}
