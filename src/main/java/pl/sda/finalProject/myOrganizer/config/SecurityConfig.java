
package pl.sda.finalProject.myOrganizer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email as principal, password as credentials, true from my_user where email=?")
                .authoritiesByUsernameQuery("select email as principal, user_role as role from my_user where email=?")
                .passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/organizer", "/organizer/register")
                .permitAll()
                .antMatchers("/organizer/users").hasRole("ADMIN")
                .antMatchers("/organizer/notes", "/organizer/tasks", "/organizer/profile")
                .hasAnyRole("USER,ADMIN").and()
                .formLogin()
                .passwordParameter("password")
                .usernameParameter("username")
                //TODO not default login address
                //.loginPage("/login")
                .loginProcessingUrl("/loginProcessing")
                .failureUrl("/login-failure")
                .defaultSuccessUrl("/organizer").and()
                //TODO logout view
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
                .exceptionHandling().accessDeniedPage("/access-denied").and()
                .httpBasic().disable();
    }
}

