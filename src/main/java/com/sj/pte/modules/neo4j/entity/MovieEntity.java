//package com.sj.pte.modules.neo4j.entity;/**
// * Created by TUTEHUB on 2021-07-23 13:35.
// * Copyright © 2021 TUTEHUB. All rights reserved.
// * ------------------------
// * Non-disclosure Terms
// * -------------------------
// * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
// * Developer fengpeng agrees with above terms.
// * Technique Support: jobyme88.com
// */
//
///**
// * @descrption
// */
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.data.neo4j.core.schema.Id;
//import org.springframework.data.neo4j.core.schema.Node;
//import org.springframework.data.neo4j.core.schema.Property;
//import org.springframework.data.neo4j.core.schema.Relationship;
//import org.springframework.data.neo4j.core.schema.Relationship.Direction;
//
//@Node("Movie")
//public class MovieEntity {
//
//    @Id
//    private final String title;
//
//    @Property("tagline")
//    private final String description;
//
//    @Relationship(type = "ACTED_IN", direction = Direction.INCOMING)
//    private List<Roles> actorsAndRoles;
//
//    @Relationship(type = "DIRECTED", direction = Direction.INCOMING) private List<PersonEntity> directors = new ArrayList<>();
//
//    public MovieEntity(String title, String description) {
//        this.title = title;
//        this.description = description;
//    }
//
//    // Getters omitted for brevity
//}
