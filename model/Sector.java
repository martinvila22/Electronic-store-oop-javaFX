package model;
import java.io.*;
import java.util.ArrayList;

import dao.HeaderlessObjectOutputStream;

public class Sector implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -444374744967224169L;
	private ArrayList<Item> items=new ArrayList<>();
    private ArrayList<Supplier> suppliers=new ArrayList<>();
    private  transient static int nrOfItemTypesInSector;
    private ArrayList<Integer> quantities=new ArrayList<>();
    private  int nrOfItems;
    private String sectorName;
    
    private  transient File outputFile=new File("src/dao/sectors.dat");
    
 
    public Sector(ArrayList<Item> item, String sectorName, 
    		int[] quantity,ArrayList<Supplier> supplier) {
        
        items.addAll(item);
       for(int j=0;j<items.size();j++) {
    	   nrOfItems+=items.get(j).getQuantity();
    	   
       }
       suppliers.addAll(supplier);
       for(int i=0;i<quantity.length;i++) {
    	   quantities.add(quantity[i]);
       }
       nrOfItemTypesInSector=items.size();
        this.sectorName = sectorName;
        
        writeToFile();
    }

    private void writeToFile() {
   	 try(FileOutputStream outputStream=new FileOutputStream(outputFile, true))
        {
        ObjectOutputStream writer;

        if(outputFile.length()>0)
        writer=new HeaderlessObjectOutputStream(outputStream);
        else
        writer=new ObjectOutputStream(outputStream);

        
        writer.writeObject(this);
        
       
        }
        catch(IOException ex)
        {
        System.out.println("Error during writing item"+ ex.getMessage());
        }

   }
    
    public boolean isSectorEmptyOrNot() {
        if (nrOfItems == 0) {
            System.out.println("Sector is empty.");
            return false;
        } else {
            System.out.println("Sector is not empty.");
            return true;
        }
    }
    
    public boolean isSectorEmptyOfOneItem(Item item) {
    	for(int i=0;i<items.size();i++) {
    		if(items.get(i)==item) {
    			if(quantities.get(i)==0)
    				return true;
    		}
    	}
    	return false;
    }

    public Item isSectorEmptyOfAnyItem() {
    	for(int i=0;i<items.size();i++) {
    		if(quantities.get(i)==0)
    			return items.get(i);
    	}
    	return null;
    }
    
    public void addNewItem(Item newItem) {
        if (newItem != null) {
            this.items.add(newItem);
            this.nrOfItems+=newItem.getQuantity();
            System.out.println("Item added successfully: " + newItem.getItemName());
        } else {
            System.out.println("Invalid item. Cannot add to sector.");
        }
        update(this);
    }

    public void updateSectorItems(int quantity) {
    	this.nrOfItems+=quantity;
    	update(this);
    	
    }
    public void deleteAnItem(Item item) {
       items.remove(item);
       update(this);
    }
    
    public ArrayList<Item> getItems() {
        return items;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
        update(this);
    }

    
    
    
    @Override
    public String toString() {
    	for(int i=0;i<items.size();i++) {
    		System.out.println("Item: "+items.get(i)+"quantity: "+quantities.get(i));
    	}
    	
    	return "Sector name: "+this.sectorName+"Number of items "+this.nrOfItems+"number of item types in sector"+this.nrOfItemTypesInSector;
    }
   
    
    public void update( Sector sector)  {
		{
			 try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(this.outputFile));
					 ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.outputFile)) ) {
		            
		            while (true) {
		                try {
		                   Sector sec;
							
								sec= (Sector) reader.readObject();
		                   if(!sector.equals(sec)) {
		                       outputStream.writeObject(sec);
		                   }
		                   else {
		                	   outputStream.writeObject(sector);
		                   }
		                } catch (IOException | ClassNotFoundException ex) {
		                    System.out.println("Error reading the file: " + ex.getMessage());
		                    ex.printStackTrace();  
		                }
		                 catch (ClassCastException ex) {
		                   
		                    ex.printStackTrace();  
		                }
	
		            
		            }
			 } catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
								e.printStackTrace();
			}
		}
	}
}