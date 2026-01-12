package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.AdministratorDashboardController;
import control.CashierDashboardController;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;

import view.*;

class IntegrationTestingCashierDashboardController {
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
	void testControllerCashierItem1() {
		List<Item> result=cashierDash.loadItems();
		
		assertEquals(result,items); //success
	}

	@Test
	void testControllerCashierItem2() {
		Item result=cashierDash.findItemByName(items.get(0).getItemName());
		
		assertEquals(result,items.get(0));//success
		
		result=cashierDash.findItemByName("none");
		
		assertEquals(result,null);//success
	}
	
	 @Test
	 void testControllerCashierItem3() {
		 boolean result=cashierDash.isProductEmpty(items.get(0).getItemName());
		 
		 assertEquals(false,result);//success
		 
		 items.get(0).setQuantity(0);
		 
		 result=cashierDash.isProductEmpty(items.get(0).getItemName());
		 
		 assertEquals(true,result);//success
		 
		 result=cashierDash.isProductEmpty("dishwasher");
		 
		 assertEquals(false,result);//success
	 }
}
