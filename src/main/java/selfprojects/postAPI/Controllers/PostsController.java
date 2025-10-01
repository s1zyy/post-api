package selfprojects.postAPI.Controllers;

import org.springframework.web.bind.annotation.*;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Services.PostsService;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostsController {
    private final PostsService postsService;

    public PostsController(PostsService notesService) {
        this.postsService = notesService;
    }

    @GetMapping("/posts")
    public List<PostEntity> getAllPosts(){
        return postsService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public PostEntity getPostById(@PathVariable(name = "id") Long id){
        return postsService.getPostById(id);
    }

    @PostMapping("/posts")
    public PostEntity createPost(@RequestBody PostEntity postEntity){
        return postsService.createPost(postEntity);
    }

    @PutMapping(path = "/posts/{id}")
    public PostEntity updatePost(
            @RequestBody PostEntity postEntity,
            @PathVariable(name = "id") Long id){
        return postsService.updatePost(postEntity, id);
    }

    @DeleteMapping(path = "/posts/{id}")
    public void deletePost(@PathVariable(name = "id") Long id){
        postsService.deletePost(id);
    }



}
