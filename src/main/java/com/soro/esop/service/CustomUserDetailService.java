package com.soro.esop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soro.esop.entity.Role;
import com.soro.esop.entity.User;
import com.soro.esop.entity.UserRole;
import com.soro.esop.repository.RoleRepository;
import com.soro.esop.repository.UserRepository;
import com.soro.esop.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        List<Role> roles = roleRepository.findAllById(userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));

        List<GrantedAuthority> authorities = roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getEnabled(),
            true, 
            true, 
            true,
            authorities);
    }
    
}
