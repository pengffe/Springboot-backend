package com.pengffe.pte.modules.neo4j.entity;
/**
 * @descrption
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.ArrayList;
import java.util.List;

@Node
@Data
@NoArgsConstructor
public class User {
    @Id
    private long id;

    private String name;



    public User(long id, String name){
        this.id = id;
        this.name = name;
    }

    @Relationship(type = "FOLLOW", direction = Direction.OUTGOING)
    private List<User> follow = new ArrayList<>();

    public void follow(User User) {
        follow.add(User);
    }

//    @Relationship(type = "FRIEND_IN", direction = Direction.INCOMING)
//    private List<User> followed = new ArrayList<>();
}
