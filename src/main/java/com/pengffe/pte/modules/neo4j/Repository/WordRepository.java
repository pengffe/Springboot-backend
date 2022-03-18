package com.pengffe.pte.modules.neo4j.Repository;

import com.pengffe.pte.modules.neo4j.entity.Word;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

//
@Repository
public interface WordRepository extends Neo4jRepository<Word, Long> {

    /**
     * CREATE
     */

    /**
     * REQUEST
     */
    @Query("Match (p:Word{title:$title})-[*]->(s:Word) return s ")
    List<Word> findChildList(String title);

    Word findMNWordByTitle(String title);

    // 查找关系
    @Query("Match (x:Word{title:$start})-[re:derive]->(y:Word{title:$end}) return re")
    Set<Word> findRelation(String start, String end);

    //查询某个节点的直属父节点
    @Query("Match (p:Word)-[*]->(s:Word{title:$title}) return p limit 1")
    Word findParent(String title);

    /**
     * 查询某个节点的所有父节点
     */
    @Query("Match (p:Word)-[*]->(s:Word{title:$title}) return p")
    List<Word> findParentList(String title);

    @Query("Match (p:Word)-[*]->(s:Word{id:$id}) return p")
    List<Word> findParentListById(@Param("id") Long id);

    /**
     * UPDATE
     */
    @Query("match(x:Word{title:$title}) set x.title=$newTitle return x")
    Word updateWordByTitle(String title, String newTitle);

    /**
     * DELETE
     */
    // delete relation from start to end
    @Query("Match (x:Word{title:$start})-[re:derive]->(y:Word{title:$end}) delete re")
    void deleteRelationTo(String start, String end);

    // delete relation as well as node
    @Query("Match (x:Word{title:$start})-[re:derive]->(y:Word{title:$end}) delete re,y")
    void deleteWordAndRelation(String start, String end);

    @Query("Match (x:Word{title:$title}) delete x")
    void deleteWordByTitle(String title);
}
