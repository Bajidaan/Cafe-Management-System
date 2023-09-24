package com.bajidan.cafe_ms.JWT;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    CustomerUserDetailsService customerUserDetailsService;
//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customerUserDetailsService);
//    }
//    @Bean
//    public InMemoryUserDetailsManager userDetails() {
//        return new InMemoryUserDetailsManager(
//                User.withUsername("jide")
//                        .password("{noop}password")
//                        .authorities("read")
//                        .build()
//        );
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        //provider.setUserDetailsService(customerUserDetailsService);
//        return provider;
//    }
//}
