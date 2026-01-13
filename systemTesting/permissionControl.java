package systemTesting;

import model.*;
import java.time.LocalDate;
import java.util.*;

public class permissionControl {

    public static void main(String[] args) {

        Supplier supplier = new Supplier(true, "Supplier1");
        Item item = new Item("Bread", 0.5, 1.0, supplier, 10);
        Sector sector = new Sector(List.of(item), "Bakery", new int[]{10}, List.of(supplier));

        Cashier cashier = new Cashier(
                "Eva", "Cashier",
                LocalDate.of(2001, 4, 12),
                12345, "City",
                "C100", "pass",
                "EMP-C100", "Cashier", 700,
                sector, List.of(item)
        );

        Administrator admin = new Administrator(
                "Main", "Admin",
                LocalDate.of(1980, 1, 1),
                11111, "HQ",
                "A100", "admin",
                "EMP-A100", "Admin", 2000,
                new ArrayList<>(),
                List.of(cashier),
                List.of(sector)
        );

        System.out.println("Admin login: " + admin.logIn("A100", "admin"));

        admin.revokePermission("EMP-C100");
        System.out.println("Permission after revoke: " + cashier.hasPermissionToWork());

        admin.givePermission("EMP-C100");
        System.out.println("Permission after give: " + cashier.hasPermissionToWork());
    }
}
