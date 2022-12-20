package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/", "/login", "/css/**", "/error").permitAll()
                    .antMatchers("/user/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .defaultSuccessUrl("/bidList/list")
                .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/bidList/list")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/app/error")
                .and()
                    .logout()
                    .logoutUrl("/app-logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");
    }
}
