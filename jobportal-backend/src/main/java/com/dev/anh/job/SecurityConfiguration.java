package com.dev.anh.job;

import static org.springframework.security.config.Customizer.withDefaults;

import java.time.LocalDateTime;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.service.JwtTokenFilter;
import com.dev.anh.job.utils.exception.SecurityExceptionHandler;


@Configuration
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		 http.csrf(csrf -> csrf.disable());
		 http.cors(withDefaults());
		 http.sessionManagement(session -> {
			  session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 });
		 
		 http.authorizeHttpRequests(request -> { 
			 request.requestMatchers("/token/**").permitAll();
			 request.requestMatchers("/admin/**").hasAuthority("Admin");
			 request.requestMatchers("/company/**").hasAuthority("CompanyAccount");
			 request.requestMatchers("/applicant/**").hasAnyAuthority("Applicant", "CompanyAccount");
			 request.anyRequest().authenticated();
		 });

		 http.addFilterAfter(jwtTokenFilter(), ExceptionTranslationFilter.class);
		 
		 http.exceptionHandling(exception -> { 
			exception.authenticationEntryPoint(securityExceptionHandler());
			exception.accessDeniedHandler(securityExceptionHandler()); 
		 });
		 
		 
		 return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	}

	@Bean
	JwtTokenFilter jwtTokenFilter() {
		 return new JwtTokenFilter();
	}
	
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	@Bean
	ApplicationRunner applicationRunner(AccountRepo repo) {
		return args -> {
		   if(repo.count() == 0L) {
			    var admin = new Account();
			    admin.setUsername("Admin");
			    admin.setEmail("admin@gmail.com");
			    admin.setPassword(passwordEncoder().encode("admin"));
			    admin.setRole(Role.Admin);
			    admin.setActive(true);
			    admin.setActivatedAt(LocalDateTime.now());
			    
			    repo.save(admin);
		   }
		};
	}
	
	@Bean
	SecurityExceptionHandler securityExceptionHandler() {
		 return new SecurityExceptionHandler();
	}
}
