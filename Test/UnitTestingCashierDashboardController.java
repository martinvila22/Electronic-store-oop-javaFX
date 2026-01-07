package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Administrator;
import model.Cashier;
import model.Item;
import model.Manager;
import model.Sector;
import view.*;

class UnitTestingCashierDashboardController {

	private CashierDashboardController cashierDash;
	private CashierDashboardView cashierView;
	private Cashier cashier;
	private ArrayList<Item> items;
	@BeforeAll
    static void initJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // JavaFX already started
        }
    }
	
	@BeforeEach
    void setUp() throws Exception {

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                ArrayList<Manager> managers = new ArrayList<>();
                Manager manager=new Manager("John","Doe",LocalDate.of(1985,5,10),123456789,"Addr1","m1","pass1","Manager",1200,"e1");
                managers.add(manager);

                items = new ArrayList<>();
                items.add(new Item("Refrigarator", 100, 120, null, 10));
                Sector sector = new Sector(items,"Electronics",new int[5],new ArrayList<>());

                ArrayList<Cashier> cashiers = new ArrayList<>();
                 cashier=new Cashier("Liam","Kola",LocalDate.of(1999,4,12),111222333,"Addr1","c1","pass1","e1","Cashier",900,sector,items);
                cashiers.add(cashier);

                ArrayList<Sector> sectors = new ArrayList<>();
                sectors.add(sector);


                cashierView=new CashierDashboardView(new Stage(), cashier);
                cashierDash=new CashierDashboardController(cashier,cashierView);
            } catch (Exception e) {
                e.printStackTrace(); 
            }  finally {
                latch.countDown();
            }
        });

        latch.await(); 
    }
	
	
	 @Test
	    void testFindItemByName() {
	        Item result = cashierDash.findItemByName("Refrigarator");
	        assertEquals("Refrigarator", result.getItemName());//success
	        
	       result=cashierDash.findItemByName("AirFyer");
	       assertNull(result);//success
	    }
	 
	 @Test
	 void testgetItemsNamesFromList() {
		 ArrayList<String> names=new ArrayList<>();
		 for(int i=0;i<items.size();i++) {
			 names.add(items.get(i).getItemName()+" - "+ items.get(i).getSellingPrice());
		 }
		 
		 assertEquals(names,cashierDash.getItemNamesFromList());
		 
	 }

	 @Test
	 void testCreateLabel() throws InterruptedException {
	     CountDownLatch latch = new CountDownLatch(1);

	     Platform.runLater(() -> {
	         Label label = cashierDash.createLabel("Hello", 14, "bold");

	         assertEquals("Hello", label.getText());
	         assertTrue(label.getStyle().contains("-fx-font-size: 14px"));
	         assertTrue(label.getStyle().contains("-fx-font-weight: bold"));
	         assertTrue(label.getStyle().contains("-fx-text-fill: black"));

	         latch.countDown();
	     });

	     latch.await();
	 }
	 
	 @Test
	 void testExtractPriceFromProduct() {
		 double result=cashierDash.extractPriceFromProduct(items.get(0).getItemName());
		 
		 assertEquals(items.get(0).getSellingPrice(),result);//success
		 
		 result=cashierDash.extractPriceFromProduct("tv");
		 
		 assertEquals(0.0,result);//success
	 }
	 
	 @Test
	 void isProductEmpty() {
		 boolean result=cashierDash.isProductEmpty(items.get(0).getItemName());
		 
		 assertEquals(false,result);//success
		 
		 items.get(0).setQuantity(0);
		 
		 result=cashierDash.isProductEmpty(items.get(0).getItemName());
		 
		 assertEquals(true,result);//success
		 
		 result=cashierDash.isProductEmpty("tv");
		 
		 assertEquals(false,result);//success
	 }

}
