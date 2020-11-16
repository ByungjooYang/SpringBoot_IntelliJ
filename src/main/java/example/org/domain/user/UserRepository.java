package example.org.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}

/*
findByEmail
 : 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판한다기 위한 메서드.

Optional<T>
 : of() 나 ofNullable() 를 사용해서 Optional 객체를 생성 가능. of()를 통해 생성된 Optional 객체에 null이 저장되면
  NullPointerException 예외가 발생한다. 따라서 참조 변수의 값이 만에 하나 null 이 저장될 가능성이 있다면, ofNullable()를 사용해
  Optional 객체를 생성하는 것이 좋다. null이면 비어있는 Optional 객체를 생성한다.
 */
