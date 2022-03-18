package com.pengffe.pte.modules.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @descrption
 */

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String soundMark;
    private String explanation;
    private List<String> examples;


    /**
     * this word is a origin from which others are derived
     */
    @Relationship(type = "derive", direction = Relationship.Direction.OUTGOING)
    private Set<Word> derive;

    public void derive(Word word) {
        if (derive == null) {
            derive = new HashSet<>();
        }
        derive.add(word);
    }

    public Word(Word word){
        this.id = word.id;
        this.title = word.title;
        this.soundMark = word.soundMark;
        this.explanation = word.explanation;
        this.examples = word.examples;
        this.derive = word.derive;
    }

    public Word(String title){
        this.title = title;
    }
}
