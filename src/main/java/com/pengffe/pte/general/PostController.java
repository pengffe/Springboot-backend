package com.pengffe.pte.general;

import org.springframework.beans.factory.annotation.Autowired;
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
public class PostController {

    private PostDao postDao;
    @Autowired
    public void setMNPostDao(PostDao postDao) {
        this.postDao = postDao;
    }


    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String title) {
        try {
            List<Post> posts = new ArrayList<Post>();

            if (title == null)
                postDao.findAll().forEach(posts::add);
            else
                postDao.findByTitle(title).forEach(posts::add);

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        Optional<Post> post = Optional.ofNullable(postDao.findById(id));

        if (post.isPresent()) {
            return new ResponseEntity<>(post.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            Post _post = postDao.save(post);
            return new ResponseEntity<>(_post, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePostById(@PathVariable("id") Long id, @RequestBody Post post) {
        Optional<Post> _post = Optional.ofNullable(postDao.findById(id));

        if (_post.isPresent()) {
            try {
                postDao.updateById(id, post);
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
            postDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/posts/published")
    public ResponseEntity<List<Post>> findByPublished() {
        try {
            List<Post> posts = postDao.findByPublished(true);

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
