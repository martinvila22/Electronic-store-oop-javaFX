package control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;
import view.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class AdministratorDashboardController {
    private Administrator administrator;
    private AdministratorDashboardView adminView;
    private Manager manager;
    private Cashier cashier;
   
    private Supplier supplier;

    public AdministratorDashboardController(Stage stage, Administrator administrator,AdministratorDashboardView view) {
        this.administrator = administrator;
        this.adminView = view;
        populateLists();
        initializeView();
    }

    private void initializeView() {
        adminView.getAddManagerButton().setOnAction(new EventHandler<ActionEvent>()

        {
               @Override
     public void handle(ActionEvent event) {
  addManager();

    }
               
             }
        );
        adminView.getGeneralReportsButton().setOnAction(new EventHandler<ActionEvent>()
        		{
            @Override
            public void handle(ActionEvent event) { 
            handleGeneralReports();	
            }
            }
        );
            
        adminView.getAddItemsButton().setOnAction(new EventHandler <ActionEvent>(){
        @Override
        public void handle(ActionEvent  event) {

            addItems();


        }

        }
        );
        
        adminView.getTotalRevenueButton().setOnAction(new EventHandler <ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                handleTotalRenevue();
            }


        });
        adminView.getModifyItemsButton().setOnAction(new EventHandler <ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                //modifyItems();
            }
        });
        adminView.getRemoveManagerButton().setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                removeManager();
            }
        });
        adminView.getModifyManagerButton().setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                ModifyManager();
            }
        });
        adminView.getAddCashierButton().setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                addCashier();
            }
        });
        adminView.getRemoveCashierButton().setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                removeCashier();
            }
        });
        adminView.getModifyCashierButton().setOnAction(event -> showModifyCashier());
        
        adminView.getRevokePermissionButton().setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Event handler");
                revokePermission();
            }
        });
        adminView.getGivePermissionButton().setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {

                givePermission();
            }
            
        });
        adminView.getViewReportsButton().setOnAction(new EventHandler<ActionEvent>()
 {
        	
 
        	@Override
 public void handle(ActionEvent event) {
        		handleViewReports();

        	}
    });
    }

    private void populateLists() {
        adminView.getManagerListView().getItems().clear();
        administrator.getManagers().forEach(manager ->
                adminView.getManagerListView().getItems().add(manager.getName() + "  " + manager.getSurname() + " - " + manager.getManagerId()));

        adminView.getCashierListView().getItems().clear();
        administrator.getCashiers().forEach(cashier ->
                adminView.getCashierListView().getItems().add(cashier.getName() + " " + cashier.getSurname() + " - " + cashier.getCashierId()));


    }

    private void addManager() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add New Manager");


        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        DatePicker dateOfBirthPicker = new DatePicker();
        TextField phoneField = new TextField();
        TextField addressField = new TextField();
        TextField managerIdField = new TextField();
        TextField passwordField = new TextField();
        TextField employeeIdField = new TextField();
        TextField salaryField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(new Label("First Name:"), 0, 0);
        gridPane.add(firstNameField, 1, 0);

        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(lastNameField, 1, 1);

        gridPane.add(new Label("Date of Birth:"), 0, 2);
        gridPane.add(dateOfBirthPicker, 1, 2);

        gridPane.add(new Label("Phone Number:"), 0, 3);
        gridPane.add(phoneField, 1, 3);

        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(addressField, 1, 4);

        gridPane.add(new Label("Manager ID:"), 0, 5);
        gridPane.add(managerIdField, 1, 5);

        gridPane.add(new Label("Password:"), 0, 6);
        gridPane.add(passwordField, 1, 6);

        gridPane.add(new Label("Employee ID:"), 0, 7);
        gridPane.add(employeeIdField, 1, 7);

        gridPane.add(new Label("Salary:"), 0, 8);
        gridPane.add(salaryField, 1, 8);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, submitButton, cancelButton);

        gridPane.add(buttonBox, 1, 10);

        submitButton.setOnAction(event -> {
            try {
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                LocalDate dateOfBirth = dateOfBirthPicker.getValue();
                int phone = Integer.parseInt(phoneField.getText().trim());
                String address = addressField.getText().trim();
                String managerId = managerIdField.getText().trim();
                String password = passwordField.getText().trim();
                String employeeId = employeeIdField.getText().trim();
                double salary = Double.parseDouble(salaryField.getText().trim());

                if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirth == null ||
                        address.isEmpty() || managerId.isEmpty() || password.isEmpty() || employeeId.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }

                administrator.addManager(new Manager(firstName, lastName, dateOfBirth, phone, address, managerId, password, "Manager", salary, employeeId));
                adminView.getManagerListView().getItems().add(firstName + " " + lastName + " - " + managerId);

                popupStage.close();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numbers for Phone and Salary.");
            } catch (IllegalArgumentException e) {
                showAlert("Invalid Input", e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();  // Log the stack trace for debugging
                showAlert("Error", "An unexpected error occurred.");
            }
        });


        cancelButton.setOnAction(event -> popupStage.close());

        Scene scene = new Scene(gridPane, 400, 450);
        popupStage.setScene(scene);
        popupStage.showAndWait();
        populateLists();
    }




    private void removeManager() {
        String selectedManager = adminView.getManagerListView().getSelectionModel().getSelectedItem();
        if (selectedManager != null) {
            String managerId = selectedManager.split(" - ")[1];
            administrator.removeManager(managerId);
            populateLists();
        } else {
            showAlert("Remove Manager", "No manager selected.");
        }
    }
    
    public void showModifyCashier() {
        String selectedCashierName = adminView.getCashierListView().getSelectionModel().getSelectedItem();
        if (selectedCashierName != null) {
            String cashierName = selectedCashierName.split(" ")[0];
            cashier = findCashierByName(cashierName);
        }

        if (cashier == null) {
            adminView.showErrorAlert("Select a cashier");
            return;
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(9, 9, 12, 11));
        grid.setHgap(10);
        grid.setVgap(10);

        Label cashierId = new Label("Cashier Id:");
        TextField cashierIdField = new TextField(cashier.getCashierId());
        grid.add(cashierId, 0, 0);
        grid.add(cashierIdField, 1, 0);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(cashier.getName());
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField(cashier.getAddress());
        grid.add(addressLabel, 0, 2);
        grid.add(addressField, 1, 2);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setText(cashier.getPassword());
        grid.add(passwordLabel, 0, 3);
        grid.add(passwordField, 1, 3);

        Label salaryLabel = new Label("Salary:");
        TextField salaryField = new TextField(String.valueOf(cashier.getSalary()));
        grid.add(salaryLabel, 0, 4);
        grid.add(salaryField, 1, 4);

        Label sectorLabel = new Label("Sector:");
        TextField sectorField = new TextField();
        grid.add(sectorLabel, 0, 5);
        grid.add(sectorField, 1, 5);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        grid.add(submitButton, 0, 6);
        grid.add(cancelButton, 1, 6);

        submitButton.setOnAction(e -> {
            String cashierIdFieldText = cashierIdField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            String password = passwordField.getText();
            String salary = salaryField.getText();
            String sector = sectorField.getText();

            Sector sec = null;
            for (Sector s : administrator.getSectors()) {
                if (s.getSectorName().equals(sector)) {
                    sec = s;
                    break;
                }
            }

            if (sec == null) {
                adminView.showErrorAlert("Not possible, not a real sector");
                return;
            }

            cashier.setCashierId(cashierIdFieldText);
            cashier.setName(name);
            cashier.setAddress(address);
            cashier.changePass(cashier.getPassword(), password, password);
            cashier.setSalary(Double.parseDouble(salary));
            cashier.setSector(sec);

            System.out.println("Details Submitted: " + cashierIdFieldText + ", Name: " + name + ", Salary: " + salary + ", Sector: " + sector);
            Stage formStage = (Stage) grid.getScene().getWindow();
            formStage.close();
            showModifyCashier();
        });

        cancelButton.setOnAction(e -> {
            cashierIdField.clear();
            nameField.clear();
            addressField.clear();
            passwordField.clear();
            salaryField.clear();
            sectorField.clear();
        });
        Scene formScene = new Scene(grid, 400, 300);
        Stage formStage = new Stage();
        formStage.setTitle("Modify Cashier Form");
        formStage.setScene(formScene);
        formStage.show();
    }
    
    
    public void ModifyManager() {
        String selectedManagerName;
        selectedManagerName = adminView.getManagerListView().getSelectionModel().getSelectedItem();
        if (selectedManagerName != null) {
            String managerName = selectedManagerName.split(" ")[0];
            manager = findManagerByName(managerName);
        }

        if (manager == null) {
            adminView.showErrorAlert("Select a manager");
            return;
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(9, 9, 12, 11));
        grid.setHgap(10);
        grid.setVgap(10);

        Label managerId = new Label("Manager Id:");
        TextField managerIdField = new TextField(manager.getManagerId());
        grid.add(managerId, 0, 0);
        grid.add(managerIdField, 1, 0);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField(manager.getName());
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField(manager.getAddress());
        grid.add(addressLabel, 0, 2);
        grid.add(addressField, 1, 2);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setText(manager.getPassword());
        grid.add(passwordLabel, 0, 3);
        grid.add(passwordField, 1, 3);

        Label salaryLabel = new Label("Salary:");
        TextField salaryField = new TextField(String.valueOf(manager.getSalary()));
        grid.add(salaryLabel, 0, 4);
        grid.add(salaryField, 1, 4);

        Label sectorLabel = new Label("Sector:");
        TextField sectorField = new TextField();
        sectorField.setText(String.valueOf(manager.getSectors().get(0).getSectorName()));
        grid.add(sectorLabel, 0, 5);
        grid.add(sectorField, 1, 5);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        grid.add(submitButton, 0, 6);
        grid.add(cancelButton, 1, 6);

        submitButton.setOnAction(e -> {
            String cashierIdFieldText = managerIdField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            String password = passwordField.getText();
            String salary = salaryField.getText();
            String sector = sectorField.getText();

            Sector sec = null;
            for (Sector s : administrator.getSectors()) {
                if (s.getSectorName().equals(sector)) {
                    sec = s;
                    break;
                }
            }

            if (sec == null) {
                adminView.showErrorAlert("Not possible, not a real sector");
                return;
            }

            manager.setManagerId(cashierIdFieldText);
            manager.setName(name);
            manager.setAddress(address);
            manager.changePass(manager.getPassword(), password, password);
            manager.setSalary(Double.parseDouble(salary));
            manager.addSector(sec);

            
            Stage formStage = (Stage) grid.getScene().getWindow();
            formStage.close();
            ModifyManager();
        });

        cancelButton.setOnAction(e -> {
            managerIdField.clear();
            nameField.clear();
            addressField.clear();
            passwordField.clear();
            salaryField.clear();
            sectorField.clear();
        });

        Scene formScene = new Scene(grid, 400, 300);
        Stage formStage = new Stage();
        formStage.setTitle("Modify Manager Form");
        formStage.setScene(formScene);
        formStage.show();
    }



    private void addCashier() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add New Cashier");


        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        DatePicker dateOfBirthPicker = new DatePicker();
        TextField phoneField = new TextField();
        TextField addressField = new TextField();
        TextField cashierIdField = new TextField();
        TextField passwordField = new TextField();
        TextField employeeIdField = new TextField();
        TextField salaryField = new TextField();
        TextField sectorField=new TextField();


        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(new Label("First Name:"), 0, 0);
        gridPane.add(firstNameField, 1, 0);

        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(lastNameField, 1, 1);

        gridPane.add(new Label("Date of Birth:"), 0, 2);
        gridPane.add(dateOfBirthPicker, 1, 2);

        gridPane.add(new Label("Phone Number:"), 0, 3);
        gridPane.add(phoneField, 1, 3);

        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(addressField, 1, 4);

        gridPane.add(new Label("Cashier ID:"), 0, 5);
        gridPane.add(cashierIdField, 1, 5);

        gridPane.add(new Label("Password:"), 0, 6);
        gridPane.add(passwordField, 1, 6);

        gridPane.add(new Label("Employee ID:"), 0, 7);
        gridPane.add(employeeIdField, 1, 7);

        gridPane.add(new Label("Salary:"), 0, 8);
        gridPane.add(salaryField, 1, 8);

        gridPane.add(new Label("Sector "), 0, 9);
        gridPane.add(sectorField, 1, 9);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, submitButton, cancelButton);

        gridPane.add(buttonBox, 1, 10);


        submitButton.setOnAction(event -> {
            try {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                LocalDate dateOfBirth = dateOfBirthPicker.getValue();
                int phone = Integer.parseInt(phoneField.getText());
                String address = addressField.getText();
                String cashierId = cashierIdField.getText();
                String password = passwordField.getText();
                String employeeId = employeeIdField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String sectorName=sectorField.getText();

                Sector sector=checkSector(sectorName);


                Cashier c= new Cashier(firstName,lastName,dateOfBirth,phone,address,cashierId,password,employeeId,
                        "Cashier",salary,sector,sector.getItems());
                administrator.addCashier(c);
                popupStage.close();
            } catch (Exception e) {
                showAlert("Invalid Input", "Please fill out the form correctly.");
            }
        });

        cancelButton.setOnAction(event -> popupStage.close());


        Scene scene = new Scene(gridPane, 400, 450);
        popupStage.setScene(scene);
        popupStage.showAndWait();
        populateLists();

    }


    private void removeCashier() {
        String selectedCashier = adminView.getCashierListView().getSelectionModel().getSelectedItem();
        if (selectedCashier != null) {
            administrator.removeCashier(selectedCashier);
            populateLists();
        } else {
            showAlert("Remove Cashier", "No cashier selected.");
        }
    }

    private Sector checkSector(String sectorName) {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream("C:\\Users\\drita\\IdeaProjects\\demo1\\src\\main\\java\\aHH\\dao\\sector.dat"))) {
            Sector s;
            while (true) {
                s = (Sector) reader.readObject();
                if(s.getSectorName().equals(sectorName)) {
                    System.out.println("yay");
                    return s;
                }
            }
        } catch (EOFException ex) {
            System.out.println("Reached the end of file");
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void modifyCashier() {
        boolean t=false;
        String existingId = showInputDialog("Modify Cashier ID", "Enter Existing Cashier ID:");
        if (existingId != null) {

            for(Cashier cashier:administrator.getCashiers()) {
                if(cashier.getUsername().equals(existingId)) {
                    t=true;
                    String newId = showInputDialog("Modify Cashier ID", "Enter New Cashier ID:");
                    if (newId != null) {
                        administrator.modifyCashierId(newId, existingId);
                        populateLists();
                    }
                }
            }
        }
        if(t==false) {
            showAlert("Incorrect","Not a correct ID");
        }
    }

    private void revokePermission() {
        String selectedEmployee = null;
        if (adminView.getManagerListView().getSelectionModel().getSelectedItem() != null) {
            selectedEmployee = adminView.getManagerListView().getSelectionModel().getSelectedItem();
        }

        else if (adminView.getCashierListView().getSelectionModel().getSelectedItem() != null) {
            selectedEmployee = adminView.getCashierListView().getSelectionModel().getSelectedItem();
        }

        if (selectedEmployee != null) {
            String employeeId = selectedEmployee.split(" - ")[1];
            administrator.revokePermission(employeeId);
            showAlert("Permission Revoked", "Permission revoked for employee ID: " + employeeId);
        } else {
            showAlert("No Selection", "Please pick a Manager or Cashier.");
        }
    }


    public  Cashier findCashierByName(String name) {
        String n;
        String s;
        for (Cashier cashier : administrator.getCashiers()) {
            n=name.split(" ")[0];
            //s=name.split(" ")[1];
            if (cashier.getName().equals(n) ) {
                return cashier;
            }
        }
        return null;
    }

    
    public void handleViewReports() {
    	boolean t=false;
    	
    	 String selectedCashierName = adminView.getCashierListView().getSelectionModel().getSelectedItem();
         if (selectedCashierName != null) {
             String cashierName = selectedCashierName.split(" - ")[0];
             Cashier selectedCashier = findCashierByName(cashierName);
             if (selectedCashier != null) {
            	 t=true;
                 viewReportsForCashier(selectedCashier);
                 System.out.println("k");
                 return;
             }
         }
         
    	
         String selectedManagerName = adminView.getManagerListView().getSelectionModel().getSelectedItem();
         if (selectedManagerName != null) {
             String managerName = selectedManagerName.split(" - ")[0];
             Manager selectedManager = findManagerByName(managerName);
             if (selectedManager != null) {
            	 t=true;
                 viewReportsFromManager(selectedManager);
                 System.out.println("k");
                 return;
             }         
          }
    	      
         else {
         showAlert("Not possible", "Select a manager or cashier");
         }
    
    }


    private  Manager findManagerByName(String name) {
        String n;
       
        for (Manager manager : administrator.getManagers()) {
            n=name.split(" ")[0];
            ;
            if (manager.getName().equals(n) ) {
                return manager;
            }
        }
        return null;
    }


    private void givePermission() {
        String selectedEmployee = null;
        if (adminView.getManagerListView().getSelectionModel().getSelectedItem() != null) {
            selectedEmployee = adminView.getManagerListView().getSelectionModel().getSelectedItem();
        }

        else if (adminView.getCashierListView().getSelectionModel().getSelectedItem() != null) {
            selectedEmployee = adminView.getCashierListView().getSelectionModel().getSelectedItem();
        }

        if (selectedEmployee != null) {
            String employeeId = selectedEmployee.split(" - ")[1];
            administrator.givePermission(employeeId);
            showAlert("Permission given", "Permission given for employee ID: " + employeeId);
        } else {
            showAlert("No Selection", "Please pick a Manager or Cashier.");
        }
    }
    
    private void handleGeneralReports() {
    	 Stage popupStage = new Stage();
    	 double sum=0.0;
    	 

         popupStage.initModality(Modality.APPLICATION_MODAL);
         popupStage.setTitle("Monthly expenses :");
         Label header = new Label("Monthly expenses Report");
         header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");
         GridPane contentGrid = new GridPane();
         contentGrid.setHgap(10);
         contentGrid.setVgap(10);
         contentGrid.setPadding(new Insets(10));
         contentGrid.setAlignment(Pos.CENTER_LEFT);

         contentGrid.add(new Label("Total for cashier salary:"), 0, 0);
         for(Cashier cashier:administrator.getCashiers()) {
        	 cashier.update();
        	 sum+=cashier.getSalary();
         }
         contentGrid.add(new Label(sum+" "), 1, 0);


         contentGrid.add(new Label("Total for manager salary:"), 0, 1);
         sum=0.0;
         for(Manager manager:administrator.getManagers()) {
        	 sum+=manager.getSalary();
         }
         contentGrid.add(new Label(sum+" "), 1, 1);

         contentGrid.add(new Label("Total expenses for items bought:"), 0, 2);
         sum=0.0;
         for(Manager manager:administrator.getManagers()) {
        	 sum+=manager.getTotalAmountSpent();
         }
         contentGrid.add(new Label(" "+String.valueOf(sum)), 1, 2);

         Separator separator = new Separator();

         Button closeButton = new Button("Close");

         closeButton.setOnAction(e -> popupStage.close());

         HBox footer = new HBox(closeButton);

         footer.setAlignment(Pos.CENTER);

         footer.setPadding(new Insets(10));

         VBox layout = new VBox(header, separator, contentGrid, footer);

         layout.setSpacing(10);

         layout.setPadding(new Insets(15));

         layout.setAlignment(Pos.CENTER);


         Scene popupScene = new Scene(layout, 350, 250);

         popupStage.setScene(popupScene);

         popupStage.showAndWait();
    }

    private void viewReportsForCashier(Cashier c) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Cashier Performance Report");

        Label header = new Label("Cashier Performance Report");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        GridPane contentGrid = new GridPane();
        contentGrid.setHgap(10);
        contentGrid.setVgap(10);
        contentGrid.setPadding(new Insets(10));
        contentGrid.setAlignment(Pos.CENTER_LEFT);


        contentGrid.add(new Label("Cashier Name: "+" Surname: "), 0, 0);
        contentGrid.add(new Label(c.getName()+" "+c.getSurname()), 1, 0);

        contentGrid.add(new Label("Total Revenue:"), 0, 1);
        contentGrid.add(new Label(String.valueOf(c.getTotalAmountWon())), 1, 1);

        contentGrid.add(new Label("Total Bills:"), 0, 2);
        contentGrid.add(new Label(String.valueOf(c.getTotalAmountOfBills())), 1, 2);

        contentGrid.add(new Label("Accuracy Percentage:"), 0, 3);
        contentGrid.add(new Label( c.getDayOfWork()/c.getTotalAmountWon()+" %"), 1, 3);


        Separator separator = new Separator();


        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());
        HBox footer = new HBox(closeButton);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));


        VBox layout = new VBox(header, separator, contentGrid, footer);
        layout.setSpacing(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(layout, 350, 250);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }



    private void viewReportsFromManager(Manager m) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Manager Performance Report");

        Label header = new Label("Manager Performance Report");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        GridPane contentGrid = new GridPane();
        contentGrid.setHgap(10);
        contentGrid.setVgap(10);
        contentGrid.setPadding(new Insets(10));
        contentGrid.setAlignment(Pos.CENTER_LEFT);


        contentGrid.add(new Label("Manager : "), 0, 0);
        contentGrid.add(new Label(m.getName()+" "+m.getSurname()), 1, 0);

        contentGrid.add(new Label("Cashiers Under Supervision:"), 0, 1);
        contentGrid.add(new Label(String.valueOf(m.getCashiers().size())), 1, 1);

        contentGrid.add(new Label("Sectors Responsible:"), 0, 2);
        contentGrid.add(new Label(String.valueOf(m.getSectors().size())), 1, 2);

        contentGrid.add(new Label("Suppliers in contact:"), 0, 3);
        contentGrid.add(new Label( m.getSuppliers().size()+" "), 1, 3);


        Separator separator = new Separator();


        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());
        HBox footer = new HBox(closeButton);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));


        VBox layout = new VBox(header, separator, contentGrid, footer);
        layout.setSpacing(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(layout, 350, 250);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private String showInputDialog(String title, String header) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        return dialog.showAndWait().orElse(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    private void addItems() {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Add New Item");

            TextField itemNameField = new TextField();
            TextField priceField = new TextField();
            TextField quantityField = new TextField();
            TextField sectorField = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.setVgap(10);
            gridPane.setHgap(10);

            gridPane.add(new Label("Item Name:"), 0, 0);
            gridPane.add(itemNameField, 1, 0);

            gridPane.add(new Label("Price:"), 0, 1);
            gridPane.add(priceField, 1, 1);

            gridPane.add(new Label("Quantity:"), 0, 2);
            gridPane.add(quantityField, 1, 2);

            gridPane.add(new Label("Sector:"), 0, 3);
            gridPane.add(sectorField, 1, 3);

            Button submitButton = new Button("Submit");
            Button cancelButton = new Button("Cancel");
            HBox buttonBox = new HBox(10, submitButton, cancelButton);

            gridPane.add(buttonBox, 1, 4);
            submitButton.setOnAction(event -> {
                try {
                    String itemName = itemNameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    String sectorName = sectorField.getText();

                    Sector sector = checkSector(sectorName);
                    sector.updateSectorItems(quantity);
                    Item newItem = new Item(itemName,12, price,
                   supplier,quantity);
                    sector.addNewItem(newItem);
                    popupStage.close();
                } catch (Exception e) {
                    showAlert("Invalid Input", "Please fill out the form correctly.");
                }
            });
            cancelButton.setOnAction(event -> popupStage.close());
            Scene scene = new Scene(gridPane, 400, 300);
            popupStage.setScene(scene);
            popupStage.showAndWait();
            populateLists();
        }


    private void handleTotalRenevue() {
    	Stage popupStage = new Stage();
   	 double sum=0.0;

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Monthly revenue :");
        Label header = new Label("Monthly revenue Report");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");
        GridPane contentGrid = new GridPane();
        contentGrid.setHgap(10);
        contentGrid.setVgap(10);
        contentGrid.setPadding(new Insets(10));
        contentGrid.setAlignment(Pos.CENTER_LEFT);

        contentGrid.add(new Label("Total Revenue received by sales:"), 0, 0);
        for(Cashier cashier:administrator.getCashiers()) {
       	 sum+=cashier.getTotalAmountWon();
        }
        contentGrid.add(new Label(sum+" "), 1, 0);
        
        contentGrid.add(new Label("Total Bills created:"), 0, 1);
        int sum1=0;
        for(Cashier cashier:administrator.getCashiers()) {
       	 sum1+=cashier.getBillsCount();
        }
        contentGrid.add(new Label(sum1+" "), 1, 1);


        Separator separator = new Separator();

        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> popupStage.close());

        HBox footer = new HBox(closeButton);

        footer.setAlignment(Pos.CENTER);

        footer.setPadding(new Insets(10));

        VBox layout = new VBox(header, separator, contentGrid, footer);

        layout.setSpacing(10);

        layout.setPadding(new Insets(15));

        layout.setAlignment(Pos.CENTER);


        Scene popupScene = new Scene(layout, 350, 250);

        popupStage.setScene(popupScene);

        popupStage.showAndWait();
       
    }

    public Item findItemByName(String name) {
        String itemName;
        for (Item item : administrator.getItems()) {
            itemName = name.split(" ")[0];
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    
 
}
