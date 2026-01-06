package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import dao.HeaderlessObjectOutputStream;

public class Supplier implements Serializable {
    private static final long serialVersionUID = -1205292926141126748L;

    
    private String name;
    private boolean supplierForStore;
    private transient int totalNrOfProductSold;
    private static final File outputFile = new File("src/dao/suppliers.dat");

    public Supplier( boolean supplierForStore, String name) {
        this.name = name;
       
        this.supplierForStore = supplierForStore;

        writeToFile();
    }

    public Supplier(Supplier supplier) {
    }

    public Supplier(String testSupplier) {
    }

    private void writeToFile() {
        try (FileOutputStream outputStream = new FileOutputStream(outputFile, true)) {
            ObjectOutputStream writer;

            if (outputFile.length() > 0) {
                writer = new HeaderlessObjectOutputStream(outputStream);
            } else writer = new ObjectOutputStream(outputStream);

            writer.writeObject(this);
           
        } catch (IOException ex) {
            System.out.println("Error during writing item: " + ex.getMessage());
        }
    }

    
    public void setIsSupplierForStore(boolean supplierForStore) {
        this.supplierForStore = supplierForStore;
    }

    public void addTotalNrOfProductSold(int quantity) {
        totalNrOfProductSold += quantity;
    }

    

    public boolean getIsSupplierForStore() {
        return supplierForStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return   " Supplier for Store: " + supplierForStore+"Supplier Name"+this.name;
    }

   
    public void update() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/dao/suppliers.dat"))) {
            {

                outputStream.writeObject(this);
            }
            

        } catch (IOException ex) {
            
        }
    }
}
