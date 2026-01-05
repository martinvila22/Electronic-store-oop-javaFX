package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class Task2partC {

    private Cashier cashier1;

    //setup all the objects neccessary to run the main class Cashier
    @BeforeEach
    void setUp() {
        Supplier sup1 = new Supplier(true, "Samsung");

        ArrayList<Item> items1 = new ArrayList<>();
        Item it1 = new Item("Tv", 350, 500, sup1, 40);
        items1.add(it1);

        ArrayList<Supplier> supplier = new ArrayList<>();
        supplier.add(sup1);

        int[] q = new int[5];
        for (int i = 0; i < q.length; i++) 
        {
            q[i] = 15;
        }

        Sector s1 = new Sector(items1, "Major appliances", q, supplier);
        cashier1 = new Cashier("Martin","Vila",LocalDate.of(2000, 7, 7),355677777,"Unknown","emanuel","MartinVila23","Cs","cashier",
         505,s1,items1  );
        }

    //Testing a password where both conditions are true 
    @Test
    void testChangePass_success() {
        assertTrue(cashier1.changePass("MartinVila23", "martimarti", "martimarti"));
    }
    //Giving a wrong old password to make first condition false
    @Test
    void testChangePass_wrongOldPassword() {
        assertFalse(cashier1.changePass("WrongOldPass", "martimarti", "martimarti"));
    }

    //new passwords do not match to make second condition false
    @Test
    void testChangePass_mismatchNewPasswords() {
        assertFalse(cashier1.changePass("MartinVila23", "martimarti", "martimarti123"));
    }
}

