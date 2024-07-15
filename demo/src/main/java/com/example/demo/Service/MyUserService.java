package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.MyUserRequest;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.MyUser;
import com.example.demo.Exception.MyUserNotFoundException;
import com.example.demo.Mapper.MyUserDtoMapper;
import com.example.demo.Repository.MyUserRepository;

@Service
public class MyUserService {
    private final MyUserRepository myUserRepository;
    private final MyUserDtoMapper myUserDtoMapper;

    public MyUserService(MyUserRepository myUserRepository, MyUserDtoMapper myUserDtoMapper) {
        this.myUserRepository = myUserRepository;
        this.myUserDtoMapper = myUserDtoMapper;
    }

    public List<MyUserRequest> getAllUsers() {
        return myUserRepository.findAll()
            .stream()
            .map(myUserDtoMapper)
            .collect(Collectors.toList());
    }

    public MyUserRequest getUserById(Long id) {
        return myUserRepository.findById(id)
            .map(myUserDtoMapper)
            .orElseThrow(() -> new MyUserNotFoundException("User by Id: " + id + " was not found."));
    }

    public boolean MyUserExist(MyUserRequest myUserRequest) {
        String email = myUserRequest.getEmail();

        Optional<MyUserRequest> exist = myUserRepository.findMyUserByEmail(email)
            .map(myUserDtoMapper);

        if (exist.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    // This should add the User based on their email
    // Need to learn spring boot security requests.
    public void registerUser(MyUserRequest myUserRequest) {
        String email = myUserRequest.getEmail();


        boolean exist = MyUserExist(myUserRequest);

        if (exist) {
            throw new MyUserNotFoundException("User by email: " +  email + " already exist.");
        }

        MyUser myUser = new MyUser(
            myUserRequest.getFname(), 
            myUserRequest.getLname(), 
            myUserRequest.getEmail(), 
            myUserRequest.getPassword(), 
            myUserRequest.getDob(), 
            new ArrayList<Account>()
        );

        myUserRepository.save(myUser);
    }

    public void deleteUserById(Long id) {
        boolean exists = myUserRepository.existsById(id);

        if (exists) {
            myUserRepository.deleteById(id);
        } else {
            throw new MyUserNotFoundException("User by id: " + id + " was not found.");
        }
    }
}
