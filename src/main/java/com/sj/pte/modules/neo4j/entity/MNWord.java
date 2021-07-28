package com.sj.pte.modules.neo4j.entity;/**
 * Created by TUTEHUB on 2021-07-26 15:09.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @descrption
 */

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MNWord {

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String soundMark;
    private String explanation;
    private List<String> examples;


    /**
     * this word is a origin from which others are derived
     */
    @Relationship(type = "derive", direction = Relationship.Direction.OUTGOING)
    private Set<MNWord> derive;

    public void derive(MNWord mnWord) {
        if (derive == null) {
            derive = new HashSet<>();
        }
        derive.add(mnWord);
    }

    public MNWord(MNWord mnWord){
        this.id = mnWord.id;
        this.title = mnWord.title;
        this.soundMark = mnWord.soundMark;
        this.explanation = mnWord.explanation;
        this.examples = mnWord.examples;
        this.derive = mnWord.derive;
    }

    public MNWord(String title){
        this.title = title;
    }
}
