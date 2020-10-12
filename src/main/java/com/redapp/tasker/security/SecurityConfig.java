package com.redapp.tasker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Autowired
private UserDetailsService userDetailsService;

@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder; 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();
	http.cors().disable();
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.authorizeRequests().antMatchers("/login/**","/public/**","**/upload/**").permitAll().and()
	.authorizeRequests().antMatchers("/admin/**").hasAuthority("MANAGER").and()
	.authorizeRequests().antMatchers("/users/**").authenticated().and()
	.addFilter(new JWTAuthntificationFilter(authenticationManager()))
	.addFilterBefore(new JWTAutorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	

	}
	

}