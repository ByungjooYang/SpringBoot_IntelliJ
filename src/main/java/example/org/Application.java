package example.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//스프링부트의 자동 설정, 스프링 bean 읽기와 생성을 모두 자동으로 설정한다. 이 어노테이션이 있는 파일부터 설정을 읽어들어가기 때문에 항상 프로젝트 최상단에 위치해야한다.
//@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); //run은 내장 WAS를 실행하는 메소드 => 톰캣을 사용할 필요가 없다.
    }
}
