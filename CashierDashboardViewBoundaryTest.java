package view;

import model.Item;
import model.Supplier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class CashierDashboardViewBoundaryTest {

    private Item makeItem(String name, int qty) {
        return new Item(name, 10.0, 15.0, new Supplier(true, "TestSupplier"), qty);
    }



    @Test
    void nullListBoundary() {
        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(null, "Phone"));
    }

    @Test
    void emptyListBoundary() {
        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(new ArrayList<>(), "Phone"));
    }

    @Test
    void listWithNullItemBoundary() {
        List<Item> items = new ArrayList<>();
        items.add(null);

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, "Phone"));
    }

    @Test
    void itemWithNullNameBoundary() {
        List<Item> items = new ArrayList<>();
        items.add(makeItem(null, 5));

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, "Phone"));
    }

    @Test
    void searchNameIsNullBoundary() {
        List<Item> items = new ArrayList<>();
        items.add(makeItem("Phone", 5));

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, null));
    }

    @Test
    void searchNameIsEmptyBoundary() {
        List<Item> items = new ArrayList<>();
        items.add(makeItem("Phone", 5));

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, ""));
    }



    @Test
    void quantityZeroBoundary() {
        List<Item> items = new ArrayList<>();
        items.add(makeItem("Phone", 0));

        assertEquals(0,
                CashierDashboardView.getItemQuantityFromList(items, "Phone"));
    }

    @Test
    void quantityOneBoundary() {
        List<Item> items = new ArrayList<>();
        items.add(makeItem("Phone", 1));

        assertEquals(1,
                CashierDashboardView.getItemQuantityFromList(items, "Phone"));
    }
}
