package unitTesting;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Supplier;

class unitTestingSupplier {

    @Test
    void constructorSetsValuesCorrectly() {
        Supplier supplier = new Supplier(true, "TestSupplier");

        assertEquals("TestSupplier", supplier.getName());
        assertTrue(supplier.getIsSupplierForStore());
    }

    @Test
    void setNameUpdatesName() {
        Supplier supplier = new Supplier(true, "OldName");
        supplier.setName("NewName");

        assertEquals("NewName", supplier.getName());
    }

    @Test
    void setSupplierForStoreUpdatesFlag() {
        Supplier supplier = new Supplier(false, "Supplier");

        supplier.setIsSupplierForStore(true);

        assertTrue(supplier.getIsSupplierForStore());
    }

    @Test
    void toStringContainsName() {
        Supplier supplier = new Supplier(true, "ABC");

        assertTrue(supplier.toString().contains("ABC"));
    }
}
