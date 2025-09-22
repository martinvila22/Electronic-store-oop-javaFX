package model;
import java.io.Serializable;


import java.time.LocalDate;

public abstract class Employee extends User implements Serializable {
 /**
	 * 
	 */
	private static final long serialVersionUID = -7658200229912264656L;
String employeeId;
 String role;
 double salary;


public Employee(String name,String surname,LocalDate dateOfBirth,int phoneNr,
		String address,String username,String password,
		String employeeId,String role,double salary) {
	super(name,surname,dateOfBirth,phoneNr,address,username,password);
	this.employeeId=employeeId;
	this.role=role;
	this.salary=salary;
	
}
public Employee(String name,String surname,String username,String password,String employeeId,
		String role,double salary) {
	super(name,surname,username,password);
	this.employeeId=employeeId;
	this.role=role;
	this.salary=salary;
	
}

 public void setEmployeeId(String employeeId) {this.employeeId=employeeId;}
 public void setRole(String role) {this.role=role;}
public void setSalary(double salary) {this.salary=salary;}

public String getEmployeeId() {return employeeId;}
public String getRole() {return role;}
public double getSalary() {return salary;}


public abstract String EmployeeTask();

@Override
public String toString() {
	return super.toString()+" employeeId: "+employeeId+" role:"+role+
			" salary:"+salary;
}
		
}



