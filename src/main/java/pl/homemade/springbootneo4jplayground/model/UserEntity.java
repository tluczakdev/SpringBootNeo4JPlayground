package pl.homemade.springbootneo4jplayground.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("User")
public class UserEntity {

    @Id
    private final String id;

    private final String username;

    @Relationship(type = "COMPANION_WITH")
    private Set<UserEntity> companions = new HashSet<>();

    public UserEntity(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void addCompanion(UserEntity user) {
        companions.add(user);
    }
}
