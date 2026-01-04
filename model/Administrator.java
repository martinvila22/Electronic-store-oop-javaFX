package model;

import java.io.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import dao.HeaderlessObjectOutputStream;

public class Administrator extends Employee implements Serializable {
    private static final long serialVersionUID = 4223558147966164685L;
    private List<Manager> managers = new ArrayList<>();
    private List<Cashier> cashiers = new ArrayList<>();
    private ArrayList<Sector> sectors=new ArrayList<>();
    private String administratorId;
    private static final File outputFile = new File("src/dao/administrators.dat");
    private static List<Item> items = new ArrayList<>();



    public Administrator(String name, String surname, LocalDate dateOfBirth, int phoneNr, String address,
                         String administratorId, String password, String employeeId, String role,
                         double salary,List<Manager> manager, List<Cashier> cashier,List<Sector> sector) {
        super(name, surname, dateOfBirth, phoneNr, address, administratorId, password, employeeId, role, salary);
        this.managers.addAll(manager);
        this.cashiers.addAll(cashier);
        this.sectors.addAll(sector);
        this.administratorId = administratorId;
        writeToFile();
    }

    private void writeToFile() {
        try (ObjectOutputStream writer = outputFile.length() > 0
                ? new HeaderlessObjectOutputStream(new FileOutputStream(outputFile))
                : new ObjectOutputStream(new FileOutputStream(outputFile))) {
            writer.writeObject(this);
        }
        catch(IOException ex)
        {
            System.out.println("Error during writing item"+ ex.getMessage());
        }

    }

    public List<Manager> getManagers() {
        return new ArrayList<>(managers);
    }

    public List<Cashier> getCashiers() {
        return new ArrayList<>(cashiers);
    }

    public List<Sector> getSectors() {
        return new ArrayList<>(sectors);
    }

    public List<Item> getItems() {
        return items == null ? new ArrayList<>() : new ArrayList<>(items);
    }


    public void setManagers(List<Manager> managers) {
        this.managers = new ArrayList<>(managers);
        update();
    }


    public void setCashiers(List<Cashier> cashiers) {
        this.cashiers = new ArrayList<>(cashiers);
        update();
    }


    public void addManager(Manager m) {
        managers.add(m);
        update();
    }

    public void addCashier(Cashier c) {
        cashiers.add(c);
        update();
    }

    public void removeManager(String managerId) {
        managers.removeIf(manager -> manager.getManagerId().equals(managerId));
        update();
    }

    public void removeCashier(String cashierId) {
        cashiers.removeIf(cashier -> cashier.getCashierId().equals(cashierId));
        update();
    }

    public void modifyManagerId(String newId, String managerId) {
        for (Manager manager : managers) {
            if (manager.getManagerId().equals(managerId)) {
                manager.setManagerId(newId);
            }
        }
        update();
    }

    public void modifyCashierId(String newId, String cashierId) {
        for (Cashier cashier : cashiers) {
            if (cashier.getCashierId().equals(cashierId)) {
                cashier.setCashierId(newId);
            }
        }
        update();
    }

    public void revokePermission( String employeeId) {
        char type=employeeId.charAt(0);
        if (type=='M') {
            for (Manager manager : managers) {
                if (manager.getEmployeeId().equals(employeeId)) {
                    manager.setPermissionToWork(false);
                    System.out.println("permission revoked");
                }
            }
        } else if (type=='C') {
            for (Cashier cashier : cashiers) {
                if (cashier.getEmployeeId().equals(employeeId)) {
                    cashier.setPermission(false);
                }
            }
        }
        update();
    }

    public void givePermission( String employeeId) {
        char type=employeeId.charAt(0);
        if (type=='M') {
            for (Manager manager : managers) {
                if (manager.getEmployeeId().equals(employeeId)) {
                    manager.setPermissionToWork(true);
                }
            }
        } else if (type=='C') {
            for (Cashier cashier : cashiers) {
                if (cashier.getEmployeeId().equals(employeeId)) {
                    cashier.setPermission(true);
                }
            }
            update();
        }
    }

    @Override
    public String EmployeeTask() {
        return "Administrates the system, gives and takes permissions, and oversees employees' work.";
    }

    @Override
    public boolean logIn(String username, String password) {
        return administratorId.equals(username) && super.getPassword().equals(password);
    }
    public void update() {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/dao/administrators.dat"))) {
            {

                outputStream.writeObject(this);
            }
            System.out.println("updated");

        } catch (IOException ex) {
            System.out.println("not updated");
        }
    }



    public void removeItem(Item itemToRemove) {
    }

    public String getAdminiId() {
        return administratorId;
    }


}