package integrationTesting;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class adminManagerTest {

    private Administrator admin;
    private Manager manager;

    @BeforeEach
    void setup() {
        manager = new Manager(
                "John", "Doe", LocalDate.now(), 123,
                "Address", "E1", "M001", "pass",
                "Manager", 1000,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()
        );

        admin = new Administrator(
                "Admin", "One", LocalDate.now(), 555,
                "HQ", "A001", "admin",
                "EMP1", "ADMIN", 2000,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }
    @Test
    void testAdministratorAddsManager() {
        admin.addManager(manager);

        assertEquals(1, admin.getManagers().size());
        assertEquals("M001", admin.getManagers().get(0).getManagerId());
    }

    @Test
    void testAdministratorRemovesManager() {
        admin.addManager(manager);
        admin.removeManager("M001");

        assertTrue(admin.getManagers().isEmpty());
    }
    @Test
    void testAdministratorModifiesManagerId() {
        admin.addManager(manager);

        admin.modifyManagerId("M002", "M001");

        assertEquals("M002", admin.getManagers().get(0).getManagerId());
    }

    @Test
    void testAdministratorRevokesManagerPermission() {
        admin.addManager(manager);

        admin.revokePermission("M001");

        assertFalse(admin.getManagers().get(0).hasPermissionToWork());
    }

    @Test
    void testAdministratorGivesManagerPermission() {
        admin.addManager(manager);
        admin.revokePermission("M001");

        admin.givePermission("M001");

        assertTrue(admin.getManagers().get(0).hasPermissionToWork());
    }
    @Test
    void testAdministratorHandlesMultipleManagers() {
        Manager manager2 = new Manager(
                "Jane", "Smith", LocalDate.now(), 456,
                "Address2", "E2", "M002", "pass2",
                "Manager", 1200,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()
        );

        admin.addManager(manager);
        admin.addManager(manager2);

        assertEquals(2, admin.getManagers().size());
    }

    @Test
    void testRemoveOneManagerKeepsOthers() {
        Manager manager2 = new Manager(
                "Jane", "Smith", LocalDate.now(), 456,
                "Address2", "E2", "M002", "pass2",
                "Manager", 1200,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()
        );

        admin.addManager(manager);
        admin.addManager(manager2);

        admin.removeManager("M001");

        assertEquals(1, admin.getManagers().size());
        assertEquals("M002", admin.getManagers().get(0).getManagerId());
    }

    @Test
    void testAdministratorLogin() {
        assertTrue(admin.logIn("A001", "admin"));
        assertFalse(admin.logIn("A001", "wrongPass"));
    }

    @Test
    void testAdministratorTaskDescription() {
        String task = admin.employeeTask();

        assertNotNull(task);
        assertTrue(task.contains("Administrates"));
    }
}
