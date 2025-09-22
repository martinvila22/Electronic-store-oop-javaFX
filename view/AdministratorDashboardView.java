package view;

import control.AdministratorDashboardController;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class AdministratorDashboardView {
    private Manager manager;
    private Stage stage;
    private ListView<String> managerListView = new ListView<>();
    private ListView<String> cashierListView = new ListView<>();
    private ListView<String> sectorListView=new ListView<>();
    private Administrator admin;
    private Button addManagerButton;
    private Button removeManagerButton;
    private Button modifyManagerButton;
    private Button addCashierButton;
    private Button removeCashierButton;
    private Button modifyCashierButton;
    private Button revokePermissionButton;
    private Button givePermissionButton;
    private Button viewReportsButton;
    private Button addItemsButton;
    private Button checkTotalRevenueButton;
    private Button modifyItemsButton;
    private Button generalReportsButton;
    private MenuBar menuBar;
    private Menu profileMenu;
    private MenuItem profileMenuItem;
    private MenuItem signOutMenuItem;

    public Button getGeneralReportsButton() {
        return generalReportsButton;
    }

    public void setGeneralReportsButton(Button generalReportsButton) {
        this.generalReportsButton = generalReportsButton;
    }
    private Cashier cashier;
    private ListView<String> itemListView;
    private Item Item;

    public AdministratorDashboardView(Stage stage, Administrator admin) {
        this.stage = stage;
        this.admin = admin;
        addManagerButton = createButton("Add Manager", 12);
        removeManagerButton = createButton("Remove Manager", 12);
        modifyManagerButton = createButton("Modify Manager ", 12);
        addCashierButton = createButton("Add Cashier", 12);
        removeCashierButton = createButton("Remove Cashier", 12);
        modifyCashierButton = createButton("Modify Cashier ", 12);
        revokePermissionButton = createButton("Revoke Permission", 12);
        givePermissionButton = createButton("Give Permission", 12);
        viewReportsButton = createButton("View Reports", 12);
        addItemsButton = createButton("Add Item", 12);
        checkTotalRevenueButton = createButton("Total Revenue Reports", 12);
        modifyItemsButton = createButton("modify Item", 12);
        generalReportsButton=createButton("Show expenses Reports",12);
        managerListView = getAvailableManagers();
        cashierListView = getAvailableCashiers();
        sectorListView=getAvailableSectors();
        createMenuBar();
        new AdministratorDashboardController(stage, admin, this);
        showDashboard(createLayout());
    }

    public void showDashboard(VBox layout) {
        Scene scene = new Scene(layout, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Administrator Dashboard");
        stage.show();
    }

    public VBox createLayout() {

        MenuBar menuBar = new MenuBar();

        Menu profileMenu = new Menu("Profile");

        MenuItem profileItem = new MenuItem("View Profile");
        profileItem.setOnAction(e -> handleProfile());

        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> handleSignOut());

        profileMenu.getItems().addAll(profileItem, logoutItem);

        menuBar.getMenus().add(profileMenu);

        VBox layout = new VBox(10);

        HBox btns = new HBox(15);
        btns.getChildren().addAll(addManagerButton, removeManagerButton, modifyManagerButton, viewReportsButton);

        HBox btns1 = new HBox(15);
        btns1.getChildren().addAll(addCashierButton, removeCashierButton, modifyCashierButton, revokePermissionButton, givePermissionButton);

        HBox btns2 = new HBox(10);
        btns2.getChildren().addAll(addItemsButton, generalReportsButton, checkTotalRevenueButton);

        layout.getChildren().addAll(
                menuBar,
                new Label("Managers:"),
                managerListView,
                btns,
                new Label("Cashiers:"),
                cashierListView,
                btns1,
                new Label("Sectors:"),
                sectorListView,
                btns2
        );

        return layout;
    }
    private void handleProfile() {

        String profileDetails = "Manager ID: " + admin.getAdminiId() + "\n";

        profileDetails += "Name: " + admin.getName() + " " + admin.getSurname() + "\n";

        profileDetails += "Role: " + admin.getRole() + "\n";

        profileDetails += "Salary: $" + admin.getSalary();



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




    private Manager findManagerByName(String managerName) {
        for (Manager manager : admin.getManagers()) {
            if (manager.getName().equals(managerName)) {
                return manager;
            }
        }
        return null;
    }


    private Cashier findCashierByName(String cashierName) {
        for (Cashier cashier : admin.getCashiers()) {
            if (cashier.getName().equals(cashierName)) {
                return cashier;
            }
        }
        return null;
    }
   
    private Item findItemByName(String itemName) {
        for (Item item : admin.getItems()) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public MenuBar createMenuBar() {
        Menu profileMenu = new Menu("Profile");
        MenuItem viewProfileItem = new MenuItem("View Profile");
        MenuItem signOutItem = new MenuItem("Sign Out");

        profileMenu.getItems().addAll(viewProfileItem, signOutItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(profileMenu);

        signOutItem.setOnAction(e -> signOut());
        viewProfileItem.setOnAction(e -> viewProfile());

        return menuBar;
    }

    private void signOut() {
        System.out.println("Signing out...");
        stage.close();
    }

    private void viewProfile() {
        String profileDetails = "Admin ID: " + admin.getAdminiId() + "\n";
        profileDetails += "Name: " + admin.getName() + " " + admin.getSurname() + "\n";
        profileDetails += "Role: " + admin.getRole() + "\n";
        profileDetails += "Salary: $" + admin.getSalary();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile");
        alert.setHeaderText("Profile Information");
        alert.setContentText(profileDetails);
        alert.showAndWait();
        System.out.println("Signing out...");
    }

    private Button createButton(String text, int fontSize) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: " + fontSize + "px; -fx-padding: 10px; -fx-background-color: #8FBC8F; -fx-text-fill: white;");
        button.setMinWidth(200);
        return button;
    }

    private TitledPane createManagerSection() {
        TitledPane managerPane = new TitledPane("Manager Actions", new VBox(10));
        VBox managerButtons = new VBox(10);
        managerButtons.getChildren().addAll(
                addManagerButton,
                removeManagerButton,
                modifyManagerButton
        );
        managerPane.setContent(managerButtons);
        managerPane.setCollapsible(true);
        return managerPane;
    }

    private TitledPane createCashierSection() {
        TitledPane cashierPane = new TitledPane("Cashier Actions", new VBox(10));
        VBox cashierButtons = new VBox(10);
        cashierButtons.getChildren().addAll(
                addCashierButton,
                removeCashierButton,
                modifyCashierButton,
                revokePermissionButton,
                givePermissionButton,
                viewReportsButton
        );
        cashierPane.setContent(cashierButtons);
        cashierPane.setCollapsible(true);
        return cashierPane;
    }

    private TitledPane createSectorSection() {
        TitledPane sectorPane = new TitledPane("Sector Actions", new VBox(10));
        VBox sectorButtons = new VBox(10);
        sectorButtons.getChildren().addAll(
                addItemsButton,
                modifyItemsButton,
                checkTotalRevenueButton
        );
        sectorPane.setContent(sectorButtons);
        sectorPane.setCollapsible(true);
        return sectorPane;
    }

    public ListView<String> getAvailableManagers() {
        ObservableList<String> managers = FXCollections.observableArrayList();
        for (Manager manager : admin.getManagers()) {
            managers.add(manager.getName() + " " + manager.getSurname() + " " + manager.getManagerId());
        }
        this.managerListView = new ListView<>(managers);
        return managerListView;
    }

    public ListView<String> getAvailableCashiers() {
        ObservableList<String> cashiers = FXCollections.observableArrayList();
        for (Cashier cashier : admin.getCashiers()) {
            cashiers.add("k");
        }
        this.cashierListView = new ListView<>(cashiers);
        return cashierListView;
    }
    public ListView<String> getAvailableItems() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Item item : admin.getItems()) {
            String itemDetails = item.getItemName() + " - " + item.getSellingPrice();
            items.add(itemDetails);
        }
        this.itemListView = new ListView<>(items);
        return itemListView;
    }

    public ListView<String> getAvailableSectors() {
        ObservableList<String> sector = FXCollections.observableArrayList();
        for (Sector sec : admin.getSectors()) {
            String sectorDetails = sec.getSectorName() + " - " + sec.getNrOfItems();
            sector.add(sectorDetails);
        }
        this.sectorListView = new ListView<>(sector);
        return sectorListView;
    }

    public ListView<String> getManagerListView() {
        return managerListView;
    }

    public ListView<String> getCashierListView() {
        return cashierListView;
    }
    public ListView<String> getItemListView() {
        return itemListView;
    }


    public Button getAddManagerButton() {
        return addManagerButton;
    }

    public Button getRemoveManagerButton() {
        return removeManagerButton;
    }

    public Button getModifyManagerButton() {
        return modifyManagerButton;
    }

    public Button getAddCashierButton() {
        return addCashierButton;
    }

    public Button getRemoveCashierButton() {
        return removeCashierButton;
    }

    public Button getModifyCashierButton() {
        return modifyCashierButton;
    }

    public Button getRevokePermissionButton() {
        return revokePermissionButton;
    }

    public Button getGivePermissionButton() {
        return givePermissionButton;
    }

    public Button getViewReportsButton() {
        return viewReportsButton;
    }

    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Button getAddItemsButton() {
        return addItemsButton;
    }
    public Button getTotalRevenueButton() {
        return checkTotalRevenueButton;
    }
    public Button getModifyItemsButton() {
        return modifyItemsButton;
    }

}
