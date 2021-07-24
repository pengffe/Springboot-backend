package com.sj.pte.modules.neo4j.Controller;/**
 * Created by TUTEHUB on 2021-07-23 16:14.
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

import com.sj.pte.modules.neo4j.Repository.MovieRepository;
import com.sj.pte.modules.neo4j.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;


    @GetMapping("/all")
    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    @GetMapping("/findByReveueGT")
    public Iterable<Movie> findByRevenueGT(@RequestParam Integer revenue) {
        return movieRepository.findByRevenueGreaterThan(revenue);
    }

}
