package Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Item;
import model.Supplier;
public class CodeCoverageTesting {

        private Item item;

        @BeforeEach
        void setUp() {
            Supplier sup = new Supplier(true, "LG");
            item = new Item("Fridge", 400, 800, sup, 10);
        }

        // valid decrease
        @Test
        void testDecreaseStock_success() {
            item.decreaseStock(5);
            assertEquals(5, item.getQuantity());
        }

        // quantity <= 0
        @Test
        void testDecreaseStock_invalidZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> item.decreaseStock(0));
        }

        // quantity > stock
        @Test
        void testDecreaseStock_exceedsStock() {
            assertThrows(IllegalArgumentException.class,
                    () -> item.decreaseStock(20));
        }
    }

