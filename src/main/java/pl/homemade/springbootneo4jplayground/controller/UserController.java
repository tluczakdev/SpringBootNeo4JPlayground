package pl.homemade.springbootneo4jplayground.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.homemade.springbootneo4jplayground.service.IUserService;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController("user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/init")
    public void init() throws IOException {
        userService.initialize();
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity<Object> handleIOException(Exception ex) {
        return new ResponseEntity<>(
                ex.getMessage(), new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }
}
