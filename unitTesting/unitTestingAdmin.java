package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class unitTestingAdmin {

    private Administrator admin;
    private Manager manager;
    private Cashier cashier;
    private Sector sector;

    @BeforeEach
    void setUp() {
        manager = new Manager(
                "John", "Doe",
                LocalDate.of(1985, 5, 10),
                123456789, "Addr1",
                "M1", "pass1",
                "eM1", 100 , "M1"
        );

        List<Item> items = new ArrayList<>();
        items.add(new Item("TV", 100, 120, null, 10));

        sector = new Sector(items, "Electronics", new int[5], new ArrayList<>());

        cashier = new Cashier(
                "Liam", "Kola",
                LocalDate.of(1999, 4, 12),
                111222333, "Addr2",
                "c1", "pass2",
                "eC1", "Cashier",
                900,
                sector, items
        );

        List<Manager> managers = new ArrayList<>();
        managers.add(manager);

        List<Cashier> cashiers = new ArrayList<>();
        cashiers.add(cashier);

        List<Sector> sectors = new ArrayList<>();
        sectors.add(sector);

        admin = new Administrator(
                "Admin", "User",
                LocalDate.of(1990, 1, 1),
                999999999, "AdminAddr",
                "admin1", "adminPass",
                "eA1", "Admin",
                1500,
                managers, cashiers, sectors
        );
    }

    @Test
    void testLoginSuccess() {
        assertTrue(admin.logIn("admin1", "adminPass"));
    }

    @Test
    void testLoginFail() {
        assertFalse(admin.logIn("admin1", "wrongPass"));
    }

    @Test
    void testGetManagers() {
        assertEquals(1, admin.getManagers().size());
    }

    @Test
    void testAddManager() {
        Manager m2 = new Manager(
                "Anna", "Smith",
                LocalDate.of(1990, 3, 3),
                222333444, "Addr3",
                "M2", "pass",
                "eM2", 1222,
                "M1"
        );

        admin.addManager(m2);
        assertEquals(2, admin.getManagers().size());
    }

    @Test
    void testRemoveManager() {
        admin.removeManager("m1");
        assertEquals(0, admin.getManagers().size());
    }

    @Test
    void testModifyManagerId() {
        admin.modifyManagerId("m99", "m1");
        assertEquals("m99", admin.getManagers().get(0).getManagerId());
    }

    @Test
    void testAddCashier() {
        Cashier c2 = new Cashier(
                "Eva", "Blue",
                LocalDate.of(2000, 1, 1),
                555666777, "Addr4",
                "c2", "pass",
                "eC2", "Cashier",
                950,
                sector, new ArrayList<>()
        );

        admin.addCashier(c2);
        assertEquals(2, admin.getCashiers().size());
    }

    @Test
    void testRemoveCashier() {
        admin.removeCashier("c1");
        assertEquals(0, admin.getCashiers().size());
    }

    @Test
    void testModifyCashierId() {
        admin.modifyCashierId("c99", "c1");
        assertEquals("c99", admin.getCashiers().get(0).getCashierId());
    }

    @Test
    void testRevokePermissionManager() {
        admin.revokePermission("M1");
        assertFalse(admin.getManagers().get(0).isPermissionToWork());
    }


    @Test
    void testGivePermissionCashier() {
        admin.revokePermission("eC1");
        admin.givePermission("eC1");
        assertTrue(admin.getCashiers().get(0).hasPermission());
    }

    @Test
    void testEmployeeTask() {
        assertNotNull(admin.employeeTask());
    }
}
