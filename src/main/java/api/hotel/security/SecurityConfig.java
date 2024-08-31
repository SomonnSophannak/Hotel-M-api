package api.hotel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    /*@Bean
    InMemoryUserDetailsManager configureUserSecurity() {
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("USER","ADMIN")
                .build();
        UserDetails editor = User
                .withUsername("manager")
                .password(passwordEncoder.encode("manager123"))
                .roles("USER","MANAGER")
                .build();
        UserDetails subscriber = User
                .withUsername("customer")
                .password(passwordEncoder.encode("customer123"))
                .roles("USER","CUSTOMER")
                .build();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(admin);
        manager.createUser(editor);
        manager.createUser(subscriber);

        return manager;
    }*/

    @Bean
    DaoAuthenticationProvider configDaoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception {

        // Endpoint security config
        http.authorizeHttpRequests(endpoint -> endpoint

                .requestMatchers("/api/v1/auth/**").permitAll()

                .requestMatchers(HttpMethod.POST,"/api/v1/rooms/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.GET,"/api/v1/rooms/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.PUT,"/api/v1/rooms/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.PATCH,"/api/v1/rooms/**").hasAnyRole("USER")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/rooms/**").hasAnyRole("ADMIN")

                .requestMatchers(HttpMethod.POST,"/api/v1/room-types/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/room-types/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/room-types/**").hasRole("USER")
                .requestMatchers(HttpMethod.PUT,"/api/v1/room-types/**").hasAnyRole("MANAGER","ADMIN")
                .requestMatchers(HttpMethod.PATCH,"/api/v1/room-types/**").hasAnyRole("MANAGER","ADMIN")

                .anyRequest().authenticated());

        // Security Mechanism (http Basic Auth)
        // Http Basic Auth (Username and Password)
        http.httpBasic(Customizer.withDefaults());

        // Disable CSRF Token
        http.csrf(toke -> toke.disable());

        // Make API to Stateless Session
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
