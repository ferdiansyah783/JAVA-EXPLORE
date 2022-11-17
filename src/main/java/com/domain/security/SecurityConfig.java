package com.domain.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.domain.filter.CustomAuthenticationFilter;
import com.domain.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/users/auth/register").permitAll();
        http.authorizeHttpRequests().antMatchers("/login").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/roles").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/users/project/user").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/projects").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/files/**").permitAll();
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(authenticationConfiguration)));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        // .csrf()
        // .disable()
        // .authorizeRequests((request) -> {
        // try {
        // request.antMatchers("/api/users/auth/register").permitAll().anyRequest()
        // .authenticated().and().httpBasic();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }).logout((logout) -> logout.logoutUrl("/api/users/auth/logout")
        // .addLogoutHandler(new SecurityContextLogoutHandler()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfiguration)
            throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

}
