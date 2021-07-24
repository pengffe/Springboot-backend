package com.sj.pte.modules.neo4j.entity;/**
 * Created by TUTEHUB on 2021-07-23 16:11.
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

import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;


@lombok.Data
@NoArgsConstructor
@NodeEntity
public class Movie{
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String category;
    private int revenue;

    public Movie (String title, String category, int revenue) {
        this.title = title;
        this.category = category;
        this.revenue = revenue;
    }
}
