package com.bajidan.cafe_ms.JWT;


import com.bajidan.cafe_ms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private com.bajidan.cafe_ms.model.User user;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadByUserName {}", username);
        user = userRepository.findByEmail(username);

        if(!Objects.isNull(user)) {
            return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }

    }

    public com.bajidan.cafe_ms.model.User getUser() {
        return user;
    }
}
