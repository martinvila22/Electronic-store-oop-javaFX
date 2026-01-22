package view;

import model.Item;
import model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CashierDashboardViewTest {

    @BeforeAll
    static void ensureDaoFolderExists() {
        // Item/Supplier constructors write to "src/dao/*.dat"
        new File("src/dao").mkdirs();
    }

    private Item makeItem(String name, int qty) {
        Supplier s = new Supplier(true, "TestSupplier");
        return new Item(name, 10.0, 15.0, s, qty);
    }

    @Test
    void returnsZeroWhenItemListIsNull() {
        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(null, "Phone"));
    }

    @Test
    void returnsQuantityWhenItemExists() {
        Item item = makeItem("Phone", 5);

        List<Item> items = new ArrayList<>();
        items.add(item);

        assertEquals(5,
                CashierDashboardView.getItemQuantityFromList(items, "Phone"));
    }

    @Test
    void returnsZeroWhenItemDoesNotExist() {
        Item item = makeItem("Laptop", 3);

        List<Item> items = new ArrayList<>();
        items.add(item);

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, "Mouse"));
    }

    @Test
    void returnsZeroWhenListContainsNullItem() {
        List<Item> items = new ArrayList<>();
        items.add(null);

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, "Phone"));
    }

    @Test
    void returnsZeroWhenListContainsItemWithNullName() {
        Supplier s = new Supplier(true, "TestSupplier");
        Item item = new Item(null, 10.0, 15.0, s, 5); // name is null from the start

        List<Item> items = new ArrayList<>();
        items.add(item);

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, "Phone"));
    }

}

