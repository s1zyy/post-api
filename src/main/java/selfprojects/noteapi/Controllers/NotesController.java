package selfprojects.noteapi.Controllers;

import org.springframework.web.bind.annotation.*;
import selfprojects.noteapi.Model.NoteEntity;
import selfprojects.noteapi.Services.NotesService;

import java.util.List;

@RestController
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public List<NoteEntity> getAllNotes(){
        return notesService.getAllNotes();
    }

    @GetMapping("{id}")
    public NoteEntity getNoteById(@PathVariable(name = "id") Long id){
        return notesService.getNoteById(id);
    }

    @PostMapping
    public void createNote(@RequestBody NoteEntity noteEntity){
        notesService.createNote(noteEntity);
    }

    @PutMapping(path = "{id}")
    public void updateNote(
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String username,
            @PathVariable(name = "id") Long id){
        notesService.updateNote(title, content, username, id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteNote(@PathVariable(name = "id") Long id){
        notesService.deleteNote(id);
    }
}
