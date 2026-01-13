package systemTesting;


import model.*;
import java.time.LocalDate;
import java.util.*;

public class productSale{

    public static void main(String[] args) {

        Supplier supplier = new Supplier(true, "MarketSupplier");
        Item juice = new Item("Juice", 1.0, 2.0, supplier, 15);

        Sector drinks = new Sector(
                List.of(juice),
                "Drinks",
                new int[]{15},
                List.of(supplier)
        );

        Cashier cashier = new Cashier(
                "Lina", "Cashier",
                LocalDate.of(2002, 8, 22),
                55555, "Store",
                "C200", "cash",
                "EMP-C200", "Cashier",
                750, drinks, List.of(juice)
        );

        System.out.println("Cashier login: " +
                cashier.logIn("EMP-C200", "cash"));

        Bill bill = cashier.createBillOneItem(juice, 3);

        System.out.println("Bill total: " + bill.getTotalAmountOfBill());
        System.out.println("Remaining stock: " + juice.getQuantity());
        System.out.println("Total for day: " + cashier.getTotalAmountForDay());

        cashier.endShift();
        System.out.println("Day of work count: " + cashier.getDayOfWork());
    }
}

