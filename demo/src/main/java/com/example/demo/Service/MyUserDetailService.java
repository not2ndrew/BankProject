package com.example.demo.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.MyUserRequest;
import com.example.demo.Mapper.MyUserDtoMapper;
import com.example.demo.Repository.MyUserRepository;
import lombok.var;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyUserDtoMapper myUserDtoMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUserRequest> userDto = myUserRepository.findMyUserByEmail(username).map(myUserDtoMapper);

        if (userDto.isPresent()) {
            var userObj = userDto.get();

            return User.builder()
                .username(userObj.getEmail())
                .password(userObj.getPassword())
                .build();
        } else {
            throw new UsernameNotFoundException("Username: " + username + "was not found.");
        }
    }

}
