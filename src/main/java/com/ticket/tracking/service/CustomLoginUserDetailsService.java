package com.ticket.tracking.service;
import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.entity.role.Role;
import com.ticket.tracking.repository.LoginUserRepository;
import com.ticket.tracking.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomLoginUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public LoginUser findUserById(String id) {
        return userRepository.findUserById(id);
    }

    public List<LoginUser> findUsers() {
        return userRepository.findAll();
    }

    public List<LoginUser> findRDUsers() {
        return userRepository.findUserByUserRole("RD");
    }


    public void saveUser(LoginUser user) {
        user.setEmail(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setFullName(user.getFullName());
        Role userRole = roleRepository.findByRole(user.getUserRole());
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        LoginUser user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(LoginUser user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
