package org.j4g.packet.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Exceptional case configuration for spring test with security
 * See this Stack Overflow answer from spring developers:
 * http://stackoverflow.com/questions/38675020/testing-security-in-spring-boot-1-4
 * 
 * @author yusuf.bahadir
 *
 */
@EnableAuthorizationServer 
public class TestConfig {
 
	@Bean
	public TestEntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return new TestEntityManager(entityManagerFactory);
	}
}
