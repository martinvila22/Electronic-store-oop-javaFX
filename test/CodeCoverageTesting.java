//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package test;

import model.Item;
import model.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CodeCoverageTesting {
    private Item item;

    public CodeCoverageTesting() {
    }

    @BeforeEach
    void setUp() {
        Supplier sup = new Supplier(true, "LG");
        this.item = new Item("Fridge", (double)400.0F, (double)800.0F, sup, 10);
    }

    @Test
    void testDecreaseStock_success() {
        this.item.decreaseStock(5);
        Assertions.assertEquals(5, this.item.getQuantity());
    }

    @Test
    void testDecreaseStock_invalidZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.item.decreaseStock(0));
    }

    @Test
    void testDecreaseStock_exceedsStock() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.item.decreaseStock(20));
    }
}
