package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Item;
import model.Supplier;
public class BoundaryValueTesting {

        @Test
        void bvtApplyDiscountTest() {
            Supplier sup = new Supplier(true, "Samsung");
            Item item = new Item("TV", 500, 1000, sup, 10);

            // min-1
            assertThrows(IllegalArgumentException.class,
                    () -> item.applyDiscount(-1));

            // min
            item.setSellingPrice(1000);
            item.applyDiscount(0);
            assertEquals(1000, item.getSellingPrice());

            // min+1
            item.setSellingPrice(1000);
            item.applyDiscount(1);
            assertEquals(990, item.getSellingPrice());

            // max-1
            item.setSellingPrice(1000);
            item.applyDiscount(99);
            assertEquals(10, item.getSellingPrice());

            // max
            item.setSellingPrice(1000);
            item.applyDiscount(100);
            assertEquals(0, item.getSellingPrice());

            // max+1
            item.setSellingPrice(1000);
            assertThrows(IllegalArgumentException.class,
                    () -> item.applyDiscount(101));
        }
    }

