package selfprojects.postAPI.Controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import selfprojects.postAPI.Model.ApiReturn;
import selfprojects.postAPI.Model.ChangeNameRequest;
import selfprojects.postAPI.Model.CreateUserRequest;
import selfprojects.postAPI.Model.Entity.ChangeBirthdayRequest;
import selfprojects.postAPI.Model.Entity.UserEntity;
import selfprojects.postAPI.Services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/name")
    public UserEntity updateName(@RequestBody ChangeNameRequest changeNameRequest, @PathVariable(name = "id") Long id){

        return userService.updateName(changeNameRequest, id);
    }

    @PutMapping("/{id}/birthdate")
    public UserEntity updateBirthDate(@RequestBody ChangeBirthdayRequest request, @PathVariable(name = "id") Long id){
        return userService.updateBirthDate(request, id);
    }

    @PostMapping
    public ApiReturn createUser(@Valid @RequestBody CreateUserRequest userRequest){
        boolean check = userService.createUser(userRequest);


        if(!check){
            return new ApiReturn("User already exists!");
        }
        return new ApiReturn("User created successfully!");
    }
}
