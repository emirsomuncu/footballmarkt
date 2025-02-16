package com.somuncu.footballmarkt.security;

import com.somuncu.footballmarkt.security.webtoken.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration  {

    //private final CustomUserDetailsService customUserDetailsService;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/v1/auth/**" ).permitAll()
                                .requestMatchers(
                                        "/api/v1/players/create" ,"/api/v1/players/update" , "/api/v1/players/delete",
                                        "/api/v1/stats/add", "/api/v1/stats/update" , "/api/v1/stats/delete/{id}",
                                        "/api/v1/images/save" , "/api/v1/images/image/{imageId}/update", "/api/v1/images/image/{imageId}/delete",
                                        "/api/v1/clubs/add" , "/api/v1/clubs/update" , "/api/v1/clubs/delete",
                                        "/api/v1/clubhistories/clubhistory/add" ,"/api/v1/clubhistories/clubhistory/update", "/api/v1/clubhistories/clubhistory/delete" ,
                                        "/api/v1/leagues/league/add" , "/api/v1/leagues/league/update" , "/api/v1/leagues/{leagueId}/delete"
                                        ).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
    */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

}
