package example.org.web;

import example.org.domain.user.User;
import example.org.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;

    @PostMapping("/join")
    public void join(@RequestParam String email, String password, String nickName) {


    }
}
