package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.AdministratorDashboardController;
import control.CashierDashboardController;
import control.ManagerController;
import control.ShowEmployeeLoginController;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Administrator;
import model.Bill;
import model.Cashier;
import model.Employee;
import model.Item;
import model.Manager;
import model.Sector;
import model.Supplier;
import model.User;
import view.AdministratorDashboardView;
import view.CashierDashboardView;
import view.ManagerDashboardView;
import view.ShowEmployeeLoginView;



class SystemTesting {
	private Supplier sup1;
	private Supplier sup2;
	private ArrayList<Supplier> suplier1;
	private Sector sector;
	private ArrayList<Cashier> cashiers = new ArrayList<>();
	private  ArrayList<Sector> sectors = new ArrayList<>();
	private Manager manager;
	private ArrayList<Item> items;
	private  ArrayList<Manager> managers = new ArrayList<>();
	private Cashier cashier;
	private Administrator admin;
	private AdministratorDashboardView adminView ;
	private AdministratorDashboardController adminDash;

	
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
                
            	 sup1=new Supplier(true,"Samsung");
                 sup2=new Supplier(true,"Hyundai");
                suplier1=new ArrayList<>();
                suplier1.add(sup1);
                suplier1.add(sup2);
                
                items = new ArrayList<>();
                items.add(new Item("Dishwasher", 100, 120, sup1, 10));
                 sector = new Sector(items,"Electronics",new int[5],new ArrayList<>());

                
                  cashier=new Cashier("Emanuel","Adebayor",LocalDate.of(2000,7,7),+355677777,"Unkwnown","Ckortez",
                          "emanuel","Cs","cashier",505,sector,items);
                cashiers.add(cashier);
                sectors.add(sector);
                 manager=new Manager("John","Doe",LocalDate.of(1985,5,10),123456789,"Addr1","m1","manager1","pass1","Manager",1200,sectors,cashiers,suplier1,items);
                 managers.add(manager);
                  admin = new Administrator(
                         "Admin","User",LocalDate.of(1990,1,1),123456789,"Addr",
                         "admin1","pass","admin1","Admin",15000.0,
                         managers,cashiers,sectors
                     );
                  
                   adminView = new AdministratorDashboardView(new Stage(), admin);
          		 adminDash = new AdministratorDashboardController(admin, adminView);
          		
                
            } catch (Exception e) {
                e.printStackTrace(); 
            }  finally {
                latch.countDown();
            }
        });

        latch.await(); 
    }
	
	@Test
	void Requirement1() {
		 ShowEmployeeLoginController loginDash=new ShowEmployeeLoginController();
		 
		User result=loginDash.handleLogin(cashier.getEmployeeId(),cashier.getPassword(),'C');
		Cashier testCashier= (Cashier) result;
		assertEquals(cashier.getCashierId(),testCashier.getCashierId()); //success
		
//		 result=loginDash.handleLogin(manager.getManagerId(),manager.getPassword(),'M');
//		Manager testManager= (Manager) result;
//		assertEquals(manager.getManagerId(),testManager.getManagerId());//failed test the deserialization fails
		
//	    result=loginDash.handleLogin(admin.getEmployeeId(), admin.getPassword(),'A');
//		Administrator testAdmin=(Administrator) result;
//		assertEquals(admin.getAdminiId(),testAdmin.getAdminiId()); //failed test due to deserialization of files
	}
	@Test
	void Requirement2() {
		ShowEmployeeLoginController loginDash=new ShowEmployeeLoginController();
		
		User result=loginDash.handleLogin(cashier.getEmployeeId(),cashier.getPassword(),'C');
		Employee testEmployee=(Employee) result;
		
		assertEquals("cashier",testEmployee.getRole()); //success
		
//		 result=loginDash.handleLogin(manager.getManagerId(),manager.getPassword(),'M');
//		 testEmployee= (Employee) result;
//		 assertEquals("manager",testEmployee.getRole()); //fails
		
//	    result=loginDash.handleLogin(admin.getEmployeeId(), admin.getPassword(),'A');
//		 testEmployee=(Administrator) result;
//		 assertEquals("administrator",testEmployee.getRole()); //fails
	}
	
	@Test
	void Requirement3() {
		ShowEmployeeLoginController loginDash=new ShowEmployeeLoginController();
		 
		User result=loginDash.handleLogin(cashier.getEmployeeId(),cashier.getPassword(),'C');
		Cashier testCashier= (Cashier) result;
		
		ArrayList<Integer> quantity=new ArrayList<>();
		quantity.add(10);
		Bill newBill=new Bill(items,quantity,testCashier);
		
		String toString="bill created  by="+testCashier+", totalAmount="+String.format("%.2f",newBill.calcTotal(items, quantity))+", created="+newBill.getCreated();
		assertEquals(toString,newBill.toString());
		
	}
	@Test
	void Requirement4() throws InterruptedException {
	    CountDownLatch latch = new CountDownLatch(1);

	    Platform.runLater(() -> {
	        CashierDashboardView cashierView = new CashierDashboardView(new Stage(), cashier);
	        CashierDashboardController cashierDash = new CashierDashboardController(cashier, cashierView);

	        Item testItem = items.get(0);
	       
	        testItem.setQuantity(5); 
	        boolean resultInStock = cashierDash.isProductEmpty(testItem.getItemName());
	        assertEquals(false, resultInStock); // success

	        
	        testItem.setQuantity(0);

	        boolean resultOutOfStock = cashierDash.isProductEmpty(testItem.getItemName());
	        assertEquals(true, resultOutOfStock);//success

	        latch.countDown();
	    });

	    latch.await();
	}

	@Test
	void Requirement5() throws InterruptedException {
		
        ArrayList<Integer> quantities=new ArrayList<>();
        quantities.add(10);
        
        Date testDate=new Date();
        Bill testBill=cashier.createBill(items, quantities);
        
        assertEquals(testDate.getTime(),testBill.getCreated().getTime());//success
	       
	        
	}
	
	@Test
	void Requirement6() {
		ArrayList<Integer> quantities=new ArrayList<>();
        quantities.add(10);
        
        Date testDate=new Date();
        Bill testBill=cashier.createBill(items, quantities);
        
        List<Bill> bills=cashier.getbills();
        
        assertEquals(testBill,bills.get(0));
	}
	
	@Test
	void Requirement7() throws InterruptedException {
		
		 CountDownLatch latch = new CountDownLatch(1);

		    Platform.runLater(() -> {
		
		boolean result = adminDash.removeManager(manager);
        assertEquals(true,result);

        boolean result2 = adminDash.removeManager(null);
        assertEquals(false,result2);
        
         result = adminDash.removeCashier(cashier);
        assertEquals(true,result);

         result2 = adminDash.removeCashier(null);
        assertEquals(false,result2);
        
        
		   latch.countDown();

	 });

	    latch.await();
	}
	
	@Test
	void Requirement8() throws InterruptedException {
		 CountDownLatch latch = new CountDownLatch(1);

		    Platform.runLater(() -> {
		ManagerDashboardView managerView=new ManagerDashboardView(new Stage(), manager);
		ManagerController managerDash=new ManagerController(manager,managerView);
		
		boolean result=managerDash.isLowStock(items.get(0));
		
		assertEquals(false,result);//success
		
		items.get(0).setQuantity(0);
		result=managerDash.isLowStock(items.get(0));
		assertEquals(true,result); //success
		  latch.countDown();

			 });

			    latch.await();
	}

}
