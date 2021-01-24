package example.org.web;

import example.org.config.auth.LoginUser;
import example.org.config.auth.dto.SessionUser;
import example.org.domain.user.User;
import example.org.service.posts.PostsService;
import example.org.web.dto.PostsResponseDTO;
import example.org.web.dto.PostsSaveRequestDTO;
import example.org.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDTO requestDTO, @LoginUser SessionUser sessionUser) {
        String email = sessionUser.getEmail();
        requestDTO.setEmail(email);

        return postsService.save(requestDTO);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDTO requestDTO) {
        return postsService.update(id, requestDTO);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDTO findById(@PathVariable Long id) {

        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
