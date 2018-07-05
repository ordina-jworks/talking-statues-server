package be.ordina.talkingstatues.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecuritySuccessHandler successHandler ;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Entering Security Setup...");
        http
          //      .exceptionHandling().accessDeniedHandler(new SecurityAccessDeniedHandler())
          //      .and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/images/**").permitAll()
             //   .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .oauth2Login()
   //             .loginPage("/auth/denied").permitAll()
                .successHandler(successHandler)
                .and().logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .and()
                .rememberMe()
                .rememberMeParameter("rememberMe")
               // .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60 * 60 * 24 * 7) // 1 week validity
        ;
    }

    @Bean @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.OPTIONS.toString(),
                HttpMethod.DELETE.toString(),
                HttpMethod.PUT.toString(),
                HttpMethod.GET.toString(),
                HttpMethod.POST.toString()));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /*
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
    */
}
