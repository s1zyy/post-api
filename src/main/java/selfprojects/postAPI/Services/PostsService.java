package selfprojects.postAPI.Services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import selfprojects.postAPI.Model.CreateUserRequest;
import selfprojects.postAPI.Model.Entity.PostDTO;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.MyUserDetails;
import selfprojects.postAPI.Repository.PostRepository;
import selfprojects.postAPI.Repository.ReminderRepository;
import selfprojects.postAPI.Repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReminderRepository reminderRepository;

    public PostsService(PostRepository postRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ReminderRepository reminderRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reminderRepository = reminderRepository;
    }

    public List<PostDTO> getAllPosts() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Long userId = user.getId();
        List<PostEntity> postsByID = postRepository.findAllByUserId(userId).orElse(null);

        assert postsByID != null;
        return postsByID.stream().map(post -> {
            ReminderEntity reminder = reminderRepository.findByPost(post);

            return new PostDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    reminder != null,
                    reminder != null && reminder.isDone(),
                    reminder != null ? reminder.getReminderTime() : null
            );
        }).collect(Collectors.toList());
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


    public PostEntity updatePost(String title, String content, Long id) {
        PostEntity checkForPost = postRepository.findById(id).orElse(null);
        if(checkForPost == null) {
            throw new IllegalStateException("Post with this ID not found!");
        }
        if(!checkForPost.getTitle().equals(title) && title != null){
            checkForPost.setTitle(title);
        }
        if(!checkForPost.getContent().equals(content) && content != null){
            checkForPost.setContent(content);
        }
        return postRepository.save(checkForPost);
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

    public boolean createUser(CreateUserRequest userRequest){

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setRole("ROLE_USER");

        Optional<UserEntity> checkForUser = userRepository.findByEmail(userEntity.getEmail());
        if(checkForUser.isPresent()){
            return false;
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return true;
    }

}
