package selfprojects.postAPI.Services;

import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.Repository.PostRepository;
import selfprojects.postAPI.Repository.UserRepository;
import java.util.List;

@Service
public class PostsService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public PostsService(PostRepository postRepository, UserRepository userRepository, AuthService authService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<PostEntity> getAllPosts() {
       return getCurrentUserEntity().getPosts();
    }

    public void deletePost(Long id) {
        PostEntity post = getPostById(id);
        postRepository.delete(post);
    }

    public PostEntity getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("Post with this ID not found!"));
    }

    public PostEntity updatePost(PostEntity post, Long id) {
        PostEntity existingPost = getPostById(id);
        existingPost.setUser(getCurrentUserEntity());
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        return postRepository.save(existingPost);
    }

    public PostEntity createPost(PostEntity postEntity) {
        postEntity.setUser(getCurrentUserEntity());
        return postRepository.save(postEntity);
    }

    private UserEntity getCurrentUserEntity() {
        return userRepository.findById(authService.getCurrentUserId()).orElseThrow(()-> new IllegalStateException("User with this ID not found!"));
    }
}