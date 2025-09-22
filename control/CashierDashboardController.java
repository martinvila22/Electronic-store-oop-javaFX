package control;


import model.*;
import view.CashierDashboardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.io.*;
import java.util.ArrayList;


public class CashierDashboardController {

    private Cashier cashier;
    private CashierDashboardView view;
    private ArrayList<String> cartItems = new ArrayList<>();
    private ArrayList<Item> allItems = new ArrayList<>();
    private ArrayList<Bill> bills=new ArrayList<>();
    private static double totalAmount;
    private Label totalAmountLabel;
    boolean empty=false;

    public CashierDashboardController(Cashier cashier, CashierDashboardView view) {
        this.cashier = cashier;
        this.view = view;
        loadItems();
        setUpButtonActions();
    }

    private void setUpButtonActions() {
        view.getAddToCartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openAddToCartWindow();
            }
        });

        view.getViewCartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openViewCartWindow();
            }
        });

        view.getStartShiftButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openStartShiftWindow();
            }
        });
        

        view.getEndShiftButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openEndShiftWindow();
            }
        });

       view.getGenerateBillsButton().setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event)
       {
    	   
    	   openGenerateBillsWindow();
       }
       
       });
    }

    private void openAddToCartWindow() {
        Stage addToCartStage = new Stage();
        VBox addToCartLayout = new VBox(10);
        TextField searchBar = new TextField();
        
        searchBar.setPromptText("Search Items...");
        searchBar.setOnKeyReleased(this::filterItems);
        ListView<String> itemsListView = new ListView<>();
        itemsListView.getItems().addAll(getItemNamesFromList());
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(e -> {
            String selectedItem = itemsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
            	String[] parts=selectedItem.split(" - ");
                String itemName = parts[0];
                empty=isProductEmpty(itemName);
                if(empty==true) {
                	showAlert("Not possible" ,"Item is empty");
                  openAddToCartWindow();	
                }else {
                cartItems.add(selectedItem);
                totalAmount+=extractPriceFromProduct(itemName);
                
            }
            }
        });
        addToCartLayout.getChildren().addAll(searchBar, itemsListView, addToCartButton);
        Scene addToCartScene = new Scene(addToCartLayout, 400, 300);
        addToCartStage.setTitle("Add to Cart");
        addToCartStage.setScene(addToCartScene);
        addToCartStage.show();
    }

    private void filterItems(KeyEvent event) {
        String query = ((TextField) event.getSource()).getText().toLowerCase();
        ListView<String> itemsListView = view.getItemsListView();
        ArrayList<String> filteredItems = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getItemName().toLowerCase().contains(query)) {
                filteredItems.add(item.getItemName());
            }
        }
        itemsListView.setItems(FXCollections.observableArrayList(filteredItems));
    }

    private void openViewCartWindow() {
        Stage viewCartStage = new Stage();
        VBox viewCartLayout = new VBox(10);
        Label viewCartLabel = new Label("Cart Items:");
        totalAmountLabel=createLabel("Total amount: "+CashierDashboardController.totalAmount ,14, "Bold");
        ObservableList<String> observableCartItems = FXCollections.observableArrayList(cartItems);
        ListView<String> cartListView = new ListView<>(observableCartItems);
      
       
        view.getRemoveItemButton().setOnAction(new EventHandler<ActionEvent>()
        
        {
        	@Override
        	public void handle(ActionEvent event) {
        		
            String selectedItem = cartListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
            	cartItems.remove(selectedItem);
                cartListView.getItems().remove(selectedItem);
                totalAmount-=extractPriceFromProduct(selectedItem.split(" - ")[0]);
                totalAmountLabel.setText("Total amount: "+CashierDashboardController.totalAmount); 
                System.out.println("Removed " + selectedItem + " from cart");
                
            }
            else {
            	showAlert("Not selected","Not selected");
            }
        	}
        });
        view.getCreateBillsButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           
                openGenerateBillsWindow(cartListView);
            }
        });
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(view.getRemoveItemButton(),view.getCreateBillsButton());
        viewCartLayout.getChildren().addAll(viewCartLabel, cartListView, buttonLayout,totalAmountLabel);
        Scene viewCartScene = new Scene(viewCartLayout, 400, 300);
        viewCartStage.setTitle("View Cart");
        viewCartStage.setScene(viewCartScene);
        viewCartStage.show();
    }

    private void openStartShiftWindow() {
        Stage startShiftStage = new Stage();
        StackPane startShiftLayout = new StackPane();
        Label startShiftLabel = new Label("Start Shift");
        startShiftLayout.getChildren().add(startShiftLabel);
        Scene startShiftScene = new Scene(startShiftLayout, 300, 200);
        startShiftStage.setTitle("Start Shift");
        startShiftStage.setScene(startShiftScene);
        startShiftStage.show();
        cashier.startShift();
        System.out.println("Shift Started");
    }

    private void openEndShiftWindow() {
        Stage endShiftStage = new Stage();
        StackPane endShiftLayout = new StackPane();
        Label endShiftLabel = new Label("End Shift");
        endShiftLayout.getChildren().add(endShiftLabel);
        Scene endShiftScene = new Scene(endShiftLayout, 300, 200);
        endShiftStage.setTitle("End Shift");
        endShiftStage.setScene(endShiftScene);
        endShiftStage.show();
        cashier.endShift();
        System.out.println("Shift Ended");
    }

    private void openGenerateBillsWindow(ListView<String> cartList) {
    	Stage billStage = new Stage();
        VBox billLayout = new VBox(15);
        billLayout.setStyle("-fx-background-color: white; -fx-padding: 20;");
        Label billLabel = new Label("Bill Details");
        ListView<String> billList = new ListView<>();
        ArrayList<Item> selectedItems = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        for (String Fullitem:cartList.getItems()) {
        	String[] parts=Fullitem.split(" - ");
            String itemName = parts[0];
            Item item = findItemByName(itemName);
            selectedItems.add(item);
            quantities.add(1);
            billList.getItems().add(itemName+"-"+item.getSellingPrice());
        }
    
        Bill bill=cashier.createBill(selectedItems, quantities);
        bills.add(bill);
        Label totalLabel = createLabel("Total: $" + bill.getTotalAmountOfBill(), 16, "normal");
        for(Item item:selectedItems) {
        	cashier.updateStock(item);
        	
        }
        
        Button closeBillButton = view.createButton("Close",14);
        closeBillButton.setOnAction(closeEvent -> billStage.close());
        billLayout.getChildren().addAll(billLabel, billList, totalLabel, closeBillButton);

        Scene billScene = new Scene(billLayout, 400, 300);
        billStage.setScene(billScene);
        billStage.setTitle("Create Bill");
        billStage.show();
        totalAmount=0;
        totalAmountLabel.setText("Total amount: "+totalAmount);
        cartList.getItems().clear();
        cartItems.clear();
        
    }
    
    

    private void openGenerateBillsWindow() {
    
    	 Stage generateBillStage = new Stage();
         VBox billLayout = new VBox(15);
         billLayout.setStyle("-fx-background-color: white; -fx-padding: 20;");
         Label billLabel = createLabel("Generated Bills:", 15, "bold");

         ListView<String> billList = new ListView<>();
         for (int i=cashier.getbills().size()-1;i>= 0;i--) {
         	billList.getItems().add("Bill"+(i+1)+" - "+cashier.getbills().get(i).getCreated());
             File file =cashier.getbills().get(i).getFile();
             if (file.exists() && file.canRead()) {
                 try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                     String line;
                     while ((line=reader.readLine()) != null) {
                     	
                         if (line.startsWith(" Item name:")) { 
                             billList.getItems().add(line);
                         }
                     }
                 } catch (IOException x) {
                     System.out.println("Error reading file: " + x.getMessage());
                     x.printStackTrace();
                 }
             } else {
                 System.out.println("File does not exist or cannot be read: " + file.getAbsolutePath());
             }
             
             
         };
         Label totalBills = new Label("Total Bills created: " + cashier.getBillsCount());
         billLayout.getChildren().addAll(billLabel, billList, totalBills);

         Scene billScene = new Scene(billLayout, 600, 700);
         generateBillStage.setScene(billScene);
         generateBillStage.setTitle("All Generated Bills");
         generateBillStage.show();
         
         
    }
    
    
    
    
    private void loadItems() {
    	
        for(Item item:cashier.getItem()) {
        	allItems.add(item);
        }
    }
    
    private Item findItemByName(String name) {
        for (int i = 0; i < this.allItems.size(); i++) {
            if (allItems.get(i).getItemName().equals(name)) {
                return allItems.get(i);
            }
        }
        return null;
    }

    private ArrayList<String> getItemNamesFromList() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : allItems) {
            itemNames.add(item.getItemName()+" - "+item.getSellingPrice());
        }
        return itemNames;
    }
    private Label createLabel(String text, int fontSize, String fontWeight) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-weight: " + fontWeight + "; -fx-text-fill: black;");
        return label;
    }
    
    private double extractPriceFromProduct(String product) {
    	for(int i=0;i<allItems.size();i++) {
    		if(allItems.get(i).getItemName().equals(product))
    			return allItems.get(i).getSellingPrice();
    	}
    	
    		return 0.0;
       
    }
    
    private boolean isProductEmpty(String product) {
    	for(int i=0;i<allItems.size();i++) {
    		if(allItems.get(i).getItemName().equals(product)) {
    			return allItems.get(i).getIsItemOutOfStock();
    		}
    	}
    	return false;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
