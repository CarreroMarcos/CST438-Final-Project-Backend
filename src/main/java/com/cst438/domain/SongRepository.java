package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends CrudRepository <Song, Long> {

    @Query("select s from Song s where s.deezer_id = :deezerId")
    Song findByDeezerId(@Param("deezerId") long deezerId);
}