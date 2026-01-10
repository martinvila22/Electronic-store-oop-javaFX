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

    @BeforeEach
    void setUp() {
        item = new Item("Milk", 1.0, 2.0, null, 10);
    }

    @Test
    void increaseStockValidTest() {
        item.increaseStock(5);
        assertEquals(15, item.getQuantity());
    }

    @Test
    void increaseStockInvalidTest() {
        assertThrows(IllegalArgumentException.class,
                () -> item.increaseStock(0));
    }

    @Test
    void decreaseStockValidTest() {
        item.decreaseStock(3);
        assertEquals(7, item.getQuantity());
        assertEquals(3, item.getNrOfItemsSold());
    }

    @Test
    void decreaseStockInvalidTest() {
        assertThrows(IllegalArgumentException.class,
                () -> item.decreaseStock(20));
    }

    @Test
    void itemOutOfStockWhenQuantityZeroTest() {
        item.decreaseStock(10);
        assertTrue(item.isItemOutOfStock());
    }

    @Test
    void applyDiscountValidTest() {
        item.applyDiscount(50);
        assertEquals(1.0, item.getSellingPrice());
    }

    @Test
    void applyDiscountInvalidTest() {
        assertThrows(IllegalArgumentException.class,
                () -> item.applyDiscount(150));
    }

    @Test
    void itemsWithSamePriceTest() {
        Item item2 = new Item("Bread", 0.5, 2.0, null, 5);

        List<Item> list = new ArrayList<>();
        list.add(item);
        list.add(item2);

        assertEquals(2, Item.itemsWithTheSamePrice(list, 2.0));
    }

    @Test
    void gettersTest() {
        assertEquals("Milk", item.getItemName());
        assertEquals(1.0, item.getPurchasePrice());
        assertEquals(2.0, item.getSellingPrice());
        assertEquals(10, item.getQuantity());
    }

    @Test
    void toStringContainsItemNameTest() {
        assertTrue(item.toString().contains("Milk"));
    }
}
