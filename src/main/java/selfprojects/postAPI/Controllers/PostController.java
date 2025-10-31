package selfprojects.postAPI.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Services.PostService;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<PostEntity> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public PostEntity getPostById(@PathVariable(name = "id") Long id){
        return postService.getPostById(id);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostEntity> createPost(@RequestBody PostEntity postEntity){
        PostEntity createdPost = postService.createPost(postEntity);
        return ResponseEntity.status(201).body(createdPost);
    }

    @PutMapping(path = "/posts/{id}")
    public PostEntity updatePost(
            @RequestBody PostEntity postEntity,
            @PathVariable(name = "id") Long id){
        return postService.updatePost(postEntity, id);
    }

    @DeleteMapping(path = "/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
