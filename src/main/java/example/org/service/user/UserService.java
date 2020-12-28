package example.org.service.user;

import example.org.domain.user.Role;
import example.org.domain.user.User;
import example.org.domain.user.UserRepository;
import example.org.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public void save(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userDTO.getPassword());
        User user = new User(userDTO.getName(), userDTO.getEmail(), null, password, Role.USER);
        userRepository.save(user);
    }

    @Transactional
    public String getName(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            return user.getName();
        }

        return null;
    }

    public String findEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {

            return "present";
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userWrapper = userRepository.findByEmail(email);
        User user = userWrapper.get();

        List<GrantedAuthority> authorityList = new ArrayList<>();

        if(("admin").equals(email)){
            authorityList.add(new SimpleGrantedAuthority(Role.ADMIN.getKey()));

        }else{
            authorityList.add(new SimpleGrantedAuthority(Role.USER.getKey()));

        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorityList);
    }

}

/*
@Transactional
 : transaction begin, commit을 자동으로 수행해 준다.
  예외를 발생시키면, rollback 처리를 자동 수행해준다.
 */
