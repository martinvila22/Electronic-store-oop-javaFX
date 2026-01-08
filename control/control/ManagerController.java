package control;

import model.*;
import view.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerController {

    private Manager manager;
    private ManagerDashboardView view;
    boolean t=true;
    Supplier s=null;
    
    public ManagerController(Manager manager, ManagerDashboardView view) {
        this.manager = manager;
        this.view = view;

        view.getCashierListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            view.getViewReportsButton().setDisable(newValue == null); 
        });

        this.view.getSupplyStoreButton().setOnAction(e -> handleSupplyStore());
        this.view.getRemoveCashierButton().setOnAction(e -> {
        	String selectedCashier = view.getCashierListView().getSelectionModel().getSelectedItem();
        	if(selectedCashier!=null) {
        		String cashierId = selectedCashier.split(" - ")[0];
        		handleRemoveCashier(cashierId);
        		 view.updateCashierListView("");
        	}
        	else {
        		view.showErrorAlert("Please select a cashier to remove.");
        	}
        });

       this.view.getRemoveProductFromSectorButton().setOnAction(e -> handleRemoveProductFromSector());
        this.view.getViewReportsButton().setOnAction(e-> {

            String selectedCashierName = view.getCashierListView().getSelectionModel().getSelectedItem();
            if (selectedCashierName != null) {
                String cashierName = selectedCashierName.split(" - ")[1]; // Get the name part
                Cashier selectedCashier = view.findCashierByName(cashierName);
                if (selectedCashier != null) {
                    handleViewReports(selectedCashier);
                }
            }
            });
        this.view.getAddProductButton().setOnAction(e -> handleAddProduct());
        this.view.getRemoveProductButton().setOnAction(e -> {
        String selectedProduct = view.getProductListView().getSelectionModel().getSelectedItem();
        if(selectedProduct!=null) {
        handleRemoveProduct(selectedProduct);
        view.updateProductListView(" ");
        manager.update();
        }else {
        	view.showErrorAlert("Please select a product to remove.");
        }
        });
    
    
        this.view.getViewSectorsButton().setOnAction(e -> handleViewSectors());
    }

    public void handleSupplyStore() {
        
        Stage addSupplyStore = new Stage();
        addSupplyStore.setTitle("Supply the Store");

        TextField itemNameField = new TextField();
        itemNameField.setPromptText("Enter Item name");

        TextField quantityField=new TextField();
        quantityField.setPromptText("Enter the product's quantity");
        

        view.getSubmitButton().setOnAction(e -> {
        	double sum=0;
        	boolean t1=false;
            String itemName =itemNameField.getText() ;
            String quantity1 = quantityField.getText();
            if (!itemName.isEmpty() && !quantity1.isEmpty()) {
                for(Item item:manager.getItems()) {
                	if(item.getItemName().equals(itemName)) {
                		t1=true;
                		manager.increaseStock(item,Integer.parseInt(quantity1));
                		sum=item.getPurchasePrice()*Integer.parseInt(quantity1);
                		for(Cashier cashier:manager.getCashiers()) {
                			for(Item items:cashier.getItem()) {
                				if(items.equals(item)) {
                					cashier.updateStock(item);
                				}
                			}
                      		}
                		manager.addTotalAmountSpent(sum);
                	
                		showAlert("Item added","Supplier for Store: "+item.getSupplier().getName());
                		break;
                	}
                }
                view.updateProductListView("");

            } else {
                showErrorAlert("Please enter both cashier ID and name.");
            }
            if(!t1) {
            	showErrorAlert("The item did not exist");
            }
            addSupplyStore.close();
        });
        manager.update();
        VBox layout = new VBox(10);
        layout.getChildren().addAll(itemNameField, quantityField, view.getSubmitButton());
        Scene scene = new Scene(layout, 600, 500);
        addSupplyStore.setScene(scene);
        addSupplyStore.show();
    }
    
    

    public boolean handleRemoveCashier(String cashierID) {
        
        if (cashierID.startsWith("c")) {
            manager.removeCashier(cashierID);
            manager.update();
            return true;
           
        } else {
        
            return false;
        }
        
    }


    public void handleAddProduct() {
        Stage addProductStage = new Stage();
        addProductStage.setTitle("Add Product");

        TextField productNameField = new TextField();
        productNameField.setPromptText("Enter Product Name");

        TextField productDescriptionField = new TextField();
        productDescriptionField.setPromptText("Enter Product Description");

        TextField productPurchasePriceField = new TextField();
        productPurchasePriceField.setPromptText("Enter Product Purchase Price");

        TextField productPriceField = new TextField();
        productPriceField.setPromptText("Enter Product Selling Price");

        TextField productQuantityField = new TextField();
        productQuantityField.setPromptText("Enter Product Quantity");

        TextField productSupplierField = new TextField();
        productSupplierField.setPromptText("Enter the Product's Supplier");

        TextField productCategoryField = new TextField();
        productCategoryField.setPromptText("Enter Product Sector");

        Button submitButton = new Button("Add Item");
        submitButton.setOnAction(e -> {
            String productName = productNameField.getText();
            String productDescription = productDescriptionField.getText();
            String productPurchasePrice = productPurchasePriceField.getText();
            String productPriceText = productPriceField.getText();
            String productQuantityText = productQuantityField.getText();
            String productCategoryText = productCategoryField.getText();
            String supplierText = productSupplierField.getText();

            Sector selectedSector = null;
            Supplier selectedSupplier = null;
            double price1 = 0.0;
            double purchasePrice1 = 0.0;
            int quantity1 = 0;

            for (Sector sector : manager.getSectors()) {
                if (sector.getSectorName().equals(productCategoryText)) {
                    selectedSector = sector;
                    break;
                }
            }
            if (selectedSector == null) {
                showErrorAlert("Not an existing sector");
                return;
            }

            for (Supplier supplier : manager.getSuppliers()) {
                if (supplier.getName().equals(supplierText)) {
                    selectedSupplier = supplier;
                    break;
                }
            }
            if (selectedSupplier == null) {
                showErrorAlert("Supplier not found");
                return;
            }
            if (productName.isEmpty() || productDescription.isEmpty() || productPurchasePrice.isEmpty() || productPriceText.isEmpty() || productQuantityText.isEmpty()) {
                showErrorAlert("Please enter all fields: product name, description, purchase price, price, and quantity.");
                return;
            }
            try {
                purchasePrice1 = Double.parseDouble(productPurchasePrice);
                price1 = Double.parseDouble(productPriceText);
                quantity1 = Integer.parseInt(productQuantityText);
            } catch (NumberFormatException _) {
                showErrorAlert("Price and Quantity must be valid numbers.");
                return;
            }
  
            Item newProduct = new Item(productName, purchasePrice1, price1, selectedSupplier, quantity1);
            manager.addItem(newProduct);

            for(Cashier cashier:manager.getCashiers()) {
    			cashier.addItem(newProduct);
    			cashier.update();
          		}
            view.updateProductListView("");
            addProductStage.close();
        });
    manager.update();
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            new Label("Product Name"), productNameField,
            new Label("Product Description"), productDescriptionField,
            new Label("Purchase Price"), productPurchasePriceField,
            new Label("Selling Price"), productPriceField,
            new Label("Quantity"), productQuantityField,
            new Label("Supplier"), productSupplierField,
            new Label("Sector"), productCategoryField,
            submitButton
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 500);
        addProductStage.setScene(scene);
        addProductStage.show();

}

    public void handleViewReports(Cashier cas) {
    	Stage popupStage = new Stage();
    	cas.update();

        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Cashier Performance Report");
        Label header = new Label("Cashier Performance Report");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        GridPane contentGrid = new GridPane();
        contentGrid.setHgap(10);
        contentGrid.setVgap(10);
        contentGrid.setPadding(new Insets(10));
        contentGrid.setAlignment(Pos.CENTER_LEFT);
    double sum1=0;
       int sum2=0;
    for(Bill bill:cas.getbills()) {
	sum1+=bill.getTotalAmountOfBill();
	sum2++;
}
    
    String formattedSum1 = String.format("%.2f", sum1);
        contentGrid.add(new Label("Cashier Name: "+" Surname: "), 0, 0);
        contentGrid.add(new Label(cas.getName()+" "+cas.getSurname()), 1, 0);
        contentGrid.add(new Label("Total Revenue:"), 0, 1);
        contentGrid.add(new Label(" "+formattedSum1), 1, 1);


        contentGrid.add(new Label("Total Bills:"), 0, 2);
        contentGrid.add(new Label(" "+sum2), 1, 2);

        contentGrid.add(new Label("Accuracy Percentage:"), 0, 3);
        if(sum1==0)
            sum1=1;

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
    

    public boolean handleRemoveProduct(String selectedProduct) {
        
       
            String productName = selectedProduct.split(" - ")[0];
            if(manager.removeItem(productName)) {
            for(Cashier cashier:manager.getCashiers()) {
            	Item i=findItemByName(productName);
            	cashier.getItem().remove(i);
            }
            return true;
            }
            return false;
     
    }
    public void handleRemoveProductFromSector() {
        String selectedProduct = view.getProductListView().getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String productName = selectedProduct.split(" - ")[0];
        }else{
            view.showErrorAlert("Please select a product to remove.");
        }
    }

    public void handleViewSectors() {
        ListView<String> sectorListView = new ListView<>();

        
        for (Sector sector : manager.getSectors()) {
            StringBuilder sectorInfo = new StringBuilder();
            sectorInfo.append("Sector Name: ").append(sector.getSectorName()).append("\n")
                      .append("Number of Items: ").append(sector.getNrOfItems()).append("\n");
            sectorListView.getItems().add(sectorInfo.toString());
        }

        Stage sectorStage = new Stage();
        sectorStage.setTitle("Sectors Information");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().add(sectorListView);

        Scene scene = new Scene(layout, 300, 400);
        sectorStage.setScene(scene);
        sectorStage.show();
    }


    public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    
    public Item findItemByName(String itemName) {
    	for(Item item:manager.getItems()) {
    		if(item.getItemName().equals(itemName))
    			return item;
    	}
		return null;
    }
}
