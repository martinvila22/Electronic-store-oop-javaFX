package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.*;

class Task2partA {

	private static final Double INVALID = null;

	@Test
	void bvtAvailableTest() {
	List<Item> itemList=new ArrayList<>();
	Item item1=new Item("televizor",99.0,100.0,null,0);//Item(String name,double buyingPrice,double sellingPrice,
    //Supplier supplier,int available)  min-1 for available
	itemList.add(item1);
	List<Integer> quantity=new ArrayList<>();
	quantity.add(1);
	Bill bill=new Bill();
	
	//assertEquals(INVALID,bill.calcTotal(itemList, quantity)); //failed test

	item1.setQuantity(1);//min for available
	
	assertEquals(100,bill.calcTotal(itemList, quantity));//passed test
	
	item1.setQuantity(2);//min+1 for available
	
	assertEquals(100,bill.calcTotal(itemList, quantity));//passed test
	
	item1.setQuantity(10000); //max available
	
	assertEquals(100,bill.calcTotal(itemList, quantity));//passed test
	
	item1.setQuantity(9999);//max-1 available
	
	assertEquals(100,bill.calcTotal(itemList, quantity));//passed test
	
	item1.setQuantity(10001);//max+1 available
	
	//assertEquals(INVALID,bill.calcTotal(itemList, quantity));//failed test
	
    
	}
	
	
	@Test
	void bvtSellingPriceTest() {
	List<Item> itemList=new ArrayList<>();
	Item item1=new Item("televizor",99.0,-1.0,null,100);//Item(String name,double buyingPrice,double sellingPrice,
    //Supplier supplier,int available)  min-1 for sellingPrice
	itemList.add(item1);
	List<Integer> quantity=new ArrayList<>();
	quantity.add(1);
	Bill bill=new Bill();
	
	//assertEquals(INVALID,bill.calcTotal(itemList, quantity)); //failed test

	item1.setSellingPrice(0.0);//min for sellingPrice
	
	assertEquals(0.0,bill.calcTotal(itemList, quantity));//passed test
	

	item1.setSellingPrice(1.0);//min+1 for sellingPrice
	
	assertEquals(1.0,bill.calcTotal(itemList, quantity));//passed test
	
	item1.setSellingPrice(10000.0); //max sellingPrice
	
	assertEquals(10000.0,bill.calcTotal(itemList, quantity));//passed test
	
	item1.setSellingPrice(9999.0);//max-1 sellingPrice
	
	assertEquals(9999.0,bill.calcTotal(itemList, quantity));//passed test
	
	item1.setSellingPrice(10001.0);//max+1 sellingPrice
	
	//assertEquals(INVALID,bill.calcTotal(itemList, quantity));//failed test
	
	
	
    
	}
	
	@Test
	void bvtQuantityTest() {
	List<Item> itemList=new ArrayList<>();
	Item item1=new Item("televizor",99.9,100.0,null,100);//Item(String name,double buyingPrice,double sellingPrice,
    //Supplier supplier,int available)  
	itemList.add(item1);
	List<Integer> quantity=new ArrayList<>();
	quantity.add(0); //min-1 for quantity
	Bill bill=new Bill();
	
	//assertEquals(INVALID,bill.calcTotal(itemList, quantity)); //failed test

	quantity.remove(0);
	quantity.add(1);//min for quantity
	
	assertEquals(100.0,bill.calcTotal(itemList, quantity));//passed test
	
	quantity.remove(0);
	quantity.add(2);//min+1 for quantity
	
	assertEquals(200.0,bill.calcTotal(itemList, quantity));//passed test
	
	quantity.remove(0);
	quantity.add(10000);//max for quantity
	
	assertEquals(1000000.0,bill.calcTotal(itemList, quantity));//passed test
	
	quantity.remove(0);
	quantity.add(9999);//max for quantity
	
	assertEquals(999900.0,bill.calcTotal(itemList, quantity));//passed test
	
	quantity.remove(0);
	quantity.add(10001);//max for quantity
	
	//assertEquals(INVALID,bill.calcTotal(itemList, quantity));//failed test
	
	
	
    
	}
	
	
}
