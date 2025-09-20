package selfprojects.postAPI.Controllers;


import org.springframework.web.bind.annotation.*;
import selfprojects.postAPI.Model.Entity.PostEntity;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Services.ReminderService;

@RestController
@RequestMapping("/api/v1")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/reminder")
    public ReminderEntity addReminder(@RequestBody PostEntity postEntity) {
        return reminderService.saveReminder(postEntity);
    }

    @PutMapping("/reminder/{id}")
    public ReminderEntity updateReminder(@RequestBody PostEntity postEntity, @PathVariable(name = "id") Long id)
    {
        return reminderService.updateReminder(postEntity);
    }
}
