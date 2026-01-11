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
    private Manager manager1;
    private Cashier cashier1;
    private Sector sector1;

    @BeforeEach
    void setUp() {
        manager1 = new Manager("John", "Doe", LocalDate.of(1990, 1, 1),
                123456789, "Address", "M001", "pass", "EMP01", 121, "M1");

        cashier1 = new Cashier("Jane", "Smith", LocalDate.of(1995, 5, 5),
                987654321, "Address", "C001", "pass", "EMP02", "Cashier", 800);

        sector1 = new Sector(new ArrayList<>(), "Warehouse", new int[5], new ArrayList<>());

        List<Manager> managers = new ArrayList<>();
        managers.add(manager1);

        List<Cashier> cashiers = new ArrayList<>();
        cashiers.add(cashier1);

        List<Sector> sectors = new ArrayList<>();
        sectors.add(sector1);

        admin = new Administrator(
                "Admin",
                "One",
                LocalDate.of(1985, 3, 3),
                111222333,
                "Admin Address",
                "A001",
                "adminpass",
                "EMP00",
                "Administrator",
                2000,
                managers,
                cashiers,
                sectors
        );
    }

    @Test
    void testGetManagers() {
        assertEquals(1, admin.getManagers().size());
    }

    @Test
    void testGetCashiers() {
        assertEquals(1, admin.getCashiers().size());
    }

    @Test
    void testGetSectors() {
        assertEquals(1, admin.getSectors().size());
    }

    @Test
    void testAddManager() {
        Manager m2 = new Manager("Mike", "Brown", LocalDate.of(1992, 2, 2),
                123123123, "Addr", "M002", "pass", "EMP03", 123, "M2");

        admin.addManager(m2);
        assertEquals(2, admin.getManagers().size());
    }

    @Test
    void testAddCashier() {
        Cashier c2 = new Cashier("Anna", "White", LocalDate.of(1996, 6, 6),
                321321321, "Addr", "C002", "pass", "EMP04", "Cashier", 800);

        admin.addCashier(c2);
        assertEquals(2, admin.getCashiers().size());
    }

    @Test
    void testRemoveManager() {
        admin.removeManager("M001");
        assertEquals(0, admin.getManagers().size());
    }

    @Test
    void testRemoveCashier() {
        admin.removeCashier("C001");
        assertEquals(0, admin.getCashiers().size());
    }

    @Test
    void testModifyManagerId() {
        admin.modifyManagerId("M999", "M001");
        assertEquals("M999", admin.getManagers().get(0).getManagerId());
    }

    @Test
    void testModifyCashierId() {
        admin.modifyCashierId("C999", "C001");
        assertEquals("C999", admin.getCashiers().get(0).getCashierId());
    }

    @Test
    void testRevokePermissionManager() {
        admin.revokePermission("EMP01");
        assertFalse(admin.getManagers().get(0).isPermissionToWork());
    }

    @Test
    void testGivePermissionManager() {
        admin.givePermission("EMP01");
        assertTrue(admin.getManagers().get(0).isPermissionToWork());
    }

    @Test
    void testEmployeeTask() {
        assertNotNull(admin.employeeTask());
    }

    @Test
    void testLogInSuccess() {
        assertTrue(admin.logIn("A001", "adminpass"));
    }

    @Test
    void testLogInFail() {
        assertFalse(admin.logIn("A001", "wrongpass"));
    }

    @Test
    void testGetAdminId() {
        assertEquals("A001", admin.getAdminiId());
    }
}
