package com.sayasat.exp2.kinopoisk_check.config;

import com.sayasat.exp2.kinopoisk_check.security.JWTFilter;
import com.sayasat.exp2.kinopoisk_check.services.UserDetailsImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ttpSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsImplService userDetailsImplService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserDetailsImplService userDetailsImplService, JWTFilter jwtFilter) {
        this.userDetailsImplService = userDetailsImplService;
        this.jwtFilter = jwtFilter;
    }

    // Конфигурируем сам Spring Security
    // Конфигурируем авторизацию
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/error",
                        "/movies/search", "/movies", "/actors", "/directors", "/genres", "/comments/**/list").permitAll()
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Настраиваем аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsImplService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
