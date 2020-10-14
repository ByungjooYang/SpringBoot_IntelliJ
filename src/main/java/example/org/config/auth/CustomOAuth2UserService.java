package example.org.config.auth;

import example.org.config.auth.dto.OAuthAttributes;
import example.org.config.auth.dto.SessionUser;
import example.org.domain.user.User;
import example.org.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
       OAuth2User oAuth2User = delegate.loadUser(userRequest);

       String registrationId = userRequest.getClientRegistration().getRegistrationId();

       String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

       OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

       User user = saveOrUpdate(attributes);

       httpSession.setAttribute("user", new SessionUser(user));

       return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

/*
1. registrationId
 : 현재 로그인 진행 중인 서비스를 구분하는 코드
  지금은 구글만 사용하는 불필요한 값이나 이후 네이버, 깃허브 연동시 구분하기 위해 사용한다.

2. userNameAttributeName
 : OAuth2 로그인 진행시 키가되는 필드값을 말한다. 기본키와 같은 의미
  구글의 경우 기본적으로 코드를 지원하나 다른 것들은 지원하지 않는다. 구글의 기본 코드는 sub 이다.

 3. OAuthAttribute
  : OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담을 클래스이다.

4. SessiomUIser
 : 세션에 사용자 정보를 저장하기 위한 dto 클래스이다.
  User 클래스를 쓰지 않는 이유는 직렬화를 구현하지 않았기 때문이다. User 클래스는 앤티티이기 때문에 다른 엔티티와 관계가 형성될 수 있기 때문ㅇㅇ이다.
  따라서 직렬화 기능을 가진 dto를 하나 추가로 만드는 것이 운영 및 유지보수에 좋다.
 */
