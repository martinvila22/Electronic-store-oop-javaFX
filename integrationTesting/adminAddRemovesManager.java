package integrationTesting;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class adminAddRemovesManager{

    @Test
    void testAdministratorAddsAndRemovesManager() {
        Manager manager = new Manager(
                "John", "Doe", LocalDate.now(), 123,
                "Address", "E1", "M001", "pass",
                "Manager", 1000,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()
        );

        Administrator admin = new Administrator(
                "Admin", "One", LocalDate.now(), 555,
                "HQ", "A001", "admin",
                "EMP1", "ADMIN", 2000,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );

        admin.addManager(manager);
        assertEquals(1, admin.getManagers().size());

        admin.removeManager("M001");
        assertEquals(0, admin.getManagers().size());
    }
}
