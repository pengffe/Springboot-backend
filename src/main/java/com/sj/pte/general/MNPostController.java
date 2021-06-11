package com.sj.pte.general;/**
 * Created by TUTEHUB on 2021/6/4 7:01 AM.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer dph agrees with above terms.
 * Technique Support: jobyme88.com
 * Contact: noreply@fengcaoculture.com
 */



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description
 */
@RestController
@RequestMapping
public class MNPostController {

    private MNPostDao mnPostDao;
    @Autowired
    public void setMNPostDao(MNPostDao mnPostDao) {
        this.mnPostDao = mnPostDao;
    }


    @GetMapping("/posts")
    public ResponseEntity<List<MNPost>> getAllPosts(@RequestParam(required = false) String title) {
        try {
            List<MNPost> posts = new ArrayList<MNPost>();

            if (title == null)
                mnPostDao.findAll().forEach(posts::add);
            else
                mnPostDao.findByTitle(title).forEach(posts::add);

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/posts/{id}")
    public ResponseEntity<MNPost> getPostById(@PathVariable("id") Long id) {
        Optional<MNPost> post = Optional.ofNullable(mnPostDao.findById(id));

        if (post.isPresent()) {
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<MNPost> createPost(@RequestBody MNPost post) {
        try {
            MNPost _post = mnPostDao.save(post);
            return new ResponseEntity<>(_post, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<MNPost> updatePostById(@PathVariable("id") Long id, @RequestBody MNPost post) {
        Optional<MNPost> _post = Optional.ofNullable(mnPostDao.findById(id));

        if (_post.isPresent()) {
            try {
                mnPostDao.updateById(id, post);
                return new ResponseEntity<>(_post.get(), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        try {
            mnPostDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/posts/published")
    public ResponseEntity<List<MNPost>> findByPublished() {
        try {
            List<MNPost> posts = mnPostDao.findByPublished(true);

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
