    package selfprojects.postAPI.Model.Entity;


    import jakarta.persistence.*;
    import lombok.Data;

    @Table(name = "users")
    @Entity
    @Data
    public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String email;

        private String password;

        private String role;

    }
