package com.spring.oauth2.service;

import com.spring.oauth2.entity.User;
import com.spring.oauth2.entity.UserRole;
import com.spring.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        String lowercaseLogin = login.toLowerCase();

        User userFromDatabase;
            userFromDatabase = userRepository.findByUsernameCaseInsensitive(lowercaseLogin);

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRole userRole : userFromDatabase.getUserRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.getRole().getRoleName());
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(), userFromDatabase.getPassword(), grantedAuthorities);
    }
}
