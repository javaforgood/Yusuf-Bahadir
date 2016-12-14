package org.j4g.packet.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Entity
@Table(name = "NEED")
public class Need implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(name = "NAME")
	private String name;

	@Column(name="DSC")
	private String desc;

	@NotNull
	@Column(name="ADDRESS")
	private String address;

	@Enumerated
	@Column(name="STATUS")
	private Status status = Status.PREPARING;
	
	@NotNull
	@Column(name = "ARRIVED_BEST_TIME", nullable = false)
	private LocalDateTime arrivedBestTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="need")
	private Set<PacketNeed> packetNeeds = new HashSet<PacketNeed>();

	public Need() {
		
	}
	
	public Need(String name, String address) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(address, "Address must not be null");
		this.name = name;
		this.address = address;
	}
	
	public Need(String name, String address, LocalDateTime arrivedBestTime) {
		this(name, address);
		Assert.notNull(arrivedBestTime, "ArrivedBestTime must not be null");
		this.arrivedBestTime = arrivedBestTime;
	}
	
	public void addPacket(PacketNeed packetNeed) { 
		this.packetNeeds.add(packetNeed);	
	}

	public void removeNeed(PacketNeed packetNeed) {
		this.packetNeeds.remove(packetNeed);		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getArrivedBestTime() {
		return arrivedBestTime;
	}

	public void setArrivedBestTime(LocalDateTime arrivedBestTime) {
		this.arrivedBestTime = arrivedBestTime;
	}

	public Set<PacketNeed> getPacketNeeds() {
		return packetNeeds;
	}

	public void setPacketNeeds(Set<PacketNeed> needs) {
		this.packetNeeds = needs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Need other = (Need) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}
