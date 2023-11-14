package com.cst438.domain;


import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer>{


	User findByAlias(String alias);
	User findByEmail(String email);
	User findByUser_id(int id);

}
