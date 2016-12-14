package org.j4g.security.web.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Default user information controller for secured with token from other microservices 
 *
 * @author Yusuf Bahadır 
 */
@RestController
public class OAuthUserController {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	} 
}
