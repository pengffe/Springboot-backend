package com.sj.pte.modules.neo4j.Controller;/**
 * Created by TUTEHUB on 2021-07-23 14:28.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.neo4j.Repository.UserRepository;
import com.sj.pte.modules.neo4j.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @descrption
 */

@RestController
@RequestMapping("neo4j")
public class NeoUserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("/user/{username}")
    public User saveUsers(@PathVariable String username){
        System.out.println("neo4j");
//        User user2 = new User();
//        user2.setName("bbc");

        User user = new User();
        user.setName(username);
//        user.getFollow().add(user2);
        return userRepository.save(user);

    }

//    @PutMapping("/user/{username}")
//    public String addFriend(@PathVariable String username){
//        userRepository.
//    }
}
