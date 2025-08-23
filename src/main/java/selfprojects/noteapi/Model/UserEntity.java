    package selfprojects.noteapi.Model;


    import jakarta.persistence.*;
    import lombok.Data;

    @Table(name = "users")
    @Entity
    @Data
    public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String username;

        private String password;

        private String role;
    }
