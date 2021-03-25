package br.com.letscode.java.banco.rest.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests(authorize -> {
                authorize
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers( "/clientes").authenticated();
                //Essas opções abaixo são funcionalmente idênticas aos Method Security (@PreAuthorize)
//                    .antMatchers(HttpMethod.GET, "/clientes/page").hasRole("PLAYER")
//                    .mvcMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN");
            })
            .authorizeRequests()
            .anyRequest().denyAll()
            .and()
            .formLogin().and()
            .httpBasic();

        //h2 console config
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}admin123")
            .roles("ADMIN")
            .and()
            .withUser("player")
            .password("{noop}player123")
            .roles("PLAYER");
    }

}
