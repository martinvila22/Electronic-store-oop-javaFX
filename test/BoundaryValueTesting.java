package test;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import model.Item;
import model.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoundaryValueTesting {
    public BoundaryValueTesting() {
    }

    @Test
    void bvtApplyDiscountTest() {
        Supplier sup = new Supplier(true, "Samsung");
        Item item = new Item("TV", (double)500.0F, (double)1000.0F, sup, 10);
        Assertions.assertThrows(IllegalArgumentException.class, () -> item.applyDiscount((double)-1.0F));
        item.setSellingPrice((double)1000.0F);
        item.applyDiscount((double)0.0F);
        Assertions.assertEquals((double)1000.0F, item.getSellingPrice());
        item.setSellingPrice((double)1000.0F);
        item.applyDiscount((double)1.0F);
        Assertions.assertEquals((double)990.0F, item.getSellingPrice());
        item.setSellingPrice((double)1000.0F);
        item.applyDiscount((double)99.0F);
        Assertions.assertEquals((double)10.0F, item.getSellingPrice());
        item.setSellingPrice((double)1000.0F);
        item.applyDiscount((double)100.0F);
        Assertions.assertEquals((double)0.0F, item.getSellingPrice());
        item.setSellingPrice((double)1000.0F);
        Assertions.assertThrows(IllegalArgumentException.class, () -> item.applyDiscount((double)101.0F));
    }
}
