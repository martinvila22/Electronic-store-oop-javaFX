package model;
import java.util.ArrayList;




import java.util.Date;
import java.util.Random;
import java.io.*;

public class Bill implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9135217586435213532L;
	
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();
    private Date created;
    private double totalAmountOfBill;
    private Cashier createdBy;
    Random random=new Random();
    private int billNumber=random.nextInt(123134);
    private String filepath="src/dao/Bill"+billNumber+".txt";
    private File file=new File(filepath);
  

    public Bill(ArrayList<Item> item, ArrayList<Integer> quantity,Cashier cashier) {
    	for(int i=0;i<item.size();i++) {
    	if(item.get(i).getIsItemOutOfStock()==true) {	
    		System.out.println("Item is out of Stock ");
    	}
    	}
    	
        this.items.addAll(item);
        this.quantities.addAll(quantity); 
        this.totalAmountOfBill = calcTotal(item,quantity); 
        this.created = new Date();
        this.createdBy=cashier;
        
       
        
        
        try(PrintWriter writer=new PrintWriter(file)){
        	
        	writer.println(this.billNumber);
        	writer.println("Cashier: "+createdBy.getName()+createdBy.getSurname());
        	for(int i=0;i<item.size();i++) {
        		writer.println();
        		writer.print(" Item name: "+items.get(i).getItemName());
        		writer.print(" Price:  "+items.get(i).getSellingPrice());
        		writer.print(" Quantity:  "+quantities.get(i));
        	
        	
        }
        	writer.println();
        	writer.println("Total price: "+calcTotal(items,quantities));
        }
        catch(IOException e) {
        	System.out.println("Cannot write to file");
        }

    }
        
        
    
     
	

	public File getFile() {
		return file;
	}

	public int getBillNumber() {
        return billNumber;
    }

    public Date getCreated() {
        return created;
    }

    public double getTotalAmountOfBill() {
            return totalAmountOfBill;
        }
    public Cashier getCreatedBy() {
            return createdBy;
        }


private double calcTotal(ArrayList<Item> item, ArrayList<Integer> quantity) {
	       double total = 0;
            for (int i = 0; i < item.size(); i++) {
                total+= item.get(i).getSellingPrice() * quantity.get(i);
            }
            return total;
        }

    public void addItem(Item item, int quantity){
            if (item != null && quantity > 0) {
                items.add(item);
                quantities.add(quantity);
                totalAmountOfBill+=item.getSellingPrice()*quantity;
            }
        }

    public boolean isProductDiscounted(String productName) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getItemName().equals(productName)) {
                return item.getIsItemDiscounted();
            }
        }
        return false;
    }

    public double applyDiscount(double discountRate) {
        if (discountRate < 0 || discountRate > 100) {
            throw new IllegalArgumentException("nuk mundet ");
        }
        return totalAmountOfBill - (totalAmountOfBill * (discountRate / 100));
    }

    public void assignCashier(Cashier cashier) {
        this.createdBy = cashier;
       
    }

    @Override
    public String toString() {
        return String.format("bill created  by=%s, totalAmount=%.2f, created=%s",
                createdBy  , totalAmountOfBill, created);
    }
} 