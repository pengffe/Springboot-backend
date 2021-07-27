package com.sj.pte.modules.neo4j.Controller;/**
 * Created by TUTEHUB on 2021-07-26 14:14.
 * Copyright © 2021 TUTEHUB. All rights reserved.
 * ------------------------
 * Non-disclosure Terms
 * -------------------------
 * These codes are TUTEHUB's property, as the developer, such as employee, contractor, and intern, working for TUTEHUB cannot disclose, spread, copy, preserve, sell, and do other activities without the consent of TUTEHUB.
 * Developer fengpeng agrees with above terms.
 * Technique Support: jobyme88.com
 */

import com.sj.pte.modules.neo4j.Repository.WordRepository;
import com.sj.pte.modules.neo4j.entity.MNWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @descrption
 */

@RestController
@RequestMapping("dic")
public class MNWordController {

    @Autowired
    WordRepository wordRepository;

    @PostMapping("/{word}")
    public ResponseEntity<?> saveWord(@PathVariable String word){
        MNWord mnWord = new MNWord(word);
        MNWord save = wordRepository.save(mnWord);
        return checkReturn(save);
    }

    /**
     *  create relation from start to end
     */
    @PostMapping("/{start}/{end}")
    public ResponseEntity<?> createRelation(@PathVariable String start, @PathVariable String end){
        MNWord startNode = wordRepository.findMNWordByTitle(start);
        if (null != startNode){
            MNWord endNode = wordRepository.findMNWordByTitle(end);
            if (null != endNode){
                startNode.derive(endNode);
                MNWord save = wordRepository.save(startNode);
                return checkReturn(save);
            }
            else {
                MNWord mnWord = new MNWord(end);
                startNode.derive(wordRepository.save(mnWord));
                MNWord save = wordRepository.save(startNode);
                return checkReturn(save);
            }

        }
        else return new ResponseEntity<>("FAIL TO ADD RELATION", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/{title}")
    public ResponseEntity<?> getWord(@PathVariable String title){
        MNWord mnWordByTitle = wordRepository.findMNWordByTitle(title);
        return checkReturn(mnWordByTitle);
    }

    /**
     * obtain direct parent node
     */
    @GetMapping("parent/{title}")
    public ResponseEntity<?> getParent(@PathVariable String title){
        MNWord parent = wordRepository.findParent(title);
        return checkReturn(parent);
    }

    @GetMapping("parents/{title}")
    public ResponseEntity<?> getParents(@PathVariable String title){
        List<MNWord> parentList = wordRepository.findParentList(title);
        return checkReturn(parentList);
    }

    /**
     * obtain all children nodes
     */
    @GetMapping("/children/{title}")
    public ResponseEntity<?> getDerivedWords(@PathVariable String title){
        List<MNWord> childList = wordRepository.findChildList(title);
        return checkReturn(childList);
    }

    @PutMapping("/{title}/{newTitle}")
    public ResponseEntity<?> updateWord(@PathVariable String title, @PathVariable String newTitle){
        MNWord mnWord = wordRepository.updateWordByTitle(title, newTitle);
        return checkReturn(mnWord);
    }

    @DeleteMapping("/{start}/{end}")
    public ResponseEntity<?>  deleteRelation(@PathVariable String start, @PathVariable String end){
        wordRepository.deleteRelationTo(start, end);
        Set<MNWord> relation = wordRepository.findRelation(start, end);
        return checkDelete(relation);
    }

    @DeleteMapping("node/{start}/{end}")
    public ResponseEntity<?>  deleteNodeAndRelation(@PathVariable String start, @PathVariable String end){
        wordRepository.deleteWordAndRelation(start, end);
        Set<MNWord> relation = wordRepository.findRelation(start, end);
        return checkDelete(relation);
    }

    private <T> ResponseEntity<?> checkReturn(T t){
        if (null != t){
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
        else return new ResponseEntity<>("OPRATION FAIL OR NO DATA RETURN", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private <T> ResponseEntity<?> checkDelete(Set<T> t){
        if (null == t || 0 == t.size()){
            return new ResponseEntity<>("SUCCESS TO DELETE", HttpStatus.OK);
        }
        else return new ResponseEntity<>("FAIL TO DELETE", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
