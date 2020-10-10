package example.org.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;

}

/*
@RequiredArgsConstructor
 : 선언된 모든 final 필드가 포함된 생성자를 만들어준다. 혹은 @NonNull이 포함된 필드.
  final 없는 필드는 생성자에 포함되지 않는다.
 */
