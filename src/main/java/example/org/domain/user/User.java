package example.org.domain.user;

import example.org.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, String password, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}

/*
@Enumerated(EnumType.String)
 : Jpa로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지 결정하는 것.
  기본적으로는 int 로 된 숫자가 저장된다. 숫자로 저장되면 데이터베이스로 확인할 때 그 값이 무슨 코드를 의미하는지 알 수가 없다. 그래서 문자열로 저장한것.
 */
