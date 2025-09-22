package model;
import dao.HeaderlessObjectOutputStream;
import view.*;
import control.*;
import dao.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Item implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7280777475184326490L;

    private String itemName;
    private Date purchaseDate;
    private double purchasePrice;
    private double sellingPrice;
    private static transient int nrOfItemsSold;
    private boolean itemOutOfStock;
    private Supplier supplier;
    private   int quantity;
    private boolean isItemDiscounted;

    private transient File  outputFile=new File("src/dao/items.dat");

    public Item(String itemName,  double purchasePrice, double sellingPrice,
                Supplier supplier,int quantity) {
        this.itemName = itemName;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.purchaseDate = new Date();
        Item.nrOfItemsSold = 0;
        this.supplier = supplier;
        this.quantity = quantity;
        
        this.itemOutOfStock = (quantity == 0);

        writeToFile();
    }
    private void writeToFile() {
        try(FileOutputStream outputStream=new FileOutputStream(outputFile, true))
        {
            ObjectOutputStream writer;

            if(outputFile.length()>0)
                writer=new HeaderlessObjectOutputStream(outputStream);
            else
                writer=new ObjectOutputStream(outputStream);


            writer.writeObject(this);

           
        }
        catch(IOException ex)
        {
            System.out.println("Error during writing item"+ ex.getMessage());
        }

    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("nrPositiv");
        }
        this.quantity += quantity;
        this.itemOutOfStock = (this.quantity == 0);
    }

    public void setQuantity(int quantity) {this.quantity=quantity;}

    public void decreaseStock(int quantity) {
        if (quantity <= 0 || quantity > this.quantity) {
            throw new IllegalArgumentException("Not possible");
        }
        this.quantity -= quantity;
        this.nrOfItemsSold += quantity;
        this.itemOutOfStock = (this.quantity == 0);
    }

    public boolean isItemOutOfStock1() {
        return this.quantity == 0;
    }

    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("xx");
        }
        this.sellingPrice -= this.sellingPrice * (percentage / 100);
    }




    public static int itemsWithTheSamePrice(ArrayList<Item> itemsList, double price) {
        int count = 0;
        for (int i = 0; i < itemsList.size(); i++) {
            if (itemsList.get(i).getSellingPrice() == price) {
                count++;
            }
        }
        return count;
    }



    public String getItemName() {
        return itemName;
    }


    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
    public boolean getIsItemDiscounted() {return this.isItemDiscounted;}
    public boolean getIsItemOutOfStock() {return this.itemOutOfStock;}

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getNrOfItemsSold() {
        return nrOfItemsSold;
    }

    public boolean isItemOutOfStock() {
        return itemOutOfStock;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getQuantity() {
        return quantity;
    }
    

    @Override
    public String toString() {
        return String.format(" name='%s',  " +
                        "purchasePrice=%.2f, sellingPrice=%.2f, " +
                        "quantity=%d, sold=%d, supplier=%s",
                itemName, purchasePrice,
                sellingPrice, quantity, nrOfItemsSold, supplier);
    }

    public void update() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/dao/items.dat"))) {
            {

                outputStream.writeObject(this);
            }
            System.out.println("updated");

        } catch (IOException ex) {
            System.out.println("not updated");
        }
    }

    public void setItemName(String itemName) {
    }

    public void setSector(Sector sec) {

    }
}