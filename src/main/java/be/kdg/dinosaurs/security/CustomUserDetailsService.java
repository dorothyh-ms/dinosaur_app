package be.kdg.dinosaurs.security;

import be.kdg.dinosaurs.service.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("CustomUserDetailsService is running loadUserByUsername");
        var user = userService.getUserByName(username);
        if (user != null) {
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
            LOGGER.debug("CustomUserDetailsService is returning user {}", user.getUsername());
            return new CustomUserDetails(user.getUsername(), user.getPassword(), authorities, user.getId());
        }
        LOGGER.debug("CustomUserDetailsService could not find user");
        throw new UsernameNotFoundException("User '" + username + "' doesn't exist");
    }
}
