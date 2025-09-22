package model;

import java.io.Serializable;

import java.time.LocalDate;

public abstract class User implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = -5872770277479208501L;
   private String name;
   

private String surname;
   private LocalDate date0fBirth;
   private int phoneNr;
   private String username;
   private String password;
   private String address;
  
  
  public User(String name,String surname,LocalDate dateOfBirth,
		  int phoneNr,String address,String username,String password) {
	  this.name=name;
	  this.surname=surname;
	  this.date0fBirth=dateOfBirth;
	  this.phoneNr=phoneNr;
	  this.address=address;
	  this.username=username;
	  this.password=password;
	  this.address=address;
			  }
  public User(String name,String surname,String username,String password) {
	  this(name,surname,LocalDate.of(1980,1,1),0000,"Unkwnown",username,password);
  }
 
  public void setBirthDate(LocalDate date) {this.date0fBirth=date;}
  public void setphoneNr(int phoneNr) {this.phoneNr=phoneNr;}
  public void setAddress(String address) {this.address=address;}
  public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
  
  public LocalDate getBirthDate() {return date0fBirth;}
  public int getPhoneNr() {return phoneNr;}
  public  String getAddress() {return address;}
  public String getUsername() {return username;}
  public String getPassword() {return password;}
  public String getSurname() {return this.surname;}
  
  
  public abstract boolean logIn(String username,String password);
  
  public boolean changePass(String oldP,String newP,String newPc) {
	  if(oldP==this.password) {
		  if(newP==newPc) {
			  this.password=newP;
			  return true;
	  }
	  }
	  return false;
  }
  public void changeSurname(String newSurname) {
	  this.surname=newSurname;
  }
  
  @Override
  public String toString() {
	  return name+" "+surname+"phone nr: "+phoneNr+"Date Of Birth: "+this.date0fBirth;
  }
  
 
}

