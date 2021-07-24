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

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

@Node
@Data
public class User {
    @Id
    private long id;

    private String name;

    @Relationship(type = "FRIEND_OUT", direction = Direction.OUTGOING)
    private List<User> follow = new ArrayList<>();

    @Relationship(type = "FRIEND_IN", direction = Direction.INCOMING)
    private List<User> followed = new ArrayList<>();
}
