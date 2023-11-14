package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends CrudRepository <Song, Integer> {

    Song findByDeezer_id(int deezerId);
}