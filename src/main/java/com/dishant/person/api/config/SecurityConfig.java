package com.dishant.person.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Profile({ "default", "cloud" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String PERSONS_PATH = "/persons/**";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/management/health").permitAll()
                .antMatchers(HttpMethod.GET, "/management/**").authenticated()

                .antMatchers(HttpMethod.GET, PERSONS_PATH)
                .hasAnyRole(SecurityScope.USER.name(), SecurityScope.ADMIN.name())
                .antMatchers(HttpMethod.POST, PERSONS_PATH)
                .hasAnyRole(SecurityScope.USER.name(), SecurityScope.ADMIN.name())
                .antMatchers(HttpMethod.PUT, PERSONS_PATH).hasRole(SecurityScope.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, PERSONS_PATH).hasRole(SecurityScope.ADMIN.name())
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

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
