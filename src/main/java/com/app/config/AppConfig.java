//package com.app.config;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//import org.springframework.web.cors.CorsConfigurationSource;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//    	http
//        .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//        .authorizeHttpRequests(authorize -> 
//            authorize
//                .requestMatchers("/api/auth/signup").permitAll()
//                .requestMatchers("/api/auth/signin").permitAll() // Allow unauthenticated access to /api/auth/signup
////                .requestMatchers("/api/**")
////                .authenticated()       // Authenticate other /api/** requests
//                .anyRequest().permitAll()
//            )
//        .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
//        .csrf().disable()
//        .cors().configurationSource(new CorsConfigurationSource() {
//			
////            @Override
////            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
////                CorsConfiguration cfg = new CorsConfiguration();
////                cfg.setAllowedOrigins(Arrays.asList(
////                    "http://localhost:3000/"
////                ));
////                cfg.setAllowedMethods(Collections.singletonList("*"));
////                cfg.setAllowCredentials(true);
////                cfg.setAllowedHeaders(Collections.singletonList("*"));
////                cfg.setExposedHeaders(Arrays.asList("Authorization"));
////                cfg.setMaxAge(3600L);
////                
////                return cfg;
////            }
//        	
////        	@Override
////        	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
////        	    CorsConfiguration cfg = new CorsConfiguration();
////        	    
////        	    // Set allowed origins explicitly
////        	    cfg.setAllowedOrigins(Arrays.asList(
////        	        "http://localhost:3001" // Removed trailing slash
////        	    ));
////        	    
////        	    // Set specific allowed methods instead of wildcard "*"
////        	    cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////        	    
////        	    // Allow credentials
////        	    cfg.setAllowCredentials(true);
////        	    
////        	    // Set specific allowed headers instead of wildcard "*"
////        	    cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
////        	    
////        	    // Expose specific headers
////        	    cfg.setExposedHeaders(Arrays.asList("Authorization"));
////        	    
////        	    // Set max age for preflight requests
////        	    cfg.setMaxAge(3600L);
////        	    
////        	    return cfg;
////        	}
//        	
//        	
//        	@Override
//        	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//        	    CorsConfiguration cfg = new CorsConfiguration();
//        	    
//        	    // Set allowed origins explicitly (can add more origins if needed)
//        	    cfg.setAllowedOrigins(Arrays.asList(
//        	        "http://localhost:3001" // You can add more origins if necessary
//        	    ));
//        	    
//        	    // Set specific allowed methods instead of wildcard "*"
//        	    cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        	    
//        	    // Allow credentials
//        	    cfg.setAllowCredentials(true);
//        	    
//        	    // Set specific allowed headers instead of wildcard "*"
//        	    cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
//        	    
//        	    // Expose specific headers
//        	    cfg.setExposedHeaders(Arrays.asList("Authorization"));
//        	    
//        	    // Set max age for preflight requests
//        	    cfg.setMaxAge(3600L);
//        	    
//        	    return cfg;
//        	}
//        })
//        .and().formLogin().and().httpBasic();
//
//    return http.build();
//}
//
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        config.addAllowedOrigin("*"); 
////        config.addAllowedMethod("*");
////        config.addAllowedHeader("*");
////        source.registerCorsConfiguration("/", config);
////        return source;
////    }
//    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//    	return new BCryptPasswordEncoder();
//    }
//}


package com.app.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers("/api/auth/signup").permitAll()
                    .requestMatchers("/api/auth/signin").permitAll()
                    .requestMatchers("/api/**").authenticated() // Authenticate other /api/** requests
                    .anyRequest().permitAll()
            )
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            .csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .formLogin()
            .and()
            .httpBasic();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        
        // Set allowed origins explicitly (can add more origins if needed)
        cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000")); 
        
        // Set specific allowed methods
        cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Allow credentials
        cfg.setAllowCredentials(true);
        
        // Set allowed headers
        cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        
        // Expose specific headers
        cfg.setExposedHeaders(Arrays.asList("Authorization"));
        
        // Set max age for preflight requests
        cfg.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg); // Apply CORS settings globally
        
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
