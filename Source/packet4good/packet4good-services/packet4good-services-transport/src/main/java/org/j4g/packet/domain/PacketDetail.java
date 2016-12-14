package org.j4g.packet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity
@Table(name = "PACKET_DETAIL")
public class PacketDetail {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id; 

	@ManyToOne
	@JoinColumn(name = "PRODUCT_NO", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "PACKET_ID" )
	private Packet packet;

	@Column(name="COUNT")
	private int count;
 
	public PacketDetail() { 
	}
	
	public PacketDetail(Product product, int count) { 
		Assert.notNull(product, "Product must not be null");
		Assert.isTrue(count > 0, "Count must not be bigger than 0");
		this.product = product;
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		PacketDetail other = (PacketDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PacketDetail [id=" + id + ", product=" + product + ", count=" + count + "]";
	}
		 
}
