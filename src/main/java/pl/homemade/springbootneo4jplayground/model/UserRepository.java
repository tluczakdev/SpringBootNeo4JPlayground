package pl.homemade.springbootneo4jplayground.model;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<UserEntity, String> {
}
