package example.org.web.dto;

import example.org.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListsResponseDTO {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListsResponseDTO(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
