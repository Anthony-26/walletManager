package com.example.walletmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.example.walletmanager.exception.DelegatedAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    // private final DelegatedAuthenticationEntryPoint authEntryPoint;
    // private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorized) -> authorized
                    .requestMatchers(HttpMethod.POST, "/api/authenticate/**").permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // .exceptionHandling(exceptionHandling ->
                // exceptionHandling.authenticationEntryPoint(authEntryPoint))

                // .authenticationProvider(authenticationProvider)     /*  Allow to add a AuthenticationProvider as a provider
                //                                                         Order is important
                //                                                         But if only one provider -> redundant    */

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /*
     * Configuring UserDetailsService & creating UserDetails Object
     * Necessary with DaoAuthenticationProvider
     */

    // @Bean
    // public UserDetailsService users() {
    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(("$2a$10$ITUBEcp6OM43B/qfcWyf5Ob/FjIqX5DluHgant9iUrIQRz6/pOMP."))
    //             .roles("ADMIN")
    //             .build();

    //     UserDetails user = User.builder()
    //             .username("user")
    //             .password(("$2a$10$kt9.bWmAs4YNgZxwOUinRua50UhmY2Tg5.4KYFA91pQ3ZAVNKQefi"))
    //             .roles("USER")
    //             .build();
    //     return new InMemoryUserDetailsManager(admin, user);
    // }

}