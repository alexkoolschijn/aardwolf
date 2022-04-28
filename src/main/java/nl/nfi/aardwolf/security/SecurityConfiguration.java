/*
 * Copyright (c) 2021 Netherlands Forensic Institute
 * All rights reserved.
 */

package nl.nfi.aardwolf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity 
@Configuration 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";
    private static final String LOGOUT_URL = "/logout";

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF for Vaadin requests, as Vaadin already does CSRF
        http.csrf().ignoringRequestMatchers(SecurityUtils::isFrameworkInternalRequest).ignoringAntMatchers("/login");
        
        //Add the custom request cache so we can restore the original URL after being redirected to the login page
        http.requestCache().requestCache(new CustomRequestCache());
        
        // Configure the authorization requirements
        http.authorizeRequests() 
            // Vaadin framework requests are always allowed
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()  
            // any other request must be authenticated 
            .anyRequest().authenticated();

        // Configure the login page (no authentication required)
        http.formLogin()  
            .loginPage(LOGIN_URL).permitAll()
            .loginProcessingUrl(LOGIN_PROCESSING_URL)  
            .failureUrl(LOGIN_FAILURE_URL);
        
        // Configure the logout URL to have no authentication required, and set things up to go back to the 
        // login page after logging out
        http.logout()
            .permitAll()
            .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
            .logoutSuccessUrl(LOGOUT_SUCCESS_URL); 
    }
    
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // TODO: AARDWOLF-28 - remove hard-coded users and un-encoded passwords
        final UserDetails user = User.withUsername("user").password("{noop}user!").roles("USER").build();
        final UserDetails admin = User.withUsername("admin").password("{noop}admin!").roles("ADMIN").build();
        final UserDetails root = User.withUsername("root").password("{noop}root!").roles("ROOT").build();
        return new InMemoryUserDetailsManager(user, admin, root);
    }
    
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest",
            "/sw.js",
            "/offline.html",
            "/icons/**",
            "/images/**",
            "/styles/**",
            "/h2-console/**",
            "/shared/**",
            "/shared-styles.css",
            "/forgotpassword",
            // todo: not sure if this is the proper solution to avoid routing to this cache file.
            "/sw-runtime-resources-precache.js");
    }
}