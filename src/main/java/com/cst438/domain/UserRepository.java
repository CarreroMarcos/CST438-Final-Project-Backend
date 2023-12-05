package com.cst438.domain;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends CrudRepository<User, Integer>{


	User findByAlias(String alias);
	User findByEmail(String email);

}
