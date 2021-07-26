package com.sj.pte.modules.neo4j.Repository;

import com.sj.pte.modules.neo4j.entity.MNWord;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by TUTEHUB on 2021-07-26 15:10.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */
//
@Repository
public interface WordRepository extends Neo4jRepository<MNWord, Long> {

    @Query("Match (p:MNWord{mId:{mId}})-[*]->(s:MNWord) return s ")
    List<MNWord> findChildList(Long mId);
}
