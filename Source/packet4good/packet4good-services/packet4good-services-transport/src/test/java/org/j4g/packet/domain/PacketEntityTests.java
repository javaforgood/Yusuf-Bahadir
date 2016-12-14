package org.j4g.packet.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import javax.persistence.PersistenceException;
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
 * Data JPA tests for {@link Packet}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestConfig.class)
@Transactional
public class PacketEntityTests {
	 
	private static final int DEFAULT_PACKET_DETAIL_SIZE = 0; 
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void createWhenSizeIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Packet Size must not be null");
		new Packet(null, PacketType.ADULT);
	}

	@Test
	public void createWhenTypeIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Packet Type must not be null");
		new Packet(PacketSize.MEDIUM, null);
	}
	
	@Test
	public void saveShouldPersistData() throws Exception {
		Packet packet = this.entityManager.persistFlushFind(new Packet(PacketSize.LARGE, PacketType.BABY)); 
		assertNotNull(packet.getId()); 
		assertThat(packet.getSize()).isEqualTo(PacketSize.LARGE);
		assertThat(packet.getType()).isEqualTo(PacketType.BABY);
		assertThat(packet.getDetails().size()).isEqualTo(DEFAULT_PACKET_DETAIL_SIZE);
	}
	
	@Test
	public void saveWhenTypeAndSizeSameShouldThrownException() throws Exception {
		this.thrown.expect(PersistenceException.class);
		this.entityManager.persistFlushFind(new Packet(PacketSize.LARGE, PacketType.BABY)); 
		this.entityManager.persistFlushFind(new Packet(PacketSize.LARGE, PacketType.BABY)); 
	}
	
	@Test
	public void addPacketDetailShouldMergeData() throws Exception {
		Packet packet = this.entityManager.persistFlushFind(new Packet(PacketSize.LARGE, PacketType.BABY)); 
		assertNotNull(packet.getId());
		 
		Product product = this.entityManager.persist(new Product(new Faker().idNumber().valid(), 
				new Faker().shakespeare().hamletQuote()));
		PacketDetail packetDetail = this.entityManager.persist(new PacketDetail(product, 
				new Faker().number().numberBetween(1,  9)));
		
		packet.addDetail(packetDetail);
		this.entityManager.merge(packet); 
		packet = this.entityManager.find(Packet.class, packet.getId());		
		
		assertThat(packet.getDetails().size()).isEqualTo(1); 
	}
	
	@Test
	public void removePacketShouldMergeData() throws Exception {
		Packet packet = this.entityManager.persistFlushFind(new Packet(PacketSize.LARGE, PacketType.BABY)); 
		assertNotNull(packet.getId());
		 
		Product product = this.entityManager.persist(new Product(new Faker().idNumber().valid(), 
				new Faker().shakespeare().hamletQuote()));
		PacketDetail packetDetail = this.entityManager.persist(new PacketDetail(product, 
				new Faker().number().numberBetween(1,  9)));
		
		packet.addDetail(packetDetail);
		this.entityManager.merge(packet); 
		packet = this.entityManager.find(Packet.class, packet.getId());		
		
		assertThat(packet.getDetails().size()).isEqualTo(1); 
		
		packet.removeDetail(packetDetail);
		
		this.entityManager.merge(packet); 
		packet = this.entityManager.find(Packet.class, packet.getId());
		assertThat(packet.getDetails().size()).isEqualTo(0);	
	}
}
