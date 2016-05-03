package org.jasr.dashard.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/*.json").authenticated()
                .antMatchers("**/upsert.html").authenticated()
                .antMatchers("**/view.html").authenticated()
                .antMatchers("**/list.html").authenticated()
                .antMatchers("/index.html").permitAll().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check").loginPage("/index.html").and().exceptionHandling()
                .accessDeniedPage("/index.html").and().sessionManagement().invalidSessionUrl("/index.html").and().csrf().disable()
                .logout();

    }

}
