package org.j4g.packet.web.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.j4g.packet.config.TestConfig;
import org.j4g.packet.service.Needs;
import org.j4g.packet.service.PacketNeedsService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.javafaker.Faker;

/**
 * {@code @WebMvcTest} based tests for {@link PacketController}.
 *
 * @author Yusuf Bahadır
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestConfig.class)
@Transactional
public class PacketControllerTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext wac;
 
	@MockBean
	private PacketNeedsService service;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void getPacketNeedsShouldReturnPagedNeedsResources() throws Exception {
		given(this.service.getPacketNeeds(new PageRequest(0, 10))).willReturn(new PageImpl<Needs>(fillServiceData()));
		this.mockMvc.perform(get("/packet").accept(MediaType.APPLICATION_JSON_VALUE).param("page", "0").param("size", "10")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
	}

	private List<Needs> fillServiceData() {
		Faker faker = new Faker();
		List<Needs> needsPage = new ArrayList<>();

		Needs needs = new Needs(faker.chuckNorris().fact(), faker.address().streetAddress(), LocalDateTime.now());
		needsPage.add(needs);

		return needsPage;
	}
}
