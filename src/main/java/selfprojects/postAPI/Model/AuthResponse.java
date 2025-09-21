package selfprojects.postAPI.Model;

import selfprojects.postAPI.Model.Entity.UserEntity;

public record AuthResponse(String token, UserEntity user) {
}
