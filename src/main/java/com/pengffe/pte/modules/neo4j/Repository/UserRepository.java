package com.pengffe.pte.modules.neo4j.Repository;

import com.pengffe.pte.modules.neo4j.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @descrption
 */

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

}
