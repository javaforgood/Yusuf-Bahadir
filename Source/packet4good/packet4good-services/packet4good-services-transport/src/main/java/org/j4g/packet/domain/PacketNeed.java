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
@Table(name = "PACKET_NEED")
public class PacketNeed {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="COUNT")
	private int count;

	@ManyToOne
	@JoinColumn(name = "PACKET_ID", nullable = false)
	private Packet packet;
	
	@ManyToOne
	@JoinColumn(name = "NEED_ID", nullable = false)
	private Need need;

	public PacketNeed() {
	}
	
	public PacketNeed(Packet packet, int count) {
		Assert.notNull(packet, "Packet must not be null");
		Assert.isTrue(count > 0, "Count must not be bigger than 0");
		this.packet = packet;
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Need getNeed() {
		return need;
	}

	public void setNeed(Need need) {
		this.need = need;
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
		PacketNeed other = (PacketNeed) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PacketNeed [id=" + id + ", count=" + count + "]";
	}

}
