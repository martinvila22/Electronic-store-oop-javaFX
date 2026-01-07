package model;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Cashier extends Employee implements Serializable {
    /**
     *
     */
    private static int totalAmountOfBills = 0;
    private static final long serialVersionUID = -2969383188267925283L;
    private static int totalAmountOfBillsForDay;
    private String cashierId;
    private ArrayList<Bill> bills = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Sector sectorResponsible;
    private double totalForDay;
    private int dayOfWork;
    private boolean permissionToWork;
    private  double totalAmountWon;
    private Date startingDay;
    private static final File  outputFile=new File("src/dao/cashiers.dat");

    public Cashier(String name, String surname, LocalDate dateOfBirth, int phoneNr,
                   String address, String cashierId, String password, String employeeId, String role,
                   double salary,Sector sector, List<Item> item){
        super(name,surname,dateOfBirth,phoneNr,address,cashierId,password,employeeId,role,salary);
        this.cashierId = cashierId;
        this.totalForDay = 0.0;
        this.dayOfWork = 0;
        this.permissionToWork = true ;
        startingDay=new Date();
        items.addAll(item);
        writeToFile();
    }

    public Cashier(String name,String surname,LocalDate dateOfBirth,int phoneNr,
                   String address,String cashierId,String password,String employeeId,String role,
                   double salary){
        super(name,surname,dateOfBirth,phoneNr,address,cashierId,password,employeeId,role,salary);
        this.cashierId = cashierId;
        this.totalForDay = 0.0;
        this.dayOfWork = 0;
        this.permissionToWork = true ;
        startingDay=new Date();
        writeToFile();
    }

    private void writeToFile() {
        try(FileOutputStream outputStream=new FileOutputStream(outputFile))
        {
            ObjectOutputStream writer;

            if(outputFile.length()>0) writer = new dao.HeaderlessObjectOutputStream(outputStream);
            else writer = new ObjectOutputStream(outputStream);
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
    public void setSector(Sector s) {
        this.sectorResponsible = (s != null) ? new Sector(s) : null;
        update();
    }

    public static void addBillNumber() {Cashier.totalAmountOfBills++;
        Cashier.totalAmountOfBillsForDay++;
    }
    public void addAmount(double amount) {
        this.totalForDay+=amount;
        this.totalAmountWon+=amount;
        update();
    }
    public Date getStartingDay() {
        return new Date(startingDay.getTime());
    }
    public String getCashierId() {return this.cashierId;}
    public double getTotalAmountWon() {return this.totalAmountWon;}


    public static int getTotalAmountOfBills() {
        return totalAmountOfBills;
    }
    public boolean getPermisssionToWork() {return this.permissionToWork;}
    public double getTotalAmountForDay() {
        return this.totalForDay;
    }
    public List<Item> getItem() {
        return new ArrayList<>(items);
    }


    public int getDayOfWork() {
        return dayOfWork;
    }
    public static int totalAmountOfBillsForAday() {
        return totalAmountOfBills;
    }
    public Sector getSector() {
        return (sectorResponsible != null) ? new Sector(sectorResponsible) : null;
    }


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
        addBillNumber();
        totalForDay += bill.getTotalAmountOfBill();
        totalAmountWon+=bill.getTotalAmountOfBill();
        update();
        return bill;
    }

    public Bill createBill(List<Item> items, List<Integer> quantities) {
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
        this.totalForDay = 0;
        update();
    }
    public void startShift() {
        resetTotalForDay();
        this.totalForDay=0;
        resetTotalAmountOfBillsForDay();
        update();
    }


    private static void resetTotalAmountOfBillsForDay() {
        totalAmountOfBillsForDay = 0;
    }
    public void endShift() {

        System.out.println("Fund dite: te ardhura = " + totalForDay + "  bills te bera: = "
                + totalAmountOfBills);
        resetTotalForDay();
        this.dayOfWork++;
        update();
    }

    public List<Bill> getbills() {
        return new ArrayList<>(bills);
    }

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



