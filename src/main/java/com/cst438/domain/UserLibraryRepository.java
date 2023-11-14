package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserLibraryRepository extends CrudRepository <UserLibrary, Integer> {
	
//	UserLibrary findByUserId(int user_id);
	
	public List<UserLibrary> findUserLibrary(@Param("user_id") int user_id);
	
	UserLibrary findByUserId(@Param("userId") int userId);
	
	UserLibrary findByUserIdAndLibraryId(@Param("user_id") int user_id, @Param("library_id") int library_id);
	
}
