package example.org.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.org.domain.posts.Posts;
import example.org.domain.posts.PostsRepository;
import example.org.web.dto.PostsSaveRequestDTO;
import example.org.web.dto.PostsUpdateRequestDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDrop() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_save() throws Exception {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDTO requestDTO = PostsSaveRequestDTO.builder().title(title).content(content).author("author").build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        /*ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDTO, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
         */
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(requestDTO))).andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_Update() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDTO requestDTO = PostsUpdateRequestDTO.builder().title(expectedTitle).content(expectedContent).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        //when
        /*
        HttpEntity<PostsUpdateRequestDTO> requestEntity = new HttpEntity<>(requestDTO);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        */
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(requestDTO))).andExpect(status().isOk());

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}

/*
1. HelloControllerTest와 달리 @WebMvcTest를 쓰지 않았는데 얘는
JPA 기능이 작동을하지 않기 때문이다.

2. @Before
 : 매번 테스트 시작 전에 MockMvc 인스턴스를 생성한다.

3. mvc.perform
 : 생성된 MockMvc 를 통해 API 테스트를 한다.
  본문 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 json으로 변환한다.
  @WithMockUser는 MockMvc에서만 작동하기 때문에 바꿔준 것이다.

4. @WithMockUser
 : 인증된 모의 사용자를 만들어 사용한다.
  roles에 권한을 추가할 수 있다. 즉, 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가 api 요청하는 것과 동일한 효과를 가지게 된다.
*/
