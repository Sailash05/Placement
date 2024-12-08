package com.placementserver.placementserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.placementserver.placementserver.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity	
public class SecurityConfig {
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterCain(HttpSecurity http) throws Exception {
	    http
	        .cors() // Enable CORS handling
	        .and()
	        .csrf(customizer -> customizer.disable()) // Disable CSRF for testing; adjust as needed for production
	        .authorizeHttpRequests(request -> 
	            request
	                //.requestMatchers("/student/addstudent", "/student/loginstudent", "/faculty/addfaculty", "/faculty/loginfaculty").permitAll() // Allow specific endpoints
	                //.anyRequest().authenticated() // Authenticate other requests
	            	.anyRequest().permitAll()
	        )
	        .httpBasic(Customizer.withDefaults())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();

	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(customUserDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
