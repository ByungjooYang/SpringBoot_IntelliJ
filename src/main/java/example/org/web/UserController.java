package example.org.web;

import example.org.service.user.UserService;
import example.org.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public void join(@ModelAttribute UserDTO userDTO) {
        if (userDTO != null) {
            System.out.println(userDTO.getName());

        }

        userService.save(userDTO);
    }
}
