package org.j4g.security.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User detail service for retrieve user information by username 
 *
 * @author Yusuf Bahadır 
 */
@Service("userDetailsService")
public class OAuthUserDetailsService implements UserDetailsService {

	private final JdbcTemplate jdbcTemplate;
	
	public OAuthUserDetailsService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RowMapper<User> userDetailsRowMapper = (rs, i) -> new User(
				rs.getString("ACCOUNT_NAME"),
				rs.getString("PASSWORD"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				rs.getBoolean("ENABLED"),
				AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
		return jdbcTemplate.queryForObject(
				"select * from ACCOUNT where ACCOUNT_NAME = ?", userDetailsRowMapper,
				username);
	}

}
