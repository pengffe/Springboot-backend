//package com.sj.pte.modules.neo4j.entity;/**
// * Created by TUTEHUB on 2021-07-23 13:36.
// * Copyright © 2021 TUTEHUB. All rights reserved.
// * ------------------------
// * Non-disclosure Terms
// * -------------------------
// * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
// * Developer fengpeng agrees with above terms.
// * Technique Support: jobyme88.com
// */
//
//import org.springframework.data.neo4j.core.schema.GeneratedValue;
//import org.springframework.data.neo4j.core.schema.Id;
//import org.springframework.data.neo4j.core.schema.RelationshipProperties;
//import org.springframework.data.neo4j.core.schema.TargetNode;
//
//import java.util.List;
//
///**
// * @descrption
// */
//
//@RelationshipProperties
//public class Roles {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    private final List<String> roles;
//
//    @TargetNode
//    private final PersonEntity person;
//
//    public Roles(PersonEntity person, List<String> roles) {
//        this.person = person;
//        this.roles = roles;
//    }
//
//    public List<String> getRoles() {
//        return roles;
//    }
//}
