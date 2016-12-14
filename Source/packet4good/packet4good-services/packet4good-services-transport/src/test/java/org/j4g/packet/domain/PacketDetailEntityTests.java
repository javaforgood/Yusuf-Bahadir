package org.j4g.packet.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
 * Data JPA tests for {@link PacketDetail}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestConfig.class)
@Transactional
public class PacketDetailEntityTests {
	 	
	private static Product SHAMPOO = new Product(new Faker().idNumber().valid(), 
			new Faker().shakespeare().hamletQuote());
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void createWhenPacketIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Product must not be null");
		new PacketDetail(null, new Faker().number().randomDigit());
	}
	
	@Test
	public void createWhenCountLessThanZeroShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Count must not be bigger than 0");
		new PacketDetail(SHAMPOO, 0);
	}
	
	@Test
	public void saveShouldPersistData() throws Exception {
		int count = new Faker().number().numberBetween(1,  20);
		SHAMPOO = this.entityManager.persistFlushFind(SHAMPOO);
		PacketDetail packetDetail = this.entityManager.persistFlushFind(new PacketDetail(SHAMPOO, count)); 
		assertNotNull(packetDetail.getId());
		assertNotNull(packetDetail.getProduct());
		assertThat(packetDetail.getCount()).isEqualTo(count);  
	}
	
	@Test
	public void updateShouldMergeData() throws Exception {
		int count = new Faker().number().numberBetween(1,  20);
		SHAMPOO = this.entityManager.persistFlushFind(SHAMPOO);
		PacketDetail packetDetail = this.entityManager.persistFlushFind(new PacketDetail(SHAMPOO, count)); 
		assertNotNull(packetDetail.getId()); 
		assertThat(packetDetail.getCount()).isEqualTo(count);  
		
		count = new Faker().number().numberBetween(21,  41);
		packetDetail.setCount(count);
		packetDetail = this.entityManager.merge(packetDetail);
		packetDetail = this.entityManager.find(PacketDetail.class, packetDetail.getId());
		
		assertThat(packetDetail.getCount()).isEqualTo(count);  
		
	}
	
	@Test
	public void deleteShouldRemoveData() throws Exception {
		int count = new Faker().number().numberBetween(1,  20);
		SHAMPOO = this.entityManager.persistFlushFind(SHAMPOO);
		PacketDetail packetDetail = this.entityManager.persistFlushFind(new PacketDetail(SHAMPOO, count)); 
		assertNotNull(packetDetail.getId()); 
		assertThat(packetDetail.getCount()).isEqualTo(count);  
		 
		this.entityManager.remove(packetDetail);
		
		packetDetail = this.entityManager.find(PacketDetail.class, packetDetail.getId());
		
		assertNull(packetDetail); 
			
	} 
}
