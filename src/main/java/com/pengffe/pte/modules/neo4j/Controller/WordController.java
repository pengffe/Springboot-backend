package com.pengffe.pte.modules.neo4j.Controller;

import com.pengffe.pte.modules.neo4j.Repository.WordRepository;
import com.pengffe.pte.modules.neo4j.entity.Word;
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
public class WordController {

    @Autowired
    WordRepository wordRepository;

    @PostMapping
    public ResponseEntity<?> saveVocabulary(@RequestBody Word word){
        Word dbWord = null;
        try {
            dbWord = wordRepository.findMNWordByTitle(word.getTitle());
            if (null == dbWord){
                Word save = wordRepository.save(word);
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
        Word dbWord = null;
        try {
            dbWord = wordRepository.findMNWordByTitle(word);
            if (null == dbWord){
                Word newWord = new Word(word);
                Word save = wordRepository.save(newWord);
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
            Word startNode = wordRepository.findMNWordByTitle(start);
            if (null != startNode){
                Word endNode = wordRepository.findMNWordByTitle(end);
                if (null != endNode){
                    startNode.derive(endNode);
                    Word save = wordRepository.save(startNode);
                    return checkReturn(save);
                }
                // if there is no child node then create a child node before create relation
                else {
                    Word word = new Word(end);
                    startNode.derive(wordRepository.save(word));
                    Word save = wordRepository.save(startNode);
                    return checkReturn(save);
                }
            }
            else return new ResponseEntity<>("PARENT NODE DOES NOT EXIST", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("fail");
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO ADD RELATION", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{title}")
    public ResponseEntity<?> getWord(@PathVariable String title){
        Word wordByTitle;
        try {
            wordByTitle = wordRepository.findMNWordByTitle(title);
            return checkReturn(wordByTitle);
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
        Word parent;
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
        List<Word> parentList = null;
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
        List<Word> childList = null;
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
//        Word mnWord = null;
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
        Set<Word> relation = null;
        try {
            wordRepository.deleteRelationTo(start, end);
            relation = wordRepository.findRelation(start, end);
            return checkDelete(relation);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL TO DELETE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除end节点，并指明直系父节点（唯一）并删除关系
     * @param start
     * @param end
     */
    @DeleteMapping("node/{start}/{end}")
    public ResponseEntity<?>  deleteNodeAndRelation(@PathVariable String start, @PathVariable String end){
        Set<Word> relation = null;
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
            // delete all relations to parents
            List<Word> parentList = wordRepository.findParentList(title);
            for (Word parent: parentList
                 ) {
                wordRepository.deleteRelationTo(parent.getTitle(), title);
            }
            // delete all relations to children
            List<Word> childList = wordRepository.findChildList(title);
            for (Word child: childList
            ) {
                wordRepository.deleteRelationTo(title, child.getTitle());
            }

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
