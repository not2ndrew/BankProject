package com.example.demo.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.MyUserRequest;
import com.example.demo.Entity.MyUser;

// This converts MyUser to MyUserDto

@Service
public class MyUserDtoMapper implements Function<MyUser, MyUserRequest>{
    @Override
    public MyUserRequest apply(MyUser myUser) {
        return new MyUserRequest(
            myUser.getFname(),
            myUser.getLname(),
            myUser.getEmail(),
            myUser.getPassword(),
            myUser.getDob()
        );
    }
}
