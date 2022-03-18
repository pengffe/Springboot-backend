package com.pengffe.pte.modules.neo4j.Controller;

import com.pengffe.pte.modules.neo4j.Repository.UserRepository;
import com.pengffe.pte.modules.neo4j.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/user/init")
    public void saveUsers(){
        System.out.println("neo4j: init");

        /**
         * 创建三个用户
         */
        User phil = new User();
        phil.setId(1L);
        phil.setName("phil");
        userRepository.save(phil);

        User bbc = new User();
        bbc.setId(2L);
        bbc.setName("bbc");
        userRepository.save(bbc);

        User feng = new User();
        feng.setId(3L);
        feng.setName("feng");
        userRepository.save(feng);

        phil.follow(bbc);
        userRepository.save(phil);

        feng.follow(bbc);
        userRepository.save(feng);
    }

    @PutMapping("/user/{id}")
    public void addFriend(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        user.get().follow(new User(4L, "peng"));
//        user.get().follow(userRepository.findById(3L).get());
        userRepository.save(user.get());
    }
}
