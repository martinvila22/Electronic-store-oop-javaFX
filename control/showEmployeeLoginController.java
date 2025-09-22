package control;

import java.io.*;



import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import model.*;
import view.*;

public class showEmployeeLoginController {
	private TextField idField = new TextField();
	private PasswordField passwordField = new PasswordField();
    private File inputFileCashier = new File("src/dao/cashiers.dat");
    private File inputFileManager = new File("src/dao/managers.dat");
    private File inputFileAdministrator = new File("src/dao/administrators.dat");
    private ShowEmployeeLoginView view;
    private Stage stage;
 

    

    public void handleLogin(Stage stage,ShowEmployeeLoginView view) {
    	this.view=view;
    	
    	String employeeId = view.getIdField().getText();
        String password = view.getPasswordField().getText();
        char type=employeeId.charAt(0);
        if(type=='C') {
        	Cashier cashier=checkCashier(employeeId,password);
        	if(cashier!=null) {
        		if(cashier.getPermisssionToWork()==true) {
        		System.out.println("permission granted");
        	 new CashierDashboardView(stage, cashier);
        }
        		else {
        			showAlert("Acces denied","You do not have a permission to Work");
        		}
        	}
        }
        else if(type=='M') {
        	
        	Manager manager=checkManager(employeeId,password);
        	if(manager!=null) {
        		if(manager.hasPermissionToWork()==true) {
        	
        	new ManagerDashboardView(stage,manager);
        }
        		else {		
        			System.out.println("k");
        			showAlert("Acces denied","You do not have a permission to Work");
        		}
        	}

      
       }
        else if(type=='A') {
        	
        	Administrator admin=checkAdministrator(employeeId,password);
        	if(admin!=null) {
        		
        	new AdministratorDashboardView(stage,admin);
        	
        }
        else {
        	System.out.println("nada");
        }
        }
        
   
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
                } catch (EOFException ex) {
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
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(inputFileManager))) {
            while (true) {
                try {
                    Object obj = reader.readObject();
                    if (obj instanceof Manager) {
                        Manager manager = (Manager) obj;
                        if (manager.logIn(username, password)) {
                            return manager;
                        }
                    } else {
                        System.out.println("Unexpected object type: " + obj.getClass().getName());
                    }
                } catch (EOFException ex) {
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
                } catch (EOFException e) {
                    
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
