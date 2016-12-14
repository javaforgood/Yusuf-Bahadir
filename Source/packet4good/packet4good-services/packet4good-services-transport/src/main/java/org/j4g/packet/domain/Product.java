package org.j4g.packet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity
@Table(name = "PRODUCT")
public class Product {

	@Id
	@Column(name="NO")
	private String no;

	@Column(name="NAME", unique = true) 
	private String name;

	@Column(name="DSC")
	private String desc;

	@Column(name="BRAND")
	private String brand;

	public Product() {
		
	}
	
	public Product(String no, String name) {
		Assert.notNull(no, "No must not be null");
		Assert.notNull(name, "Name must not be null"); 
		this.no = no;
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((no == null) ? 0 : no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (no == null) {
			if (other.no != null)
				return false;
		} else if (!no.equals(other.no))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [no=" + no + ", name=" + name + ", desc=" + desc + ", brand=" + brand + "]";
	}
 
}
