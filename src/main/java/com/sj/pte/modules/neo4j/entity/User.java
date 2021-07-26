package com.sj.pte.modules.neo4j.entity;


/**
 * Created by TUTEHUB on 2021-07-22 15:46.
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

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.ArrayList;
import java.util.List;

@Node
@Data
@NoArgsConstructor
public class User {
    @Id
    private long id;

    private String name;



    public User(long id, String name){
        this.id = id;
        this.name = name;
    }

    @Relationship(type = "FOLLOW", direction = Direction.OUTGOING)
    private List<User> follow = new ArrayList<>();

    public void follow(User User) {
        follow.add(User);
    }

//    @Relationship(type = "FRIEND_IN", direction = Direction.INCOMING)
//    private List<User> followed = new ArrayList<>();
}
