package com.bookstore.security;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bookstore.security.JWT.JwtEntryPoint;
import com.bookstore.security.JWT.JwtFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
@DependsOn("passwordEncoder")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	private JwtEntryPoint accessDenyHandler;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@org.springframework.beans.factory.annotation.Value("${spring.queries.users-query}")
	private String usersQuery;

	@Autowired
	@org.springframework.beans.factory.annotation.Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource((javax.sql.DataSource) dataSource).passwordEncoder(passwordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()

				.antMatchers("/profile/**").authenticated().antMatchers("/cart/**").access("hasAnyRole('CUSTOMER')")
				.antMatchers("/order/finish/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')").antMatchers("/order/**")
				.authenticated().antMatchers("/profiles/**").authenticated().antMatchers("/seller/product/new")
				.access("hasAnyRole('MANAGER')").antMatchers("/seller/**/delete").access("hasAnyRole( 'MANAGER')")
				.antMatchers("/seller/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')").anyRequest().permitAll()

				.and().exceptionHandling().authenticationEntryPoint(accessDenyHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
