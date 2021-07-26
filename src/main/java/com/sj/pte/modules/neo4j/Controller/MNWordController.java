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
import java.util.Optional;

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
        wordRepository.save(mnWord);
        return checkResult(mnWord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDerivedWord(@PathVariable Long id){
        List<MNWord> childList = wordRepository.findChildList(id);
        return checkResult(childList);
    }

    @PutMapping("/{id}/{did}")
    public void addRelation(@PathVariable Long id, @PathVariable Long did){
        Optional<MNWord> word = wordRepository.findById(did);
        word.get().derive(wordRepository.findById(id).get());
        wordRepository.save(word.get());
    }

    private <T> ResponseEntity<?> checkResult(T t){
        if (null != t){
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
        else return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
