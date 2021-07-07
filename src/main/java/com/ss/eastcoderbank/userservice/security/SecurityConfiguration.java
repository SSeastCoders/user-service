package com.ss.eastcoderbank.userservice.security;

import com.ss.eastcoderbank.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;
    //private BasicAuthenticationEntryPoint authenticationEntryPoint;

    //public SecurityConfiguration(UserRepo userRepo, UserPrincipalDetailsService userPrincipalDetailsService, BasicAuthenticationEntryPoint authenticationEntryPoint) {
    public SecurityConfiguration(UserRepository userRepository, UserPrincipalDetailsService userPrincipalDetailsService) {
        this.userRepository = userRepository;
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        //this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManBuild) throws Exception {
        //authManBuild.authenticationProvider(authenticationProvider());
        authManBuild.userDetailsService(userPrincipalDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // add JWT Filters (1. authentication 2. authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository))
                .authorizeRequests()

                //SPECIFY ACCESS
                .antMatchers("*").permitAll();
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
