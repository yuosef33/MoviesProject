package com.project.revision.Config;

import com.project.revision.Config.JWT.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
public class SpringConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenFilter tokenFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.securityMatcher("/**").cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.authorizeHttpRequests(api -> api
                .requestMatchers(HttpMethod.GET, "/Movie/**").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.GET, "/User/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/Redis/**").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.POST, "/User/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/Redis/**").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.DELETE, "/Redis/**").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.POST, "/ai/**").hasRole("NORMALUSER")
        );
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration= new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;

    }
}