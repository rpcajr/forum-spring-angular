package br.com.couto.forum.config.security;

import br.com.couto.forum.service.security.AuthService;
import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;

    //configurações de autenticação - contorle de acesso, login e tals...
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());

    }

    //Configurações de autorização - liberar url e tals...
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/topicos").permitAll()
                .antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    //Configurações de recursos estaticos - css, js, imagens e etc... frontend integrado
    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
