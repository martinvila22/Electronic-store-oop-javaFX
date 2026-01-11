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
import model.*;
import view.*;

class IntegrationTestingLoginPageController {
	private ShowEmployeeLoginController loginDash;
	private ShowEmployeeLoginView loginView;
	private Manager manager;
	private ArrayList<Item> items;
	private Cashier cashier;
	private Administrator admin;

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
                  cashier=new Cashier("Emanuel","Adebayor",LocalDate.of(2000,7,7),+355677777,"Unkwnown","Ckortez",
                          "emanuel","Cs","cashier",505,sector,items);
                cashiers.add(cashier);

                ArrayList<Sector> sectors = new ArrayList<>();
                sectors.add(sector);
                ArrayList<Manager> managers = new ArrayList<>();
                 manager=new Manager("John","Doe",LocalDate.of(1985,5,10),123456789,"Addr1","m1","manager1","pass1","Manager",1200,sectors,cashiers,suplier1,items);
                 managers.add(manager);
                  admin = new Administrator(
                         "Admin","User",LocalDate.of(1990,1,1),123456789,"Addr",
                         "admin1","pass","admin1","Admin",15000.0,
                         managers,cashiers,sectors
                     );
                
                loginDash=new ShowEmployeeLoginController();
            } catch (Exception e) {
                e.printStackTrace(); 
            }  finally {
                latch.countDown();
            }
        });

        latch.await(); 
    }
	
	@Test
	void testControllerManager() {
		   User result=loginDash.handleLogin(manager.getManagerId(),manager.getPassword(),'M');
			Manager testManager= (Manager) result;
			assertEquals(manager.getManagerId(),testManager.getManagerId());//failed test the deserialization fails
			//other tests for checkManager are not conducted since they will always fail due to deserialization problems
			 
		
	}
	
	@Test
	void testControllerAdministrator() {
		User result=loginDash.handleLogin(admin.getEmployeeId(), admin.getPassword(),'A');
		Administrator testAdmin=(Administrator) result;
		assertEquals(admin.getAdminiId(),testAdmin.getAdminiId()); //failed test due to deserialization of files
		//other tests for checkAdministrator are not conducted since they will always fail due to deserialization problems
	}
	
	@Test void testControllerCashier() {
		User result=loginDash.handleLogin(cashier.getEmployeeId(),cashier.getPassword(),'C');
		Cashier testCashier= (Cashier) result;
		assertEquals(cashier.getCashierId(),testCashier.getCashierId()); //success
		
		 result=loginDash.handleLogin(cashier.getEmployeeId(),cashier.getPassword(),'l');
		 
		assertEquals(null,result); //success
		
		 result=loginDash.handleLogin(cashier.getEmployeeId(),"wrongpassword",'C');
		 
		assertEquals(null,result); //success
		
	    result=loginDash.handleLogin("WrongemployeeID",cashier.getPassword(),'C');
		 
		assertEquals(null,result); //success
			
	}

}
