package com.adaabindia.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource ds() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://127.0.0.1/user_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("adaab@123");
        return dataSource;
    }
    	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.authoritiesByUsernameQuery("select user_name, role from userdetails where user_name=?")
				.usersByUsernameQuery("select user_name, password, 1 as enabled  from userdetails where user_name=?");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().anyRequest().fullyAuthenticated().antMatchers("**/rest/admin/*")
				.hasRole("ADMIN").antMatchers("**/rest/user/*").hasAnyRole("USER", "ADMIN")
				.and().httpBasic();
		httpSecurity.addFilterBefore(customFilter(), BasicAuthenticationFilter.class)
				.csrf()
				.disable();
	}

	@Bean
	public CustomFilter customFilter() {
		return new CustomFilter();
	}
}
