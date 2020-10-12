package example.org.service.posts;

import example.org.domain.posts.Posts;
import example.org.domain.posts.PostsRepository;
import example.org.web.dto.PostsListsResponseDTO;
import example.org.web.dto.PostsResponseDTO;
import example.org.web.dto.PostsSaveRequestDTO;
import example.org.web.dto.PostsUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDTO requestDTO) {

        return postsRepository.save(requestDTO.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDTO requestDTO) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDTO.getTitle(), requestDTO.getContent());

        return id;
    }

    public PostsResponseDTO findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));

        return new PostsResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListsResponseDTO> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(posts -> new PostsListsResponseDTO(posts)).collect(Collectors.toList());
    }
}

/*
@Transactional에 readOnly=true를 붙이면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선되기 때문에
등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용하는 것을 추천.

map(posts -> new PostsListsResponseDTO()) => map(PostListsResponseDTO::new)
 */
