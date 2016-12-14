package org.j4g.security;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.jsonpath.JsonPath;

/**
 * Integration test for {@link Application} starting on a random port.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ApplicationIntegrationTests {

	@Value("${local.server.port}")
	private String port;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testUnauthenticatedUser() throws Exception {
		TestRestTemplate template = new TestRestTemplate(HttpClientOption.ENABLE_COOKIES);
		ResponseEntity<String> httpsEntity = template.getForEntity("http://localhost:"
				+ this.port + "/sso/user", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, httpsEntity.getStatusCode()); 
		assertEquals("unauthorized", JsonPath.parse(httpsEntity.getBody()).read("$['error']"));
	}
	 
}
