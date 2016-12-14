package org.j4g.packet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.j4g.packet.domain.Need;
import org.j4g.packet.domain.Packet;
import org.j4g.packet.domain.PacketNeed;
import org.j4g.packet.domain.PacketSize;
import org.j4g.packet.domain.PacketType;
import org.j4g.packet.domain.Status;
import org.j4g.packet.repository.NeedRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.github.javafaker.Faker;

/**
 * Service tests for {@link PacketNeedsService}.
 */
 
public class PacketNeedsServiceTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
 
	@Mock
	private NeedRepository needRepository;
	
	private PacketNeedsService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.service = new PacketNeedsService(this.needRepository);
	}

	@Test
	public void getPacketNeedsShouldReturnNeedsPage() throws Exception {
		given(this.needRepository.findByStatusOrderByArrivedBestTimeAsc(Status.PREPARING, new PageRequest(0, 10)))
		.willReturn(new PageImpl<Need>(generateData()));
		Page<Needs> needsPage = this.service.getPacketNeeds(new PageRequest(0, 10));
		assertThat(needsPage.getTotalElements()).isEqualTo(3);
		assertNotNull(needsPage.getContent().get(0).getArrivedBestTime()); 
	}
	  
	
	private List<Need> generateData() {
		Faker faker = new Faker();
		List<Need> needs = new ArrayList<Need>();
		needs.add(new Need(faker.shakespeare().kingRichardIIIQuote(),faker.address().streetAddress(),LocalDateTime.now()));
		needs.add(new Need(faker.shakespeare().kingRichardIIIQuote(),faker.address().streetAddress(),LocalDateTime.now()));
		needs.add(new Need(faker.shakespeare().kingRichardIIIQuote(),faker.address().streetAddress(),LocalDateTime.now()));
		needs.get(1).addPacket(new PacketNeed(new Packet(PacketSize.LARGE, PacketType.BABY), 21));
		return needs;
	}
}
