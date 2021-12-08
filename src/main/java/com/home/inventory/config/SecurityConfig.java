package com.home.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.home.inventory.services.MyUserDetailsService;

/**
 * Basic Authentication used with Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * For manage authentication rules.
     */
    @Autowired
    protected void configureGlobal(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * Web app security configuration.
     */
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/login", "/categories/**",
                            "/sous-categories/**", "/fournisseurs/**",
                            "/produits/**")
                    .authenticated().antMatchers("/user/**")
                    .hasAnyAuthority("ADMIN").and().csrf().disable().httpBasic()
                    .and().formLogin().defaultSuccessUrl("/categories/list")
                    .and().logout().logoutUrl("/app-logout")
                    .logoutSuccessUrl("/");
        }
    }

}
