package example.org.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("DB", "DB1", "DB2");

        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);

    }
}

/*
env.getActiveProfiles()
 : 현재 실행 중인 ActiveProfile을 모두 가져온다. 즉, oauth, DB, AWS 등이 활성화 되어 있다면 3개가 모두 담겨있다.
  여기서 AWS, AWS1, AWS2 는 모두 매포에 사용될 profile 이라 이 중 하나만 있으면 그 값을 반환하도록 한다.
 */
