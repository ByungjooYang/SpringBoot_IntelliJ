package example.org.config.auth.dto;

import example.org.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}

/*
Entity인 User 클래스를 세션에 저장하려면 User 클래스에 직렬화를 구현하지 않았다고 에러가 난다.
User클래스를 직렬화 시킬수도 있지만 엔티티 클래스에는 언제 다른 엔티티와 관계가 형성될지 모른다.
예를들어 @OneToMany, @ManyToMany 등 자식 엔티티를 갖고 있디면 직렬화 대상에 자식들까지 포함이되니 성능 이슈, 부수 효과가 발생할 확률이 높다.
그래서 직렬화 기능을 가진 DTO를 하나 추가로 만드는 것이 운영 및 유지 보수 때 많은 도움이 된다.
 */
