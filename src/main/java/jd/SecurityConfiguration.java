package jd;

import jd.security.PrincipalAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by eduardom on 10/15/16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Configuration
    public static class LoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{

        @Autowired
        private PrincipalAuthenticationProvider principalAuthenticationProvider;

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth){
            auth.authenticationProvider(principalAuthenticationProvider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .antMatchers(
                                "/**"
                        ).permitAll()
                        .antMatchers(
                                "../static/partials/dashboard/**",
                                "../static/partials/generaldef/**",
                                "../static/partials/users/manage/**"
                        ).hasRole("USER")
                    .anyRequest()
                        .authenticated()
                    .and()
                        .formLogin()
                        .loginPage("/loginpage")
                        .loginProcessingUrl("/loginpage")/*
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/loginpage")*/
                        .usernameParameter("username")
                        .passwordParameter("password")
                    .permitAll()
                    .and()
                        .csrf().disable()
                    /*.and()*/
                    .logout()
                    .invalidateHttpSession(false)
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID")
                    .permitAll();

        }
    }

}
