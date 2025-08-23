package selfprojects.noteapi.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import selfprojects.noteapi.Model.NoteEntity;
import selfprojects.noteapi.Model.UserEntity;
import selfprojects.noteapi.Services.NotesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/allNotes")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<NoteEntity> getAllNotes(){
        return notesService.getAllNotes();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NoteEntity getNoteById(@PathVariable(name = "id") Long id){
        return notesService.getNoteById(id);
    }

    @PostMapping("/createNote")
    public void createNote(@RequestBody NoteEntity noteEntity){
        notesService.createNote(noteEntity);
    }

    @PutMapping(path = "/update/{id}")
    public void updateNote(
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String username,
            @PathVariable(name = "id") Long id){
        notesService.updateNote(title, content, username, id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteNote(@PathVariable(name = "id") Long id){
        notesService.deleteNote(id);
    }


    @PostMapping("/createUser")
    public String createUser(@RequestBody UserEntity userEntity){
        notesService.createUser(userEntity);
        return "User created!";
    }
}
