package example.org.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void post_save() {
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder().title(title).content(content).author("byungjoo104@gmail.com").build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}

/*
1. @After
 : JUnit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정하는것.
  보통 배포전 전체 테스트를 수행할 때 테스트간 데이터 침법을 막기 위해 사용.
  여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행시 테스트가 실패할 수 있다.

2. postsRepository.save
 : 테이블 posts에 insert/update 쿼리를 실행.
  id 값이 있다면 update가, 없다면 insert가 실행된다.

3. postsRepository.findAll
 : 테이블 posts에 있는 모든 데이터를 조회해오는 메소드.

* SpringBootTest는 별다른 설정이 없으면 H2 데이터베이스를 자동으로 실행시켜준다.

*application.properties

spring.jpa.show-sql=true -> sql문을 콘솔에 보여준다
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect -> sql문을 mysql로 바꿔보여준다.
 */
