package example.org.web;

import example.org.service.user.UserService;
import example.org.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public String join(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        String email = userService.findEmail(userDTO.getEmail());

        if (email != null) {
            redirectAttributes.addFlashAttribute("msg", "msg");

            return "redirect:/joinForm";

        }else {
            userService.save(userDTO);

            return "redirect:/loginForm";

        }
    }
}
