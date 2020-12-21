package example.org.web;

import example.org.config.auth.dto.SessionUser;
import example.org.domain.user.UserRepository;
import example.org.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = request.getParameter("username");
        String name = userService.getName(email);
        SessionUser user = new SessionUser(email, name, "");

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        response.sendRedirect("/");
    }
}
