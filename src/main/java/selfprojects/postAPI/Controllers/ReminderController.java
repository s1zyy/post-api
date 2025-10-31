package selfprojects.postAPI.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import selfprojects.postAPI.Model.Entity.ReminderEntity;
import selfprojects.postAPI.Model.RequestsResponses.ReminderRequest;
import selfprojects.postAPI.Services.ReminderService;

@RestController
@RequestMapping("/api/v1/reminder")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping
    public ResponseEntity<ReminderEntity> addReminder(@RequestBody ReminderRequest reminderRequest) {
        ReminderEntity reminder = reminderService.saveReminder(reminderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reminder);
    }

    @PutMapping("/{id}")
    public ReminderEntity updateReminder(@RequestBody ReminderEntity reminderForUpdate, @PathVariable(name = "id") Long id)
    { return reminderService.updateReminder(reminderForUpdate); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable(name = "id") Long id)
    {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}
