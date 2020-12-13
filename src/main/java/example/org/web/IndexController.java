package example.org.web;

import example.org.config.auth.LoginUser;
import example.org.config.auth.dto.SessionUser;
import example.org.service.posts.PostsService;
import example.org.web.dto.PostsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        Enumeration se = httpSession.getAttributeNames();

        while (se.hasMoreElements()) {
            String getse = se.nextElement() + "";

            System.out.println("@@@@@@@ session : " + getse + " : " + httpSession.getAttribute(getse));

        }

        if (user != null) {
            model.addAttribute("userName", "aaa");
            model.addAttribute("user", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDTO dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

    @GetMapping("/loginForm")
    public String loginForm() {

        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {

        return "joinForm";
    }
}

/*
앞의 경로와 뒤의 확장자는 자동으로 붙는다.
 */
