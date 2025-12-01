package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de Spring Security.
 *
 * <p>
 * Cette classe met en place :
 * <ul>
 *   <li>les règles d’accès aux différentes routes,</li>
 *   <li>l’authentification basée sur une session,</li>
 *   <li>l’utilisation de CustomUserDetailsService pour charger les utilisateurs,</li>
 *   <li>la configuration du formulaire de login et du logout.</li>
 * </ul>
 *
 * Routes :
 * <ul>
 *   <li>/css/** : accessible sans authentification</li>
 *   <li>/user/** : accessible uniquement aux utilisateurs ayant le rôle ADMIN</li>
 *   <li>Toutes les autres routes nécessitent une authentification</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configure les règles de sécurité HTTP d'authentification, d'autorisations, de login et de logout.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                        .defaultSuccessUrl("/bidList/list", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                );

        return http.build();
    }

    /**
     * Fournit une instance BCrypt pour le hachage des mots de passe.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
