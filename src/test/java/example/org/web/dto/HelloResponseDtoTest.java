package example.org.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {
    @Test
    public void lombok_Test() {
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}

/*
assertThat
 : assertj라는 테스트 검증 라이브러리의 검증 메서드
  검증하고싶은 대상을 메소드 인자로 받는다. 메소드 체이닝이 지원되어 isEqualTo 같은 메소드를 이어 사용 가능하다.

isEqualTo
 : assertJ의 동등 비교 메소드.
  assertThat에 있는 값과 isEqualTO의 값의 비교해서 같을 때만 성공이다.

JUnit과 비교해서 assertj의 장점은
1. CoreMatchers와 달리 추가적으로 라이브러리가 필요하지 않다.
2. 자동완성이 좀 더 확실하게 지원된다.
 */
