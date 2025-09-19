package selfprojects.postAPI.Model.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "reminders")
@Data
public class ReminderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime reminderTime;


    @NotNull
    @Column(name = "completed")
    private boolean completed;

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonBackReference
    private PostEntity post;



}
