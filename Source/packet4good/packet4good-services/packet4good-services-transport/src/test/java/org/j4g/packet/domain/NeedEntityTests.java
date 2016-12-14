package org.j4g.packet.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.j4g.packet.config.TestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;

/**
 * Data JPA tests for {@link Need}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestConfig.class)
@Transactional
public class NeedEntityTests {
	 
	private static final int DEFAULT_PACKET_SIZE = 0;
	private static final String NAME = new Faker().shakespeare().asYouLikeItQuote();
	private static final String ADDRESS = new Faker().address().streetAddress();
	
	private static final Need NEED = new Need(NAME, ADDRESS, LocalDateTime.now());

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void createWhenNameIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Name must not be null");
		new Need(null, ADDRESS);
	}
	
	@Test
	public void createWhenAddressIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Address must not be null");
		new Need(NAME, null);
	}
	
	@Test
	public void createWhenArrivedBestDateIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("ArrivedBestTime must not be null");
		new Need(NAME, ADDRESS, null);
	}
 
	@Test
	public void saveShouldPersistData() throws Exception {
		Need need = this.entityManager.persistFlushFind(NEED);
		assertThat(need.getName()).isEqualTo(NAME);
		assertThat(need.getAddress()).isEqualTo(ADDRESS);
		assertThat(need.getStatus()).isEqualTo(Status.PREPARING);
		assertThat(need.getPacketNeeds().size()).isEqualTo(DEFAULT_PACKET_SIZE);
		assertNotNull(need.getId());
		assertNotNull(need.getArrivedBestTime());
	}
	
	@Test
	public void addPacketShouldMergeData() throws Exception {
		Need need = this.entityManager.persist(NEED);
		assertNotNull(need.getId());
		
		Packet packet = this.entityManager.persist(new Packet(PacketSize.LARGE, PacketType.BABY));
		PacketNeed packetNeed = this.entityManager.persist(new PacketNeed(packet, 10));
		
		need.addPacket(packetNeed);
		this.entityManager.merge(need); 
		need = this.entityManager.find(Need.class, need.getId());		
		
		assertThat(need.getPacketNeeds().size()).isEqualTo(1); 
	}
	
	@Test
	public void removePacketShouldMergeData() throws Exception {
		Need need = this.entityManager.persist(NEED);
		assertNotNull(need.getId());
		
		Packet packet = this.entityManager.persist(new Packet(PacketSize.LARGE, PacketType.BABY));
		PacketNeed packetNeed = this.entityManager.persist(new PacketNeed(packet, 10));
		
		need.addPacket(packetNeed);
		this.entityManager.merge(need); 
		need = this.entityManager.find(Need.class, need.getId());		
		
		assertThat(need.getPacketNeeds().size()).isEqualTo(1); 
		
		need.removeNeed(packetNeed);
		this.entityManager.merge(need); 
		need = this.entityManager.find(Need.class, need.getId());
		assertThat(need.getPacketNeeds().size()).isEqualTo(DEFAULT_PACKET_SIZE);	
	}
}
