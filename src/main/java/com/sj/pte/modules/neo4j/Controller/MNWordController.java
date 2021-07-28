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

    @PostMapping
    public ResponseEntity<?> saveVocabulary(@RequestBody MNWord word){
        MNWord dbWord = null;
        try {
            dbWord = wordRepository.findMNWordByTitle(word.getTitle());
            if (null == dbWord){
                MNWord save = wordRepository.save(word);
                return new ResponseEntity<>(save, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(dbWord, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO SAVE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{word}")
    public ResponseEntity<?> saveWord(@PathVariable String word){
        MNWord dbWord = null;
        try {
            dbWord = wordRepository.findMNWordByTitle(word);
            if (null == dbWord){
                MNWord newWord = new MNWord(word);
                MNWord save = wordRepository.save(newWord);
                return new ResponseEntity<>(save, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(dbWord, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO SAVE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *  create relation from start to end
     */
    @PostMapping("/{start}/{end}")
    public ResponseEntity<?> createRelation(@PathVariable String start, @PathVariable String end){
        try {
            MNWord startNode = wordRepository.findMNWordByTitle(start);
            if (null != startNode){
                MNWord endNode = wordRepository.findMNWordByTitle(end);
                if (null != endNode){
                    startNode.derive(endNode);
                    MNWord save = wordRepository.save(startNode);
                    return checkReturn(save);
                }
                // if there is no child node then create a child node before create relation
                else {
                    MNWord mnWord = new MNWord(end);
                    startNode.derive(wordRepository.save(mnWord));
                    MNWord save = wordRepository.save(startNode);
                    return checkReturn(save);
                }
            }
            else return new ResponseEntity<>("PARENT NODE DOES NOT EXIST", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO ADD RELATION", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{title}")
    public ResponseEntity<?> getWord(@PathVariable String title){
        MNWord mnWordByTitle;
        try {
            mnWordByTitle = wordRepository.findMNWordByTitle(title);
            return checkReturn(mnWordByTitle);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO GET", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * obtain direct parent node
     */
    @GetMapping("parent/{title}")
    public ResponseEntity<?> getParent(@PathVariable String title){
        MNWord parent;
        try {
            parent = wordRepository.findParent(title);
            return checkReturn(parent);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO GET", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("parents/{title}")
    public ResponseEntity<?> getParents(@PathVariable String title){
        List<MNWord> parentList = null;
        try {
            parentList = wordRepository.findParentList(title);
            return checkReturnList(parentList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO GET", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * obtain all children nodes
     */
    @GetMapping("/children/{title}")
    public ResponseEntity<?> getChildren(@PathVariable String title){
        List<MNWord> childList = null;
        try {
            childList = wordRepository.findChildList(title);
            return checkReturnList(childList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO GET", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
//    @PutMapping("/{title}/{newTitle}")
//    public ResponseEntity<?> updateWord(@PathVariable String title, @PathVariable String newTitle){
//        MNWord mnWord = null;
//        try {
//            mnWord = wordRepository.updateWordByTitle(title, newTitle);
//            return new ResponseEntity<>(mnWord, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("FAIL TO UPDATE", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @DeleteMapping("/{start}/{end}")
    public ResponseEntity<?>  deleteRelation(@PathVariable String start, @PathVariable String end){
        Set<MNWord> relation = null;
        try {
            wordRepository.deleteRelationTo(start, end);
            relation = wordRepository.findRelation(start, end);
            return checkDelete(relation);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO DELETE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("node/{start}/{end}")
    public ResponseEntity<?>  deleteNodeAndRelation(@PathVariable String start, @PathVariable String end){
        Set<MNWord> relation = null;
        try {
            wordRepository.deleteWordAndRelation(start, end);
            relation = wordRepository.findRelation(start, end);
            return checkDelete(relation);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO DELETE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<?>  deleteNode(@PathVariable String title){
        try {
            wordRepository.deleteWordByTitle(title);
            return new ResponseEntity<>(title, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO DELETE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private <T> ResponseEntity<?> checkReturn(T t){
        if (null != t){
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
        else return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
    }

    private <T> ResponseEntity<?> checkReturnList(List<T> t){
        if (null != t && 0 != t.size()){
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
        else return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
    }

    private <T> ResponseEntity<?> checkDelete(Set<T> t){
        if (null == t || 0 == t.size()){
            return new ResponseEntity<>("SUCCESS TO DELETE", HttpStatus.OK);
        }
        else return new ResponseEntity<>("FAIL TO DELETE", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
