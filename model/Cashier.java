package model;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.*;

public class Cashier extends Employee implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2969383188267925283L;
    private String cashierId;
    private ArrayList<Bill> bills = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Sector sectorResponsible;
    private static int totalAmountOfBills;
    private static int totalAmountOfBillsForDay;
    private double totalForDay;
    private static int dayOfWork;
    private boolean permissionToWork;
    private  double totalAmountWon;
    private Date startingDay;
    private transient File  outputFile=new File("src/dao/cashiers.dat");

    public Cashier(String name,String surname,LocalDate dateOfBirth,int phoneNr,
                   String address,String cashierId,String password,String employeeId,String role,
                   double salary,Sector sectorReponsible,ArrayList<Item> item){
        super(name,surname,dateOfBirth,phoneNr,address,cashierId,password,employeeId,role,salary);
        this.cashierId = cashierId;
        Cashier.totalAmountOfBills = 0;
        this.totalForDay = 0.0;
        this.dayOfWork = 0;
        this.permissionToWork = true ;
        startingDay=new Date();
        items.addAll(item);
        this.sectorResponsible=sectorReponsible;
        writeToFile();
    }

    public Cashier(String name,String surname,LocalDate dateOfBirth,int phoneNr,
                   String address,String cashierId,String password,String employeeId,String role,
                   double salary){
        super(name,surname,dateOfBirth,phoneNr,address,cashierId,password,employeeId,role,salary);
        this.cashierId = cashierId;
        Cashier.totalAmountOfBills = 0;
        this.totalForDay = 0.0;
        this.dayOfWork = 0;
        this.permissionToWork = true ;
        startingDay=new Date();
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


    public void setPermission(boolean b) {permissionToWork=b;
        update();
    }
    public void setCashierId(String newS) {cashierId=newS;
        update();
    }
    public void setSector(Sector s) {sectorResponsible=s;
        update();
    }
    public void addBillNumber() {Cashier.totalAmountOfBills++;
        Cashier.totalAmountOfBillsForDay++;
    }
    public void addAmount(double amount) {
        this.totalForDay+=amount;
        this.totalAmountWon+=amount;
        update();
    }

    public Date getStartingDay() {return startingDay;}
    public String getCashierId() {return this.cashierId;}
    public double getTotalAmountWon() {return this.totalAmountWon;}


    public static int getTotalAmountOfBills() {
        return totalAmountOfBills;
    }
    public boolean getPermisssionToWork() {return this.permissionToWork;}
    public double getTotalAmountForDay() {
        return this.totalForDay;
    }
    public ArrayList<Item> getItem(){return this.items;}

    public static int getDayOfWork() {
        return dayOfWork;
    }
    public static int TotalAmountOfBillsForAday() {
        return totalAmountOfBills;
    }
    public Sector getSector() {return this.sectorResponsible;}

    public Bill createBillOneItem(Item item, int quantity) {
        if (item == null || quantity == 0) {
            throw new IllegalArgumentException(" Item or Quantity is empty");
        }
        ArrayList<Item> itemm=new ArrayList<>();
        ArrayList<Integer> quant=new ArrayList<>();
        itemm.add(item);
        quant.add(quantity);
        Bill bill=new Bill(itemm,quant,this);
        bills.add(bill);
        totalAmountOfBills++;
        totalForDay += bill.getTotalAmountOfBill();
        totalAmountWon+=bill.getTotalAmountOfBill();
        update();
        return bill;
    }

    public Bill createBill(ArrayList<Item> items, ArrayList<Integer> quantities) {
        if (items == null || quantities == null || items.size() != quantities.size()) {
            throw new IllegalArgumentException("There is an error in items quantities,or they "
                    + "do not match each other");
        }

        Bill bill = new Bill(items,quantities,this);
        bills.add(bill);
        addBillNumber();
        totalForDay += bill.getTotalAmountOfBill();
        totalAmountWon+=bill.getTotalAmountOfBill();
        for(Item item:items) {
            item.decreaseStock(1);
        }

        update();
        return bill;
    }

    public void updateStock(Item item) {
        item.setQuantity(item.getQuantity());
        update();
    }


    public void resetTotalForDay() {

        totalAmountOfBills = 0;
        this.totalForDay=0;
        update();
    }
    public void startShift() {
        resetTotalForDay();
        this.totalForDay=0;
        this.totalAmountOfBillsForDay=0;

        update();
    }
    public void endShift() {


        System.out.println("Fund dite: te ardhura = " + totalForDay + "  bills te bera: = "
                + totalAmountOfBills);
        resetTotalForDay();
        Cashier.dayOfWork++;
        update();
    }


    public ArrayList<Bill> getbills(){return this.bills;}



    public void printBills() {
        if (bills.isEmpty()) {
            System.out.println("xxxx");
        } else {
            for (int i = 0; i < bills.size(); i++) {
                Bill bill = bills.get(i);
                System.out.println(bill);
            }
        }
    }

    public int getBillsCount() {
        return bills.size();
    }
    public void addItem(Item p) {
        this.items.add(p);
        update();
    }

    public boolean hasPermissionToWork() {
        return permissionToWork;
    }
    @Override
    public boolean logIn(String username, String password) {
        return this.cashierId.equals(username) && this.getPassword().equals(password);
    }

    @Override
    public String toString() {
        return super.toString()+" "+ String.format(" cashierID='%s', " + cashierId,
                "totalForDay=%.2f," + totalForDay,
                " totalAmountOfBills=%d," + totalAmountOfBills,
                " dayOfWork=%s, " + dayOfWork,
                "permissionToWork=%s", permissionToWork);

    }


    @Override
    public String EmployeeTask() {

        return "Deals with products and customers.Prepares the bills and updates the system";
    }

    public void update() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/dao/cashiers.dat"))) {
            {

                outputStream.writeObject(this);
            }
            

        } catch (IOException ex) {
            
        }
    }

    	 
    }
    



