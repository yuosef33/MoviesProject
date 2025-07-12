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
                .requestMatchers(HttpMethod.GET, "/Redis/**").hasRole("DEVELPOER")
                .requestMatchers(HttpMethod.POST, "/Redis/**").hasRole("DEVELPOER")
                .requestMatchers(HttpMethod.DELETE, "/Redis/**").hasRole("DEVELPOER")
                .requestMatchers(HttpMethod.GET, "/User/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/User/signup").permitAll()
                .requestMatchers(HttpMethod.POST, "/User/signup2").permitAll()
                .requestMatchers(HttpMethod.POST, "/User/verify").permitAll()
                .requestMatchers(HttpMethod.PUT, "/User/update/data").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.PUT, "/User/update/password").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.GET, "/User/data").hasAnyRole("NORMALUSER","DEVELPOER")
                .requestMatchers(HttpMethod.POST, "/OAuth2/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/User/addImage/Upload").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.DELETE, "/ai/**").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.GET, "/TMDBAPI/**").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.POST, "/ai/**").hasRole("NORMALUSER")
                .requestMatchers(HttpMethod.POST, "/v2/**","/v3/api-docs/**","/v3/api-docs","/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.GET, "/v2/**","/v3/api-docs/**","/v3/api-docs","/swagger-ui.html","/swagger-ui/**").permitAll()

        );

        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration= new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;

    }
}