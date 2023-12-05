package com.cst438.controller;

import com.cst438.domain.User;
import com.cst438.domain.UserRepository;

import com.cst438.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
@CrossOrigin 
public class SignUpController {
	
    @Autowired
	private UserRepository userRepository;
    
    @PostMapping("/signup")
    @Transactional
    public void createUser(@RequestBody UserDTO newUser) {
    	
    	User u = userRepository.findByEmail(newUser.email());
    	
    	if(u == null) {
    		u = new User();
    		
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    		String encrpted_password = encoder.encode(newUser.password());
    		
        	u.setAlias(newUser.alias());
        	u.setEmail(newUser.email());
        	u.setPassword(encrpted_password);
        	u.setRole(newUser.role());
        	
        	userRepository.save(u);
    	}else {
    		throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "user with email exists" );
    	}
    	
    }
    
    
}
