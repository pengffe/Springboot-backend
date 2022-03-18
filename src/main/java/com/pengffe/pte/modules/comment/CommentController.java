package com.pengffe.pte.modules.comment;

import com.mongodb.client.result.DeleteResult;
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
    CommentDao commentDao;

    @PostMapping(value = "")
    public Comment addComment(@RequestBody Comment comment){
        System.out.println("comment: " + comment.toString());
        return commentDao.addComment(comment);
    }

    @GetMapping("/{questionId}")
    public List<Comment> findAllCommentsByQuestionId(@PathVariable String questionId){
        return commentDao.findAllByQuestionId(questionId);
    }

    @DeleteMapping("/{id}")
    public DeleteResult deleteCommentById(@PathVariable Long id){
        return commentDao.deleteById(id);
    }

//    @PutMapping("/{questionId}/{message}")
//    public UpdateResult updateMessage(@PathVariable String questionId, @PathVariable String message){
//        System.out.println("comment: " + message);
//        return mnCommentDao.updateMessage(questionId, message);
//    }
}
