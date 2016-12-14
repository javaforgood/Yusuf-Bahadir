package org.j4g.packet.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.Assert;

@Entity
@Table(name = "PACKET", uniqueConstraints={@UniqueConstraint(columnNames = "PACKET_SIZE"),
		@UniqueConstraint(columnNames = "PACKET_TYPE")})
public class Packet {

	@Id
	@GeneratedValue
	private Long id; 

	@Enumerated(EnumType.STRING)
	@Column(name="PACKET_SIZE")
	private PacketSize size;

	@Enumerated(EnumType.STRING)
	@Column(name="PACKET_TYPE")
	private PacketType type;	 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="packet")
	private Set<PacketDetail> details = new HashSet<PacketDetail>();
	
	public Packet() {
	} 
	 
	public Packet(PacketSize size, PacketType type) {
		Assert.notNull(size, "Packet Size must not be null");
		Assert.notNull(type, "Packet Type must not be null");
		this.size = size;
		this.type = type;
	}

	public void addDetail(PacketDetail packetDetail) {
		this.details.add(packetDetail);		
	}
	
	public void removeDetail(PacketDetail packetDetail) {
		this.details.remove(packetDetail);		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PacketSize getSize() {
		return size;
	}

	public void setSize(PacketSize size) {
		this.size = size;
	}

	public PacketType getType() {
		return type;
	}

	public void setType(PacketType type) {
		this.type = type;
	}

	
	public Set<PacketDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<PacketDetail> details) {
		this.details = details;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Packet other = (Packet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (size != other.size)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Packet [id=" + id + ", size=" + size + ", type=" + type + "]";
	}

  
}
