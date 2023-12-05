package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserLibraryRepository extends CrudRepository <UserLibrary, Integer> {

    @Query("select l from UserLibrary l where l.user.id = :userId")
    List<UserLibrary> findByUserId(@Param("userId") int userId);
	
	@Query("select l from UserLibrary l where l.user.id=:userId and l.song.deezer_id=:deezer_id")
	UserLibrary findByUserIdAndSong(@Param("userId") int userId, @Param("deezer_id") long deezer_id);
	
    @Query("select l from UserLibrary l where l.library_id = :library_id")
    UserLibrary findByLibraryId(@Param("library_id") int library_id);
}
