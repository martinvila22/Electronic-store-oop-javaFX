package systemTesting;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import control.AdministratorDashboardController;
import control.ManagerController;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import view.AdministratorDashboardView;
import view.ManagerDashboardView;

class systemTest {

    private Supplier supplier;
    private Item item;
    private Item secondItem;
    private ArrayList<Item> items;
    private Sector sector;
    private ArrayList<Sector> sectors;
    private Cashier cashier;
    private Manager manager;
    private Administrator admin;
    private ManagerController managerController;
    private AdministratorDashboardController adminController;

    @BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                supplier = new Supplier(true, "Samsung");

                item = new Item("TV", 300, 450, supplier, 10);
                secondItem = new Item("Radio", 50, 80, supplier, 4);

                items = new ArrayList<>();
                items.add(item);
                items.add(secondItem);

                sector = new Sector(items, "Electronics", new int[]{10, 4}, List.of(supplier));
                sectors = new ArrayList<>();
                sectors.add(sector);

                cashier = new Cashier(
                        "Ana", "Smith", LocalDate.of(2000, 1, 1),
                        123456789, "Addr", "c1", "pass",
                        "c1" , "cashier", 1000
                );

                manager = new Manager(
                        "John", "Doe", LocalDate.of(1985, 5, 10),
                        123456789, "Addr",
                        "m1", "m1", "pass", "manager",
                        1200, sectors, List.of(cashier),
                        List.of(supplier), items
                );

                admin = new Administrator(
                        "Admin", "User", LocalDate.of(1990, 1, 1),
                        123456789, "Addr",
                        "admin", "pass", "admin",
                        "administrator", 15000,
                        List.of(manager), List.of(cashier), sectors
                );

                managerController =
                        new ManagerController(manager, new ManagerDashboardView(new Stage(), manager));

                Stage adminStage = new Stage();
                AdministratorDashboardView adminView =
                        new AdministratorDashboardView(adminStage, admin);

                AdministratorDashboardController adminController =
                        new AdministratorDashboardController(
                                adminStage,
                                admin,
                                adminView
                        );


            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }
//req9
    @Test
    void Requirement9() {
        int initialQty = item.getQuantity();
        item.decreaseStock(3);
        assertEquals(initialQty - 3, item.getQuantity());
        assertFalse(item.isItemOutOfStock());
        item.decreaseStock(item.getQuantity());

        assertTrue(item.isItemOutOfStock());
        assertEquals(0, item.getQuantity());
    }

    //req10
    @Test
    void Requirement10() {
        int initialSize = manager.getItems().size();

        Item laptop = new Item("Laptop", 500, 750, supplier, 6);
        manager.addItem(laptop);
        assertEquals(initialSize + 1, manager.getItems().size());
        assertTrue(
                manager.getItems().stream()
                        .anyMatch(i -> i.getItemName().equals("Laptop"))
        );
    }

    //req11
    @Test
    void Requirement11() {
        int oldQty = secondItem.getQuantity();


        manager.increaseStock(secondItem, 6);
        assertEquals(oldQty + 6, secondItem.getQuantity());
        manager.removeItem(secondItem.getItemName());
        assertFalse(manager.getItems().contains(secondItem));
    }

    //req12
    @Test
    void Requirement12() {
        Item phone = new Item("Phone", 200, 350, supplier, 5);

        sector.addNewItem(phone);
        assertTrue(sector.getItems().contains(phone));
        assertTrue(sector.getNrOfItems() >= phone.getQuantity());
    }

    //req13
    @Test
    void Requirement13() {
        secondItem.setQuantity(2);

        boolean isLow = manager.isOneSectorEmpty(sector.getSectorName());
        assertTrue(isLow);
        manager.notifySupplierToIncreaseStock(10);

        assertTrue(secondItem.getQuantity() > 2);
    }

    //req14
    @Test
    void Requirement14() {
        item.setQuantity(1);

        manager.increaseStock(item, 15);
        assertEquals(16, item.getQuantity());
        assertFalse(item.isItemOutOfStock());
    }

   //req15
    @Test
    void Requirement15() {
        Inventory inventory = new Inventory(items);

        Date before = new Date(System.currentTimeMillis() - 10000);
        Date now = new Date();

        double reportBefore = inventory.giveReportAfterPeriod(before);
        double reportNow = inventory.giveReportAfterPeriod(now);

        assertEquals(reportBefore, reportNow);
        assertTrue(reportNow >= 0);
    }

   //req16
    @Test
    void Requirement16() throws Exception {

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {

            AdministratorDashboardView adminView =
                    new AdministratorDashboardView(new Stage(), admin);

            AdministratorDashboardController adminController =
                    new AdministratorDashboardController(new Stage(), admin, adminView);

            admin.addManager(manager);
            admin.addCashier(cashier);

            adminController.populateLists();

            String managerEntry =
                    manager.getName() + "  " + manager.getSurname() + " - " + manager.getManagerId();

            adminView.getManagerListView()
                    .getSelectionModel()
                    .select(managerEntry);

            adminView.getRemoveManagerButton().fire();

            assertFalse(admin.getManagers().contains(manager));

            String cashierEntry =
                    cashier.getName() + " " + cashier.getSurname() + " - " + cashier.getCashierId();

            adminView.getCashierListView()
                    .getSelectionModel()
                    .select(cashierEntry);

            adminView.getRemoveCashierButton().fire();

            assertFalse(admin.getCashiers().contains(cashier));

            adminView.getManagerListView().getSelectionModel().clearSelection();
            adminView.getRemoveManagerButton().fire();

            adminView.getCashierListView().getSelectionModel().clearSelection();
            adminView.getRemoveCashierButton().fire();

            assertNotNull(admin);

            latch.countDown();
        });

        latch.await();
    }
}

