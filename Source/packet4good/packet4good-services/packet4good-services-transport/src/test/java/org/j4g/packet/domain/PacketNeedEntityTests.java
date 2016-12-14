package org.j4g.packet.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

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
 * Data JPA tests for {@link PacketNeed}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestConfig.class)
@Transactional
public class PacketNeedEntityTests {
	
	private static Packet MEDIUM_TEENEGER = new Packet(PacketSize.MEDIUM, PacketType.TEENEGER);
	private static final Packet ADULT_LARGE = new Packet(PacketSize.LARGE, PacketType.ADULT);

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void createWhenPacketIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Packet must not be null");
		new PacketNeed(null, new Faker().number().randomDigit());
	}
	
	@Test
	public void createWhenCountLessThanZeroShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Count must not be bigger than 0");
		new PacketNeed(ADULT_LARGE, 0);
	}
 
	@Test
	public void saveShouldPersistData() throws Exception {
		MEDIUM_TEENEGER = this.entityManager.persistFlushFind(MEDIUM_TEENEGER);
		int count = new Faker().number().randomDigit();
		PacketNeed packetNeed = this.entityManager.persistFlushFind(new PacketNeed(MEDIUM_TEENEGER, count));
		assertNotNull(packetNeed.getId());
		assertThat(packetNeed.getPacket().getType()).isEqualTo(PacketType.TEENEGER);
		assertThat(packetNeed.getPacket().getSize()).isEqualTo(PacketSize.MEDIUM); 
		assertThat(packetNeed.getCount()).isEqualTo(count); 
	}	  
}
