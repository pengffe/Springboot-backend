package com.sj.pte.modules.neo4j.Repository;

import com.sj.pte.modules.neo4j.entity.MNWord;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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

    /**
     * CREATE
     */

    /**
     * REQUEST
     */
    @Query("Match (p:MNWord{title:$title})-[*]->(s:MNWord) return s ")
    List<MNWord> findChildList(String title);

    MNWord findMNWordByTitle(String title);

    // 查找关系
    @Query("Match (x:MNWord{title:$start})-[re:derive]->(y:MNWord{title:$end}) return re")
    Set<MNWord> findRelation(String start, String end);

    //查询某个节点的直属父节点
    @Query("Match (p:MNWord)-[*]->(s:MNWord{title:$title}) return p limit 1")
    MNWord findParent(String title);

    /**
     * 查询某个节点的所有父节点
     */
    @Query("Match (p:MNWord)-[*]->(s:MNWord{title:$title}) return p")
    List<MNWord> findParentList(String title);

    @Query("Match (p:MNWord)-[*]->(s:MNWord{id:$id}) return p")
    List<MNWord> findParentListById(@Param("id") Long id);

    /**
     * UPDATE
     */
    @Query("match(x:MNWord{title:$title}) set x.title=$newTitle return x")
    MNWord updateWordByTitle(String title, String newTitle);

    /**
     * DELETE
     */
    // delete relation from start to end
    @Query("Match (x:MNWord{title:$start})-[re:derive]->(y:MNWord{title:$end}) delete re")
    void deleteRelationTo(String start, String end);

    // delete relation as well as node
    @Query("Match (x:MNWord{title:$start})-[re:derive]->(y:MNWord{title:$end}) delete x,re,y")
    void deleteWordAndRelation(String start, String end);

}
