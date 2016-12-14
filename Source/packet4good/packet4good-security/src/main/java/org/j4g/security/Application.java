package org.j4g.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
 
/**
 * Easy to retrieve an access token using:
 * {@code curl -X POST -vu acme:acmesecret http://localhost:9999/uaa/oauth/token -H "Accept: application/json" -d "password=spring&username=jlong&grant_type=password&scope=openid&client_secret=acmesecret&client_id=acme" }
 *
 * Then, send the access token to an OAuth2 secured REST resource using:
 * {@code curl http://localhost:9000/hi -H "Authorization: Bearer _INSERT TOKEN_"}
 *
 * @author Yusuf Bahadır
 */
@SpringBootApplication
@EnableResourceServer
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
