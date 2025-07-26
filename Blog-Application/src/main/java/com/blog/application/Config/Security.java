package com.blog.application.Config;

import com.blog.application.Helper.JWTAuthenticationEntryPoint;
import com.blog.application.Helper.JWTAuthenticationFilter;
import com.blog.application.Helper.JWTHelper;
import com.blog.application.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class Security {

    @Autowired
    private JWTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetailsService, JWTHelper jwtHelper) {
        return new JWTAuthenticationFilter(userDetailsService, jwtHelper);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JWTAuthenticationFilter authenticationFilter) throws Exception {

        http.cors(cors -> cors.disable());
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.POST,"/auth/register").permitAll();
            auth.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
            auth.requestMatchers(HttpMethod.GET,"/post/getAll").permitAll();
            auth.requestMatchers(HttpMethod.GET,"/post/getSingle/**").permitAll();
            auth.requestMatchers(HttpMethod.GET,"/comment/readComment/**").permitAll();
            auth.requestMatchers(HttpMethod.GET,"/comment/getSingleComment/**").permitAll();
            auth.requestMatchers("/post/create").hasRole("ADMIN");
            auth.anyRequest().authenticated();
        });

        http.exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
