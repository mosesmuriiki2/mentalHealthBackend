package com.example.mentalHealthApp.service;

import com.example.mentalHealthApp.entity.Roles;
import com.example.mentalHealthApp.entity.Users;
import com.example.mentalHealthApp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository= userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Users users = userRepository.findByEmail(email);
        if (users == null) {
            return new org.springframework.security.core.userdetails.User(users.getEmail(),
                    users.getPassword(),
                    mapRolesToAuthorities(users.getRoles()));
        }else {
            throw  new UsernameNotFoundException("Invalid username or password");
        }
    }
    private Collection <? extends GrantedAuthority> mapRolesToAuthorities(Collection <Roles> roles){
        Collection <? extends GrantedAuthority> mapRoles = roles.stream()
                .map(roles1 -> new SimpleGrantedAuthority(roles1.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}
