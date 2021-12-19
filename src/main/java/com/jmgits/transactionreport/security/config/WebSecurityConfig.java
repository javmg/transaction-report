package com.jmgits.transactionreport.security.config;

import com.jmgits.transactionreport.security.filter.TokenAuthenticationFilter;
import com.jmgits.transactionreport.security.resolver.TokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenHandler tokenHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().disable()
                .csrf().disable()

                .authorizeRequests()

                .antMatchers("/").permitAll()

                .antMatchers("/api/**").authenticated()

                .and()

                .addFilterBefore(new TokenAuthenticationFilter(tokenHandler), UsernamePasswordAuthenticationFilter.class)

                .httpBasic().disable() //

                .sessionManagement().sessionCreationPolicy(STATELESS);
    }
}
