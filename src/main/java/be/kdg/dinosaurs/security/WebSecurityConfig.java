package be.kdg.dinosaurs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("!test")
public class WebSecurityConfig {


    @Bean // allows you to create instances for autowiring for which you don't have source code
    // from framework point of view
    // permit by role - implement!!
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.httpBasic()
                // .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests( // most important block
                        auths -> auths
                                .regexMatchers("/(h2-console.*|dinosaurs|digsites|digsites/digsite\\?.+|dinosaurs/dinosaur\\?.+)") // permit all requests to these urls
                                .permitAll()
                                .antMatchers(HttpMethod.GET, "/api/**/*")// everyone should be allowed to see information
                                .permitAll()
                                .antMatchers(HttpMethod.POST, "/api/**/*") // users, moderators, and admins should be allowed to make new entities, including favorites
                                .authenticated()
                                .antMatchers(HttpMethod.PATCH, "/api/**/*") // moderators and admins should be allowed to edit entities
                                .hasAnyAuthority("ROLE_MODERATOR", "ROLE_ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/api/dinosaurs/*/favorites") // users should be allowed to unfavorite
                                .authenticated()
                                .antMatchers(HttpMethod.DELETE, "/api/**/*") // all other deletes should only be allowed for admins and no one else
                                .hasAuthority("ROLE_ADMIN")
                                .antMatchers("/", "/register")// allow access to homepages and register.html pages
                                .permitAll()
                                .antMatchers("/h2-console/**")
                                .permitAll()
                                .antMatchers("/javascript/**", "/css/**", "/webjars/**")
                                .permitAll()
                                .anyRequest() // anything else requires authentication
                                .authenticated()) // posts will be caught here and given 403
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .permitAll();
        return http.build();
    }

    @Bean
    // don't autowire this yourself - security says if you want to do customization, please provide
    // a bean of this type
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/javascript/**", "/css/**", "/webjars/**"); // favicons, css, javascript don't require security
        // globally speaking ignore anything that starts with this ^ this ^ , or this ^
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
