package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.AdministratorDashboardController;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Administrator;
import model.Cashier;
import model.Item;
import model.Manager;
import model.Sector;
import view.AdministratorDashboardView;

class IntegrationTestingAdministratorDashboardController {
	
	private AdministratorDashboardController adminDash;
    private AdministratorDashboardView adminView;
    private Cashier cashier;
    private Manager manager;
    private Sector sector;
    
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
                manager=new Manager("John","Doe",LocalDate.of(1985,5,10),123456789,"Addr1","m1","pass1","Manager",1200,"e1");
                managers.add(manager);

                ArrayList<Item> items = new ArrayList<>();
                items.add(new Item("TV", 100, 120, null, 10));
                 sector = new Sector(items,"Electronics",new int[5],new ArrayList<>());

                ArrayList<Cashier> cashiers = new ArrayList<>();
                cashier=new Cashier("Liam","Kola",LocalDate.of(1999,4,12),111222333,"Addr1","c1","pass1","e1","Cashier",900,sector,items);
                cashiers.add(cashier);

                ArrayList<Sector> sectors = new ArrayList<>();
                sectors.add(sector);

                Administrator admin = new Administrator(
                    "Admin","User",LocalDate.of(1990,1,1),123456789,"Addr",
                    "admin1","pass","admin1","Admin",15000.0,
                    managers,cashiers,sectors
                );
                admin.addItem(items.get(0));

                adminView = new AdministratorDashboardView(new Stage(), admin);
                adminDash = new AdministratorDashboardController(admin, adminView);

            } finally {
                latch.countDown();
            }
        });

        latch.await(); 
    }
	@Test
	void testControllerAdministratorManager() {
		 boolean result = adminDash.removeManager(manager);
         assertEquals(true,result);

         boolean result2 = adminDash.removeManager(null);
         assertEquals(false,result2);
	}
	
	@Test
	void testControllerAdministratorCashier() {
		 boolean result = adminDash.removeCashier(cashier);
         assertEquals(true,result);

         boolean result2 = adminDash.removeCashier(null);
         assertEquals(false,result2);
	}
	
	@Test
	void testControllerAdministratorSector() {
		Sector testSector=adminDash.checkSector(sector.getSectorName());
		
		assertEquals(sector,testSector);//fails
		
		assertEquals(null,testSector); //success
	}

}
