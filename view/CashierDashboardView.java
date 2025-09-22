package view;

import control.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cashier;
import model.Item;
import dao.*;

import java.io.*;
import java.util.ArrayList;

public class CashierDashboardView {

    private Stage stage;
    private ListView<String> productList;
    private ListView<String> cartList;
    private Label totalLabel;
    private File inputFile = new File("src/dao/items.dat");
    private Cashier cashier;
    private ListView<String> categoryList;

    private Button addToCartButton;
    private Button viewCartButton;
    private Button startShiftButton;
    private Button endShiftButton;
    private Button createBillsButton;
    private Button removeItemButton;
    private Button generateBillsButton;

    public Button getRemoveItemButton() {
        return removeItemButton;
    }

    public void setRemoveItemButton(Button removeItemButton) {
        this.removeItemButton = removeItemButton;
    }

    public CashierDashboardView(Stage stage, Cashier cashier) {
        this.stage = stage;
        this.cashier = cashier;

        addToCartButton = createButton("Add to Cart", 15);
        viewCartButton = createButton("View Cart", 15);
        startShiftButton = createButton("Start Shift", 15);
        endShiftButton = createButton("End Shift", 15);
        createBillsButton = createButton("Create Bill", 15);
        removeItemButton = createButton("Remove Item", 15);
        generateBillsButton=createButton("Generate Bills",15);
        new CashierDashboardController(cashier, this);

        showDashboard(createMenuBar(), createButtonLayout(), createItemsLayout(getProductList()));
    }

    public void showDashboard(MenuBar menuBar, VBox buttonsLayout, VBox itemsLayout) {
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setLeft(buttonsLayout);
        layout.setCenter(itemsLayout);

        Scene scene = new Scene(layout, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Cashier Dashboard");
        stage.show();
    }

    public VBox createButtonLayout() {
        VBox buttonsLayout = new VBox(15);
        buttonsLayout.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10;");
        buttonsLayout.getChildren().addAll(
                createLabel("Cashier Dashboard", 18, "bold"),
                addToCartButton,
                viewCartButton,
                startShiftButton,
                endShiftButton,
                generateBillsButton
        );
        buttonsLayout.setPrefWidth(250);
        return buttonsLayout;
    }

    public Button createButton(String text, int fontSize) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: " + fontSize + "px; -fx-padding: 10px; -fx-background-color: #8FBC8F; -fx-text-fill: white;");
        button.setMinWidth(200);
        return button;
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
    	String profileDetails = "Cashier ID: " + cashier.getCashierId() + "\n";
        profileDetails += "Name: " + cashier.getName() + " " + cashier.getSurname() + "\n";
        profileDetails += "Role: " + cashier.getRole() + "\n";
        profileDetails += "Salary: $" + cashier.getSalary();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile");
        alert.setHeaderText("Profile Information");
        alert.setContentText(profileDetails);
        alert.showAndWait();
        System.out.println("Signing out...");
    }

    public VBox createItemsLayout(ListView<String> productList) {
    	
       Label Label = createLabel(cashier.getSector().getSectorName()+" ",15,"bold");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Items...");
        searchBar.setOnKeyReleased(event -> filterItems(event));

        VBox itemsLayout = new VBox(15);
        itemsLayout.setStyle("-fx-background-color: white; -fx-padding: 10;");
        itemsLayout.getChildren().addAll(
                createLabel("Sector", 16, "bold"),
                Label,
                createLabel("Search Items", 16, "bold"),
                searchBar,
                productList
        );

        productList.setOnMouseClicked(event -> {
            String selectedProduct = productList.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                showProductDetails(selectedProduct);
            }
        });

        return itemsLayout;
    }

    private void showProductDetails(String selectedProduct) {
        String[] parts = selectedProduct.split(" - ");
        String itemName = parts[0];
        double itemPrice = Double.parseDouble(parts[1]);

        Stage productDetailsStage = new Stage();
        VBox productDetailsLayout = new VBox(10);
        Label nameLabel = new Label("Product: " + itemName);
        Label quantityLabel=new Label("Quantity: "+getItemQuantity(itemName));
        Label priceLabel = new Label("Price: $" + itemPrice);
        Label categoryLabel = new Label("Category: " + getItemCategory(itemName));
        Label descriptionLabel = new Label("Supplier: " + getItemSupplier(itemName));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> productDetailsStage.close());

        productDetailsLayout.getChildren().addAll(nameLabel,quantityLabel, priceLabel, categoryLabel, descriptionLabel, closeButton);
        Scene productDetailsScene = new Scene(productDetailsLayout, 300, 200);
        productDetailsStage.setScene(productDetailsScene);
        productDetailsStage.setTitle(itemName + " Details");
        productDetailsStage.show();
    }

    private String getItemCategory(String itemName) {
        for(Item item:cashier.getItem()) {
        	if(item.getItemName().equals(itemName))
        		return cashier.getSector().getSectorName()+" ";
        }
        return "Unknown";
    }

    

    private void filterItems(KeyEvent event) {
        String query = ((TextField) event.getSource()).getText().toLowerCase();
        ObservableList<String> filteredItems = FXCollections.observableArrayList();

        for (Item item : getItems()) {
            if (item.getItemName().toLowerCase().contains(query)) {
                filteredItems.add(item.getItemName()+" -"+item.getSellingPrice());
            }
        }

        productList.setItems(filteredItems);
    }

    private void filterItemsByCategory(String category) {
        ObservableList<String> filteredItems = FXCollections.observableArrayList();

        for (Item item : getItems()) {
            if (cashier.getSector().equals(category)) {
                filteredItems.add(item.getItemName());
            }
        }

        productList.setItems(filteredItems);
    }

    public ListView<String> getProductList() {
        ObservableList<String> items = FXCollections.observableArrayList();
       for(Item item:cashier.getItem()) {
        if(item.getIsItemOutOfStock()) {
        	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
 	        alert.setTitle("Item out of stock");
 	        alert.setHeaderText("Item name:"+item.getItemName());
 	        alert.setContentText("Item's quantity:"+item.getQuantity());
 	        alert.showAndWait();
        }
        items.add(item.getItemName()+" - "+item.getSellingPrice());
       }
        productList = new ListView<>(items);
        return productList;
    }

    public Label getTotalLabel() {
        if (totalLabel == null) {
            totalLabel = new Label();
        }
        return totalLabel;
    }

    private Label createLabel(String text, int fontSize, String fontWeight) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-weight: " + fontWeight + "; -fx-text-fill: black;");
        return label;
    }
    private int getItemQuantity(String itemName) {
    	for(int i=0;i<cashier.getItem().size();i++) {
    		if(cashier.getItem().get(i).getItemName().equals(itemName))
    			return cashier.getItem().get(i).getQuantity();
    	}
    	return 0;
    }

    private ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for(Item item:cashier.getItem()) {
        	items.add(item);
        }
        return items;
    }
    public String getItemSupplier(String itemName) {
    	for(Item item:cashier.getItem()) {
        	if(item.getItemName().equals(itemName))
        		return item.getSupplier().getName()+" ";
        }
    	return "Unknown";
    }

    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public Button getViewCartButton() {
        return viewCartButton;
    }

    public Button getStartShiftButton() {
        return startShiftButton;
    }

    public Button getEndShiftButton() {
        return endShiftButton;
    }

    public Button getCreateBillsButton() {
        return this.createBillsButton;
    }
    public Button getGenerateBillsButton() {
    	return this.generateBillsButton;
    }

    public ListView<String> getItemsListView() {
        return getItemsListView();
    }

    public Scene getScene() {
        return getScene();
    }

    public TextField getSearchBar() {
        return getSearchBar();
    }
}