package com.dishant.person.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile({ "default", "cloud" })
public class SecurityConfig {

    private static final String PERSONS_PATH = "/persons/**";

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz.requestMatchers(HttpMethod.GET, "/management/health").permitAll()
                                .requestMatchers(HttpMethod.GET, "/management/**").authenticated()
                                .requestMatchers(HttpMethod.GET, PERSONS_PATH)
                                .hasAnyRole(SecurityScope.USER.name(), SecurityScope.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, PERSONS_PATH)
                                .hasAnyRole(SecurityScope.USER.name(), SecurityScope.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, PERSONS_PATH).hasRole(SecurityScope.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, PERSONS_PATH).hasRole(SecurityScope.ADMIN.name()))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();

        final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);
        UserDetails user = userBuilder
                .username("user.dishant")
                .password("password")
                .roles(SecurityScope.USER.name())
                .build();

        UserDetails admin = userBuilder
                .username("admin.dishant")
                .password("password")
                .roles(SecurityScope.USER.name(), SecurityScope.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
