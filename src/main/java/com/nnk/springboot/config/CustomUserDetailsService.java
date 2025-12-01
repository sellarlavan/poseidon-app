package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service permettant de charger un utilisateur depuis la base de données pour les besoins de Spring Security.
 *
 * <p>
 * Cette implémentation utilise UserRepository pour retrouver un utilisateur à partir de son username, puis construit un objet
 * UserDetails utilisé par Spring Security pour l’authentification.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username le nom saisi lors du login
     * @return un objet UserDetails contenant username le mot de passe haché et le rôle
     * @throws UsernameNotFoundException si aucun utilisateur ne correspond
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
