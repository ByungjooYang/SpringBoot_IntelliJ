package example.org.config.auth;

import example.org.domain.user.Role;
import example.org.service.user.UserService;
import example.org.web.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/image/**", "/js/**", "/h2-console/**", "/loginForm", "/joinForm", "/join").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginPage("/loginForm")
                        .defaultSuccessUrl("/")
                            .loginProcessingUrl("/login")
                                .successHandler(new CustomLoginSuccessHandler(userService))
            .and()
                .logout()
                    .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
            .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

}

/*
1. @EenableWebSecurity
 : 스프링 시큐리티 설정들을 활성화시켜준다.

2. .csrf().disable().headers().frameOptions().disable()
 : h2-consle을 사용하기위해 해당 옵션들을 disable 시켜준다.

3. authorizeRequests
 : url 별 권한 관리를 설정하는 옵션이다. 이게 선언되어야 antMatchers 옵션을 사용할 수 있다.

4. antMatchers
 : 권한관리 대상을 지정하는 옵션이다. url, http 메소드 별로 관리가 가능하다.

5. anyRequest
 : 설정된 값들 이외의 나머지 url 들을 나타낸다. 여기선 authenticated()를 추가해 나머지 url 은 모두 인증된 사용자들에게만 허용하게 한다.
 즉, 로그인 한 사람만 이용 가능하다는 거.

6. oauth2Login
 : oauth2 로그인 기능에 대한 여러 설정의 진입점이다.

7. userInfoEndPoint
 : OAuth2 로그인 성공 이후 사용자 정보를 가져올 땨의 설정들을 담당한다.

8. userService
 : 소셜 로그인 성공 시 후속 조치를 진핼할 UserService 인터페이스의 구현체를 등록.
  리소스 서버 즉, 소셜 서비스들에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 잇다.
 */
