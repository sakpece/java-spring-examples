package com.adaabindia.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()

                .withUser("adaab").password("adaab123").roles("USER").and()
                .withUser("admin").password("admin123").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .antMatchers("**/rest/admin/*").hasRole("ADMIN")
                .antMatchers("**/rest/user/*").hasAnyRole("USER","ADMIN")
                //.antMatchers("**/rest/*").anonymous()
                .and().formLogin();
                //.addFilterBefore(customFilter(), BasicAuthenticationFilter.class)
                //.httpBasic();
        httpSecurity.csrf().disable();

    }

    @Bean
    public CustomFilter customFilter() {
        return new CustomFilter();
    }
}
