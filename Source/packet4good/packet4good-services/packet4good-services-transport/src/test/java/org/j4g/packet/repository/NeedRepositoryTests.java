package org.j4g.packet.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.j4g.packet.config.TestConfig;
import org.j4g.packet.domain.Need;
import org.j4g.packet.domain.Status;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;

/**
 * Data JPA tests for {@link Need}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestConfig.class)
@Transactional
public class NeedRepositoryTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private NeedRepository repository;

	@Test
	public void findByStatusOrderByArrivedBestTimeAscShouldReturnPageNeeds() throws Exception {
		String bestTimeNeed = fillData();

		Page<Need> need = this.repository.findByStatusOrderByArrivedBestTimeAsc(Status.PREPARING,
				new PageRequest(0, 10));
		assertThat(need.getTotalElements()).isEqualTo(4);
		assertThat(need.getContent().get(0).getName()).isEqualTo(bestTimeNeed);
	}
	
	@Test
	public void findByStatusOrderByArrivedBestTimeAscShouldReturnEmptyPage() throws Exception {
		
		fillData();
		
		Page<Need> need = this.repository.findByStatusOrderByArrivedBestTimeAsc(Status.COMPLETED,
				new PageRequest(0, 10));
		assertThat(need.getTotalElements()).isEqualTo(0);
		assertNotNull(need.getContent());
	}
	
	
	private String fillData() {
		Faker faker = new Faker();
		String bestTimeNeed = faker.shakespeare().romeoAndJulietQuote();
		
		this.entityManager.persist(new Need(faker.shakespeare().romeoAndJulietQuote(), faker.address().streetAddress(),
				LocalDateTime.now().plusDays(3)));
		this.entityManager
				.persist(new Need(bestTimeNeed, faker.address().streetAddress(), LocalDateTime.now().plusDays(1)));
		this.entityManager.persist(new Need(faker.shakespeare().romeoAndJulietQuote(), faker.address().streetAddress(),
				LocalDateTime.now().plusDays(2)));
		this.entityManager.persist(new Need(faker.shakespeare().romeoAndJulietQuote(), faker.address().streetAddress(),
				LocalDateTime.now().plusDays(4)));
		
		return bestTimeNeed;
	}
}
