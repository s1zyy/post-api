    package selfprojects.postAPI.Model.Entity;

    import jakarta.persistence.*;
    import lombok.Data;

    import java.time.LocalDateTime;
    import java.util.List;

    @Table(name = "users")
    @Entity
    @Data
    public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String username;

        private LocalDateTime birthDate;

        private String email;

        private String password;

        private String role;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        @OrderBy("id ASC")
        private List<PostEntity> posts;




    }
