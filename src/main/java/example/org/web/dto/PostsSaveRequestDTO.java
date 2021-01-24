package example.org.web.dto;

import example.org.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDTO {
    private String email;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDTO(String title, String content, String author, String email) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Posts toEntity() {
        return Posts.builder().title(title).content(content).author(author).email(email).build();
    }
}
