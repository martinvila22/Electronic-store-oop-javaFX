package view;
import model.*;
import control.*;
import dao.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class ManagerDashboardView {

    private Stage stage;
    private Manager manager;
    public ListView<String> cashierListView = new ListView<>();
    private ListView<String> productListView = new ListView<>();
    private Button supplyStoreButton;
    private Button removeCashierButton;
    private Button viewReportsButton;
    private Button addProductButton;
    private Button removeProductButton;
    private Button viewSectorsButton;
    private Button removeItemFromSectorButton;
    private MenuBar menuBar;
    private Menu profileMenu;
    private MenuItem profileMenuItem;
    private MenuItem signOutMenuItem;
    private MenuItem showSupplyStoreMenuItem;
    private TextField cashierSearchField;
    private TextField productSearchField;
    private Supplier Supplier;

    List<String> validCategories ;
    private MenuItem showSupplierMenuItem;
    
    public List<String> getValidCategories() {

        return validCategories;

    }



    public void setValidCategories(List<String> validCategories) {

        this.validCategories = validCategories;

    }



    private Button submitButton;





    public ManagerDashboardView(Stage stage, Manager manager) {

        this.stage = stage;

        this.manager = manager;



        supplyStoreButton = createButton("Supply the Store");

        removeCashierButton = createButton("Remove Cashier");

        viewReportsButton=createButton("View Reports");

        addProductButton = createButton("Add Product");

        removeProductButton = createButton("Remove Product");

        viewSectorsButton = createButton("View Sectors");

        removeItemFromSectorButton=createButton("Remove From Sector");

        submitButton=createButton("Submit");

        createMenuBar();



        cashierSearchField = new TextField();

        cashierSearchField.setPromptText("Search Cashiers...");

        cashierSearchField.textProperty().addListener((observable, oldValue, newValue) -> updateCashierListView(newValue));



        productSearchField = new TextField();

        productSearchField.setPromptText("Search Products...");

        productSearchField.textProperty().addListener((observable, oldValue, newValue) -> updateProductListView(newValue));



        cashierListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            viewReportsButton.setDisable(newValue == null);

        });

        new ManagerController(manager,this);



        showManagerDashboard();

    }



    public void showManagerDashboard() {



        VBox managerLayout = new VBox(10);



        managerLayout.getChildren().addAll(menuBar,

                new Label("Cashiers under supervision:"),

                cashierSearchField,

                cashierListView,

                supplyStoreButton,

                removeCashierButton,

                viewReportsButton,

                new Label("Products/Items:"),

                productSearchField,

                productListView,

                addProductButton,

                removeProductButton,

                viewSectorsButton

        );

        updateCashierListView("");

        updateProductListView("");



        Scene scene = new Scene(managerLayout, 600, 450);

        stage.setScene(scene);

        stage.setTitle("Manager Dashboard");

        stage.show();

    }


    private Button createButton(String text) {

        Button button = new Button(text);

        button.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #8FBC8F; -fx-text-fill: white;");

        button.setMinWidth(150);

        return button;

    }

    private void createMenuBar() {
    	menuBar = new MenuBar();

        profileMenu = new Menu("Profile");

        profileMenuItem = new MenuItem("Profile");

        signOutMenuItem = new MenuItem("Sign Out");
        
        showSupplyStoreMenuItem = new MenuItem("Show Supplier");


        profileMenuItem.setOnAction(e -> handleProfile());

        signOutMenuItem.setOnAction(e -> handleSignOut());
        
        showSupplyStoreMenuItem.setOnAction(e -> {
            handleShowSupplyStore();
        });



        profileMenu.getItems().addAll(profileMenuItem, signOutMenuItem, showSupplyStoreMenuItem);

        menuBar.getMenus().addAll(profileMenu);

    }

    private void handleShowSupplyStore() {
        Manager currentManager = this.manager; 

        if (currentManager != null) {
            List<Supplier> suppliers = currentManager.getSuppliers();
            Stage supplyStoreStage = new Stage();
            supplyStoreStage.initModality(Modality.APPLICATION_MODAL);
            supplyStoreStage.setTitle("Suppliers for " + currentManager.getName());

            ListView<String> supplierListView = new ListView<>();
            for (Supplier supplier : suppliers) {
                supplierListView.getItems().add(supplier.getName() + " - " + supplier.getName());
            }
            if (suppliers.isEmpty()) {
                supplierListView.getItems().add("No suppliers found.");
            }

            VBox vbox = new VBox(10, new Label("Suppliers for " + currentManager.getName()), supplierListView);
            Scene scene = new Scene(vbox, 400, 300);
            supplyStoreStage.setScene(scene);
            supplyStoreStage.show();
        } else {
            showAlert("Error", "No manager found or logged in.");
        }
    }

    private void showAlert(String error, String s) {
    }


    private void handleProfile() {

        String profileDetails = "Manager ID: " + manager.getManagerId() + "\n";

        profileDetails += "Name: " + manager.getName() + " " + manager.getSurname() + "\n";

        profileDetails += "Role: " + manager.getRole() + "\n";

        profileDetails += "Salary: $" + manager.getSalary();



        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Profile");

        alert.setHeaderText("Profile Information");

        alert.setContentText(profileDetails);

        alert.showAndWait();

    }



    private void handleSignOut() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Sign Out");

        alert.setHeaderText("Are you sure you want to sign out?");

        alert.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                stage.close();

            }

        });

    }


    public void showAddProductDialog() {



    }



    public void showErrorAlert(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }



    public void updateCashierListView(String query) {

        cashierListView.getItems().clear();

        for (Cashier cashier : manager.getCashiers()) {



            if (cashier.getName().toLowerCase().contains(query.toLowerCase()) || cashier.getCashierId().contains(query)) {

                cashierListView.getItems().add(cashier.getCashierId() + " - " + cashier.getName());

            }

        }

    }
    public void updateProductListView(String query) {
        manager.update();
        productListView.getItems().clear();
        for (Item item : manager.getItems()) {
            {
                if(item.getIsItemOutOfStock()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Item out of stock");
                    alert.setHeaderText("Item name:"+item.getItemName());
                    alert.setContentText("Item's quantity:"+item.getQuantity());
                    alert.showAndWait();
                }
                productListView.getItems().add(item.getItemName() + " - $" + item.getSellingPrice() + " - Qty: " + item.getQuantity());
            }
        }

    }



    public  Cashier findCashierByName(String name) {

        for (Cashier cashier : manager.getCashiers()) {

            if (cashier.getName().equals(name)) {

                return cashier;

            }

        }

        return null;

    }













        





    public Button getSupplyStoreButton() {

        return this.supplyStoreButton;

    }



    public Button getRemoveCashierButton() {

        return this.removeCashierButton;

    }

    public Button getViewReportsButton() {

        return this.viewReportsButton;

    }



    public Button getAddProductButton() {

        return this.addProductButton;

    }



    public Button getRemoveProductButton() {

        return this.removeProductButton;

    }

    public Button getRemoveProductFromSectorButton() {

        return this.removeItemFromSectorButton;

    }



    public Button getViewSectorsButton() {

        return this.viewSectorsButton;

    }

    public Button getSubmitButton() {

        return this.submitButton;

    }

    public ListView<String> getCashierListView() {

        return this.cashierListView;

    }



    public ListView<String> getProductListView() {

        return this.productListView;

    }

}