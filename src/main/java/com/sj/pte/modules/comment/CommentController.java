package com.sj.pte.modules.comment;

/**
 * Created by TUTEHUB on 2021-05-31 16:46.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author pengffe  Email: pengffe@gmail.com
 * @descrption handle user's comment to each question
 * @date 26-05-2021
 */

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    MNCommentDao mnCommentDao;

    @PostMapping(value = "")
    public MNComment addComment(@RequestBody MNComment comment){
        System.out.println("comment: " + comment.toString());
        return mnCommentDao.addComment(comment);
    }

    @GetMapping("/{questionId}")
    public List<MNComment> findAllCommentsByQuestionId(@PathVariable String questionId){
        return mnCommentDao.findAllByQuestionId(questionId);
    }

    @DeleteMapping("/{id}")
    public DeleteResult deleteCommentById(@PathVariable Long id){
        return mnCommentDao.deleteById(id);
    }

//    @PutMapping("/{questionId}/{message}")
//    public UpdateResult updateMessage(@PathVariable String questionId, @PathVariable String message){
//        System.out.println("comment: " + message);
//        return mnCommentDao.updateMessage(questionId, message);
//    }
}
