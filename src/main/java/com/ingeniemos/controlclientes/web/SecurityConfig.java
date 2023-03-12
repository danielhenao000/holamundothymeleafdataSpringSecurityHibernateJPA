package com.ingeniemos.controlclientes.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/*
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("Admin")
                .password("{noop}123")
                .roles("ADMIN","USER")
                .and().withUser("user").password("{noop}123").roles("USER");
    }
}*/

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public InMemoryUserDetailsManager users() {
        // The builder will ensure the passwords are encoded before saving in memory
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}123")
                .roles("USER", "ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}root")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        /*
        http.authorizeHttpRequests()
                .requestMatchers("/editar/**", "/agregar/**", "/eliminar")
                .hasAnyRole("ADMIN")
                .requestMatchers("/")
                .hasAnyRole("USER","ADMIN")
                //.and().exceptionHandling().accessDeniedPage("/errores/403")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and().exceptionHandling().accessDeniedPage(accessDeniedHandler())

                //.formLogin((form) -> form
                 //       .loginPage("/login").permitAll())


                ;
        return http.build();*/


        http.csrf()
                .disable()
                .httpBasic()
                .disable()
                .authorizeRequests()
                .requestMatchers("/editar/**", "/agregar/**", "/eliminar")
                .hasAnyRole("ADMIN")
                .requestMatchers("/")
                .hasAnyRole("USER","ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                //.failureHandler(authenticationFailureHandler())
                //.successHandler(authenticationSuccessHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .logout();
        return http.build();
    }
    /*
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }*/
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccesoDenegadoCliente();
    }
}


