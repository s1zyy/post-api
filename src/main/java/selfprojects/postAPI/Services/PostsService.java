package selfprojects.postAPI.Services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.MyUserDetails;
import selfprojects.postAPI.Repository.PostRepository;
import selfprojects.postAPI.Repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostsService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;

    }

    public List<PostEntity> getAllPosts() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        Long userId = user.getId();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();

        return userEntity.getPosts();
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public PostEntity getPostById(Long id) {
        PostEntity checkForPost = postRepository.findById(id).orElse(null);
        if(checkForPost == null){
            throw new IllegalStateException("Not with this ID not found!");
        }
        return checkForPost;
    }


    public PostEntity updatePost(PostEntity post, Long id) {
        String title = post.getTitle();
        String content = post.getContent();

        PostEntity checkForPost = postRepository.findById(id).orElse(null);
        if(checkForPost == null) {
            throw new IllegalStateException("Post with this ID not found!");
        }

        var auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        checkForPost.setUser(userRepository.findById(user.getId()).orElseThrow());
        checkForPost.setTitle(title);
        checkForPost.setContent(content);
        postRepository.save(checkForPost);
        return checkForPost;
    }

    public PostEntity createPost(PostEntity postEntity) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        postEntity.setUser(userRepository.findById(user.getId()).orElseThrow());


        Optional<PostEntity> checkForPost = postRepository.findByTitleAndContentAndUser(postEntity.getTitle(), postEntity.getContent(), postEntity.getUser());
        if(checkForPost.isPresent()){
            throw new IllegalStateException("Post with this title and content already exists!");
        }
        return postRepository.save(postEntity);

    }


}
