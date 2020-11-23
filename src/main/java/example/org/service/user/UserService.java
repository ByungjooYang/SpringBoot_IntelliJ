package example.org.service.user;

import example.org.domain.user.Role;
import example.org.domain.user.User;
import example.org.domain.user.UserRepository;
import example.org.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(UserDTO userDTO) {
        String password = passwordEncoder.encode(userDTO.getPassword());
        User user = new User(userDTO.getName(), userDTO.getEmail(), null, password, Role.USER);
        userRepository.save(user);
    }
}
