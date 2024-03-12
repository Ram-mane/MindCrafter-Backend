package com.user.bloggs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.user.bloggs.security.CustomUserDetailsServices;
import com.user.bloggs.security.JwtAuthenticationEntryPoint;
import com.user.bloggs.security.JwtAuthenticationFilter;
import com.user.bloggs.repository.RoleRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
@Transactional
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Autowired
	private CustomUserDetailsServices customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authFilter;
//	
	

    @Autowired
    public SecurityConfig(CustomUserDetailsServices customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }
	
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	 http
         .csrf(AbstractHttpConfigurer::disable)          
         .authorizeHttpRequests(authorize -> authorize
                 .requestMatchers("/api/auth/**").permitAll()
                 .anyRequest().authenticated()).exceptionHandling()
         .authenticationEntryPoint(this.entryPoint)
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         
         http.addFilterBefore(this.authFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
     }
    
    
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
    
    
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//    	
//    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    	
//    	provider.setUserDetailsService(this.customUserDetailService);
//    	provider.setPasswordEncoder(passwordEncoder());
//    	 return provider;
//    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    } 
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    
    


}
