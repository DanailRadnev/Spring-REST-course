package course.spring.rentacar.config;

import course.spring.rentacar.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic(withDefaults())
                .authorizeRequests()

                .antMatchers(HttpMethod.GET,"/rentacar/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/rentacar/user/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/rentacar/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/rentacar/user/**").hasAnyRole("ADMIN", "USER")


                .antMatchers(HttpMethod.GET,"/rentacar/car/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/rentacar/car/dirty/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/rentacar/car/wash/**").hasAnyRole("EMPLOYEE")
                .antMatchers(HttpMethod.PUT,"/rentacar/car/broke/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/rentacar/car/repair/**").hasAnyRole("EMPLOYEE")
                .antMatchers(HttpMethod.PUT,"/rentacar/car/rent/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT,"/rentacar/car/return/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.DELETE,"/rentacar/car/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/rentacar/car/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/rentacar/car/**").hasAnyRole("ADMIN")


                .antMatchers("/rentacar/admin/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/rentacar/user/**").hasAnyRole("ADMIN");
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return username -> userService.getUserByUsername(username);
    }
}
