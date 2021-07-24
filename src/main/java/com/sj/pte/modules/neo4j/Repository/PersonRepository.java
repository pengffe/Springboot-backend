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

import java.util.Collection;

import com.sj.pte.modules.neo4j.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person findByfirstName(@Param("firstName") String firstName);

    Collection<Person> findByfirstNameLike(@Param("firstName") String firstName);
}
