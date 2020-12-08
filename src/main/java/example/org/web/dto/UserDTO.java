package example.org.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String password;
    private String name;

}
