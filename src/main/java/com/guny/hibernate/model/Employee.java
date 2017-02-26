package com.guny.hibernate.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name="EMPLOYEE")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="employee")
public class Employee {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="emp_id") private long id;
	@Basic(fetch=FetchType.EAGER)
	@Column(name="emp_name") private String name;
	@Column(name="joined_date") 
	@Temporal(TemporalType.TIME) private Date joinedDate;
	@Column(name="emp_salary") private double salary;
	@OneToOne(mappedBy="employee")
	@Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	private Address address;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
