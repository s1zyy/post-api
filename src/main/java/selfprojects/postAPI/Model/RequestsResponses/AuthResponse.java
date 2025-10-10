package selfprojects.postAPI.Model.RequestsResponses;

import selfprojects.postAPI.Model.Entity.UserEntity;

public record AuthResponse(String token, UserEntity user) {
}
