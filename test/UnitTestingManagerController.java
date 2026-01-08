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
import javafx.stage.Stage;
import model.*;
import view.*;

class UnitTestingManagerController {

	private ManagerController managerDash;
	private ManagerDashboardView managerView;
	private Manager manager;
	private ArrayList<Item> items;
	private Cashier cashier;
	
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
                
            	Supplier sup1=new Supplier(true,"Samsung");
                Supplier sup2=new Supplier(true,"Hyundai");
                ArrayList<Supplier> suplier1=new ArrayList<>();
                suplier1.add(sup1);
                suplier1.add(sup2);
                
                items = new ArrayList<>();
                items.add(new Item("Dishwasher", 100, 120, sup1, 10));
                Sector sector = new Sector(items,"Electronics",new int[5],new ArrayList<>());

                ArrayList<Cashier> cashiers = new ArrayList<>();
                  cashier=new Cashier("Liam","Kola",LocalDate.of(1999,4,12),111222333,"Addr1","c1","pass1","e1","Cashier",900,sector,items);
                cashiers.add(cashier);

                ArrayList<Sector> sectors = new ArrayList<>();
                sectors.add(sector);
                
                Manager manager=new Manager("John","Doe",LocalDate.of(1985,5,10),123456789,"Addr1","m1","manager1","pass1","Manager",1200,sectors,cashiers,suplier1,items);


                managerView=new ManagerDashboardView(new Stage(), manager);
                managerDash=new ManagerController(manager,managerView);
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
        Item result = managerDash.findItemByName("Dishwasher");
        assertEquals("Dishwasher", result.getItemName());//success
        
       result=managerDash.findItemByName("TV");
       assertNull(result);//success
    }
	
	@Test 
	void testHandleRemoveCashier() {
		boolean result=managerDash.handleRemoveCashier(cashier.getCashierId());
		
		assertEquals(true,result);//success
		
		result=managerDash.handleRemoveCashier("d123");
		
		assertEquals(false,result);//success
	}
	
	@Test 
	void testHandleRemoveProduct() {
		boolean result=managerDash.handleRemoveProduct(items.get(0).getItemName());
		
		assertEquals(true,result);//success
		
		result=managerDash.handleRemoveProduct("TV");
		
		assertEquals(false,result);//success
	}

}
