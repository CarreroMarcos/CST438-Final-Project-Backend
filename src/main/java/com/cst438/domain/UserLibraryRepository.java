package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserLibraryRepository extends CrudRepository <UserLibrary, Integer> {

    @Query("select l from UserLibrary l where l.user.id = :userId")
    List<UserLibrary> findByUserId(@Param("userId") int userId);
	
//	@Query("select l from user_library l where l.user_id=:user_id and l.library_id=:library_id")
//	UserLibrary findByUserIdAndLibraryId(@Param("user_id") int user_id, @Param("library_id") int library_id);
	
}
