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
import java.time.LocalDate;
import java.util.ArrayList;




public class Manager extends Employee implements Serializable {

    private static final long serialVersionUID = -4410598428000581485L;

    private ArrayList<Sector> sectorResponsible = new ArrayList<>();
    private ArrayList<Cashier> cashiersUnderSupervision = new ArrayList<>();
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private ArrayList<Item> items=new ArrayList<>();
    private boolean permissionToWork;
    private String managerId;
    private double TotalAmountSpent;
    private transient File outputFile=new File("src/dao/managers.dat");

    public Manager(String name,String surname,LocalDate dateOfBirth,int phoneNr,
                   String address,String employeeId,String managerId,String password,String role,double salary,
                   ArrayList<Sector> sectors,ArrayList<Cashier> cashiers,ArrayList<Supplier> supp,ArrayList<Item> item) {
        super(name,surname,dateOfBirth,phoneNr,address,managerId,password,
                employeeId,role,salary);
        sectorResponsible.addAll(sectors);
        cashiersUnderSupervision.addAll(cashiers);
        suppliers.addAll(supp);
        permissionToWork=true;
        items.addAll(item);
        this.managerId=managerId;
        writeToFile();
    }


    public Manager(String name, String surname, LocalDate dateOfBirth, int phoneNr,
                   String address, String managerId, String password, String role,
                   double salary, String employeeId) {
        super(name,surname,address,password,employeeId,role,salary);
        this.managerId = managerId;
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


    public void addCashier(Cashier cashier) {
        cashiersUnderSupervision.add(cashier);
        update();
    }


    public void removeCashier(String cashierId) {
        cashiersUnderSupervision.removeIf(cashier -> cashier.getCashierId().equals(cashierId));
        update();
    }
    public void removeProductFromSector(String ItemName) {
        this.sectorResponsible.removeIf(sector -> sector.getClass().equals(getItemName()));
        update();
    }


    public ArrayList<Cashier> getCashiers() {
        return this.cashiersUnderSupervision;
    }


    public String getManagerId() {
        return this.managerId;
    }
    public void addItem(Item p) {items.add(p);
        update();
    }



    public ArrayList<Sector> getSectors() {
        return this.sectorResponsible;
    }

    public boolean hasPermissionToWork() {
        return this.permissionToWork;
    }



    public void setManagerId(String managerId) {
        this.managerId = managerId;
        update();
    }
    public ArrayList<Item> getItems(){
        return items;
    }

    public void addSector(Sector sector) {
        sectorResponsible.add(sector);

        update();
    }

    public void increaseStock(Item item,int quantity) {
        item.increaseStock(quantity);
        update();
    }

    public void updateStock(Item item) {
        item.setQuantity(item.getQuantity());
        update();
    }

    public boolean removeSector(String sectorName) {
        for (int i = 0; i < sectorResponsible.size(); i++) {
            if (sectorResponsible.get(i).getSectorName().equals(sectorName)) {
                sectorResponsible.remove(i);

                return true;
            }
        }
        return false;
    }


    public boolean isOneSectorEmpty(String sectorName) {
        for (Sector sector : sectorResponsible) {
            if (sector.getSectorName().equals(sectorName) && sector.getNrOfItems() < 5) {
                return true;
            }
        }
        return false;
    }


    public Sector isAnySectorEmpty() {
        for (Sector sector : sectorResponsible) {
            if (sector.getNrOfItems() < 5) {
                return sector;
            }
        }
        return null;
    }


    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        update();
    }


    public ArrayList<Supplier> getSuppliers() {
        return this.suppliers;
    }


    @Override
    public String EmployeeTask() {
        // Manager-specific task implementation
        return "Managing cashiers, sectors, suppliers, and products.";
    }


    @Override
    public boolean logIn(String username, String password) {
        return username.equals(managerId) && password.equals(super.getPassword());
    }


    public void notifySupplierToIncreaseStock(int quantity) {
        for (Sector sector : sectorResponsible) {
            for (Item item : sector.getItems()) {
                if (item.getQuantity() < 5) {
                    item.increaseStock(quantity);
                    Supplier supplier = item.getSupplier();
                    supplier.addTotalNrOfProductSold(quantity);
                }
            }
        }
    }

    // Method to rate cashiers based on their performance
    public String rateCashier(String cashierId) {
        for (Cashier cashier : cashiersUnderSupervision) {
            if (cashier.getCashierId().equals(cashierId)) {
                double avgDailySales = cashier.getTotalAmountWon() / 30;  // Assuming 30 days
                if (cashier.getTotalAmountForDay() > avgDailySales) {
                    return "Amazing work done";
                } else if (cashier.getTotalAmountForDay() == avgDailySales) {
                    return "Good work done";
                } else {
                    return "Bad work done";
                }
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Manager ID: ").append(managerId).append("\n");
        sb.append("Number of sectors responsible: ").append(sectorResponsible.size()).append("\n");
        sb.append("Permission to work: ").append(permissionToWork).append("\n");

        sb.append("Sectors under responsibility: \n");
        for (Sector sector : sectorResponsible) {
            sb.append(sector.getSectorName()).append("\n");
        }

        sb.append("Cashiers under supervision: \n");
        for (Cashier cashier : cashiersUnderSupervision) {
            sb.append(cashier.getCashierId()).append("\n");
        }

        return sb.toString();
    }


    public Iterable<? extends Sector> getSectorsResponsible() {
        return this.sectorResponsible;
    }


    public void addProduct(Item newItem) {
        // Assuming the manager can add an item to the sectors they are responsible for.
        for (Sector sector : sectorResponsible) {
            // Example: Add the item to the first sector (you can adapt this logic)
            sector.addNewItem(newItem);
        }
        update();
    }


    public void removeItem(String selectedProductName) {
        for(int i=0;i<cashiersUnderSupervision.size();i++) {
            for (Item item :items) {
                if(item.getItemName().equals(selectedProductName)) {
                    items.remove(item);
                    break;
                }
            }
        }
        update();
    }

    public Item[] getItemName() {
        return getItemName();
    }

    public void setPermissionToWork(boolean t) {
        this.permissionToWork=t;
        update();
    }

    public void update() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/dao/managers.dat"))) {
            {

                outputStream.writeObject(this);
            }
            

        } catch (IOException ex) {
            
        }
    }

    


	public double getTotalAmountSpent() {
		return this.TotalAmountSpent;
	}


	public void addTotalAmountSpent(double sum) {
	 this.TotalAmountSpent+=sum;
		
	}
}