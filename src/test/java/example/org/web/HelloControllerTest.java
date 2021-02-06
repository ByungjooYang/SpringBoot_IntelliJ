package example.org.web;

import example.org.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
) //스캔 대상에서 SecurityConfig를 제거한것.
public class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
    }


    @WithMockUser(roles = "USER")
    @Test
    public void helloDTO_return() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))).andExpect(jsonPath("$.amount", is(amount)));
    }
}
/*
1. RunWith
 : 테스트를 진행할 때 JUnit에 내장된 실행자 외 다른 실행자를 실행시킨다.
  여기서는 SpringExtension라는 스프링 실행자를 사용한다. 즉, 스프링부트 테스트와 JUnit 사이에 연결자 역할을 한다.

2. @WebMvcTest
 : Web(Spring Mvc)에 집중할 수 있는 어노테이션. 이를 선언할 경우 @Controller, @CotrollerAdvice 등을 사용할 수 있다.
  단, @Service, @Component, @Repository 등은 사용할 수 업다. 여기서는 컨트롤러만 사용하기 때문에 선언한다.

3. @Autowired
 : 스프링이 관리하는 빈을 주입받는다.

4. private MockMvc mvc
 : 웹 API를 테스트 할 때 사용한다.
  스프링 MVC 테스트의 시작점이다.
  이 클래스를 통해 HTTP GEGT, POST 등에 대한 API 테스트를 할 수 있다.

5. mvc.perform
 : MockMvc를 통해 /hello 주소로 HTTP GETe을 요청을 한다.
  체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다.

6. .andExpect(status().isOk())
 : mvc.perform의 결과를 검증한다.
  HTTP Header의 Status를 검증한다.
  우리가 흔히 알고있는 200, 404, 500 등의 상태를 검증한다.
  여기선 ok 즉, 200인지 아닌지를 검증한다.

7. .andExpect(content().string(hello))
 : mvc.perform의 결과를 검증한다.
  응답 본무의 내용을 검증한다.
  Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다.

8. param
 : api 테스트 할 때 사용될 요청 파라미터를 설정한다. 단, 값은 String 만 허용.
  그래서 숫자, 날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능하다.

9. jsonPath
 : json 응답값의 빌드별로 검증할 수 있는 메서드.
  $를 기준으로 필드명을 명시한다. $.* 형식.
 */

/*
@WebMvcTest 는 WebSecurityAdapter, WebMvcConfigurer 를 비롯한 @ControllerAdvicㄷ, @Controller를 읽는다.
즉, @Repository, @Service, @Component 는 스캔 대상이 아니다. 따라서 SecurityConfig는 읽었지만 이를 생성하기 위한 Custom2OAuth2UserService는 읽을수 없어 에러가 뜨는 것
따라서 스캔 대상에서 SecurityCofig를 제거해 줬다.
 */
