package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON_INFO")
public class Person {

    @Id
    @Column(name = "Person_ID")
    private int id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "SALARY")
    private String salary;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "GENDER")
    private String gender;
    
    @Column(name = "CONTACT")
    private String contactNo;
    
    @Column(name = "COUNTRY")
    private String country;
    
    @Column(name = "DOB")
    private String dob;
    
    @Column(name = "COLLEGE")
    private String college;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}
    
    


}
