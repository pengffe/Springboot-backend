package com.sj.pte.modules.neo4j.Repository;/**
 * Created by TUTEHUB on 2021-07-23 16:15.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

/**
 * @descrption
 */

import com.sj.pte.modules.neo4j.entity.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MovieRepository extends Neo4jRepository<Movie, Long> {

//    Movie findByTitle(@Param("title") String title);

    Collection<Movie> findByRevenueGreaterThan(int revenue);

}
