package pl.homemade.springbootneo4jplayground.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.homemade.springbootneo4jplayground.model.UserEntity;
import pl.homemade.springbootneo4jplayground.model.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    public UserService(UserRepository userRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void initialize() throws IOException {
        initUsers();
        createConnections();
    }

    private void initUsers() throws IOException {
            DataUser dataUser = mapper.readValue(
                    new File("src/main/resources/initData/users.json"), DataUser.class);

            var result = dataUser.users()
                    .stream()
                    .map(u -> new UserEntity(u.id, u.username))
                    .toList();
            userRepository.saveAll(result);
    }

    private void createConnections() throws IOException {
        DataConnection dataConnection = mapper.readValue(
                new File("src/main/resources/initData/connections.json"), DataConnection.class);

        dataConnection
                .connections()
                .iterator()
                .forEachRemaining(
                        connection -> {
                            UserEntity firstUser = userRepository.findById(connection.userFirstId()).orElseThrow();
                            UserEntity secondUser = userRepository.findById(connection.userSecondId()).orElseThrow();

                            firstUser.addCompanion(secondUser);
                            userRepository.save(firstUser);
                        }
                );
    }

    private record DataUser(@JsonProperty("reply") List<User> users) {
    }

    private record User(
            String id,
            String username,
            @JsonProperty("access_level") String accessLevel,
            @JsonProperty("is_active") String isActive,
            @JsonProperty("lastlog") String lastLog) {
    }

    private record DataConnection(@JsonProperty("reply") List<Connection> connections) {
    }

    private record Connection(@JsonProperty("user1_id") String userFirstId,
                              @JsonProperty("user2_id") String userSecondId) {

    }
}
