package control;

import java.io.*;


import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import view.*;

public class ShowEmployeeLoginController {
    private File inputFileCashier = new File("control/dao/cashiers.dat");
    private File inputFileManager = new File("control/dao/managers.dat");
    private File inputFileAdministrator = new File("control/dao/administrators.dat");


    public void handleLogin(Stage stage,ShowEmployeeLoginView view) {
        String employeeId = view.getIdField().getText();
        String password = view.getPasswordField().getText();
        char type=employeeId.charAt(0);
        User user=handleLogin(employeeId,password,type);
        if(user instanceof Cashier) {
        	new CashierDashboardView(stage, user);
        }
        else if(user instanceof Manager) {
        	new ManagerDashboardView(stage,user);
        }
        else if(user instanceof Administrator) {
        	new AdministratorDashboardView(stage,user);
        }else {
        	showAlert("Acces denied","You do not have a permission to Work");
        }
    }
    
    
    public User handleLogin(String employeeId,String password,char type) {
    	System.out.println("inside login");
        if(type=='C') {
        	Cashier cashier=checkCashier(employeeId,password);
        	if(cashier!=null) {
        		if(cashier.getPermisssionToWork()) {
        		System.out.println("\npermission granted");
        	 return cashier;
        }
        	}
        }
        else if(type=='M') {
        	Manager manager=checkManager(employeeId,password);
        	if(manager!=null) {
        		if(manager.hasPermissionToWork()) {
        	
        	return manager;
        }
        	}
       }
        else if(type=='A') {
        	Administrator admin=checkAdministrator(employeeId,password);
        	if(admin!=null) {
        	return admin;
        }
        }
		return null;

    }

    public Cashier checkCashier(String username, String password) {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(inputFileCashier))) {
            System.out.println("File opened for reading cashiers.");
            while (true) {
                try {
                	
                    Cashier cashier = (Cashier) reader.readObject();
                  System.out.println(" Checking:"+cashier);
                    if (cashier.logIn(username, password)) {
                         return cashier;  
                    }
                } catch (EOFException _) {
                    System.out.println("Reached the end of file for cashiers.");
                    break;  // End of file reached, break out of the loop
                } catch (ClassCastException ex) {
                    System.out.println("Class cast error: " + ex.getMessage());
                    ex.printStackTrace();  // Print any casting errors
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error reading the file: " + ex.getMessage());
            ex.printStackTrace();  // Print any IO or ClassNotFound exceptions
        }
        System.out.println("Cashier not found with provided credentials.");
        return null;  // No matching cashier found, return null
    }


    public Manager checkManager(String username, String password) {
    	System.out.println("we are here");
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(inputFileManager))) {
            while (true) {
                try {
                    Object obj = reader.readObject();
                    if (obj instanceof Manager manager) {
                        if (manager.logIn(username, password)) {
                            return manager;
                        }
                    } else {
                        System.out.println("Unexpected object type: " + obj.getClass().getName());
                    }
                } catch (EOFException _) {
                    System.out.println("Reached the end of file for managers.");
                    break;
                } catch (ClassCastException ex) {
                    System.out.println("Class cast error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error reading the file: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }



    public Administrator checkAdministrator(String username, String password) {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(inputFileAdministrator))) {
            while (true) {
                try {
                    Administrator administrator = (Administrator) reader.readObject();
                    if (administrator.logIn(username, password)) {
                        return administrator;
                    }
                } catch (EOFException _) {
                    
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null; // Return null if no matching administrator is found
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

}
