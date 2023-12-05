package com.cst438.controller;


import com.cst438.domain.User;
import com.cst438.domain.UserRepository;

import com.cst438.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RestController
@CrossOrigin 
public class AdminController {
	
    @Autowired
	private UserRepository userRepository;
    
    @GetMapping("/users")
    public UserDTO[] getUsers() { 
		Iterable<User> list = userRepository.findAll();
		ArrayList<UserDTO> alist = new ArrayList<>();
		for (User u : list) {
			UserDTO udto = new UserDTO(u.getAlias(), u.getEmail(), u.getPassword(), u.getRole());
			alist.add(udto);
		}
		return alist.toArray(new UserDTO[alist.size()]);    	
    }

}
