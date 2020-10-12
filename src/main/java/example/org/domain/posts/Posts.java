package example.org.domain.posts;

import example.org.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

/*
**JPA 어노테이션**

1. @Entity
 : 테이블과 링크될 클래스임을 나타낸다.
  기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다.
  ex)SalesManager.java -> sales_managet table

2. @Id
 : 해당 테이블의 pk 필드를 나타낸다.

3. @GeneratedValue
 : PK의 생성규칙을 나타낸다.
  스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 된다.

4. @Column
 : 테이블의 칼럼을 나타내며 굳이 선언하지 않아도 해달 클래스의 필드는 모두 칼럼이 된다.
  굳이 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
  문자열의 경우 varchar(255)가 기본값이다. 타입을 TEXT로 변경한다던지 사이즈를 늘린다던지 할때 사용한다.
 */

/*
롬복 어노테이션

1. @NoArgsConstructor
 : 기본 생성자 자동 추가.

2. @Builder
 : 해당 클래스의 빌더 패던 클래스를 생성
  생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함.
 */