package model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

public class Inventory implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2509837283020867465L;
	private int soldQuantityTotal;
    private int nrofItems;
    private ArrayList<Item> items;
    private double totalSumOfSales;
    private double totalSumOfCost;
    private Date dateCreated;

    public Inventory(ArrayList<Item> items) {
        this.items = items;
        this.nrofItems = (items != null) ? items.size() : 0;
        this.soldQuantityTotal = 0;
        this.totalSumOfSales = 0.0;
        this.totalSumOfCost = 0.0;
        this.dateCreated = new Date(); // Sets the creation date to the current date
    }

    public int getSoldQuantityTotal() {
        return soldQuantityTotal;
    }

    public int getNrofItems() {
        return nrofItems;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addNewItem(Item item) {
        if (item != null) {
            this.items.add(item);
            this.nrofItems++;
        }
    }

    public double giveReportAfterPeriod(Date date) {
        if (date.before(this.dateCreated)) {
            return 0.0;
        }
        return totalSumOfSales;
    }
}
