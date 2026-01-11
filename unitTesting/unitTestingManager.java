package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class unitTestingManager {

    private Manager manager;
    private Cashier cashier;
    private Supplier supplier;
    private Item item;
    private Sector sector;

    @BeforeEach
    void setUp() {

        // ✅ SAME FIX AS SECTOR
        new java.io.File("src/dao").mkdirs();

        // ✅ Supplier MUST exist
        supplier = new Supplier(true, "Tea");

        cashier = new Cashier(
                "Ana", "Test", LocalDate.now(), 123,
                "Addr", "cash1", "pass", "C1", "CASHIER", 500
        );

        item = new Item("Milk", 1.0, 1.5, supplier, 10);

        sector = new Sector(
                new ArrayList<>(),
                "Food",              // ✅ match tests
                new int[5],
                new ArrayList<>()
        );

        sector.addNewItem(item);

        List<Sector> sectors = new ArrayList<>();
        sectors.add(sector);

        List<Cashier> cashiers = new ArrayList<>();
        cashiers.add(cashier);

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier);

        List<Item> items = new ArrayList<>();
        items.add(item);

        manager = new Manager(
                "John", "Doe", LocalDate.now(), 123,
                "Address", "E1", "M1", "pass",
                "MANAGER", 1000,
                sectors, cashiers, suppliers, items
        );
    }

    @Test
    void testAddCashier() {
        Cashier c2 = new Cashier("Bob", "Test", LocalDate.now(), 111,
                "Addr", "cash2", "pass", "C2", "CASHIER", 500);

        manager.addCashier(c2);
        assertEquals(2, manager.getCashiers().size());
    }

    @Test
    void testRemoveCashier() {
        manager.removeCashier("C1");
        assertEquals(0, manager.getCashiers().size());
    }

    @Test
    void testAddSector() {
        Sector sec2 = new Sector(new ArrayList<>(), "Warehouse", new int[5], new ArrayList<>());
        manager.addSector(sec2);
        assertEquals(2, manager.getSectors().size());
    }

    @Test
    void testRemoveSector() {
        boolean removed = manager.removeSector("Food");
        assertTrue(removed);
        assertEquals(0, manager.getSectors().size());
    }

    @Test
    void testIsOneSectorEmpty() {
        assertTrue(manager.isOneSectorEmpty("Food"));
    }

    @Test
    void testIsAnySectorEmpty() {
        Sector result = manager.isAnySectorEmpty();
        assertNotNull(result);
    }

    @Test
    void testIncreaseStock() {
        manager.increaseStock(item, 5);
        assertEquals(15, item.getQuantity());
    }

    @Test
    void testAddSupplier() {

        Supplier s2 = new Supplier(true , "tea");

        manager.addSupplier(s2);

        assertEquals(2, manager.getSuppliers().size());
        assertTrue(manager.getSuppliers().contains(s2));
    }



    @Test
    void testLogInValid() {
        assertTrue(manager.logIn("M1", "pass"));
    }

    @Test
    void testLogInInvalid() {
        assertFalse(manager.logIn("wrong", "pass"));
    }

    @Test
    void testRateCashier() {
        cashier.addTotalAmountWon(3000);
        cashier.addTotalAmountForDay(200);

        String result = manager.rateCashier("C1");
        assertNotNull(result);
    }

    @Test
    void testRemoveItem() {
        manager.removeItem("Milk");
        assertEquals(0, manager.getItems().size());
    }

    @Test
    void testPermissionToWork() {
        manager.setPermissionToWork(false);
        assertFalse(manager.hasPermissionToWork());
    }

    @Test
    void testTotalAmountSpent() {
        manager.addTotalAmountSpent(100);
        assertEquals(100, manager.getTotalAmountSpent());
    }

    @Test
    void testEmployeeTask() {
        assertNotNull(manager.employeeTask());
    }
}
