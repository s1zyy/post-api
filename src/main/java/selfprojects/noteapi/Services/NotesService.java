package selfprojects.noteapi.Services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import selfprojects.noteapi.Model.NoteEntity;
import selfprojects.noteapi.Model.UserEntity;
import selfprojects.noteapi.Repository.NoteRepository;
import selfprojects.noteapi.Repository.UserRepository;

import java.util.List;

@Service
public class NotesService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public NotesService(NoteRepository noteRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<NoteEntity> getAllNotes() {
        return noteRepository.findAll();
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public NoteEntity getNoteById(Long id) {
        NoteEntity checkForNote = noteRepository.findById(id).orElse(null);
        if(checkForNote == null){
            throw new IllegalStateException("Not with this ID not found!");
        }
        return checkForNote;
    }

    @Transactional
    public void updateNote(String title, String content, String username, Long id) {
        NoteEntity checkForNote = noteRepository.findById(id).orElse(null);
        if(checkForNote == null){
            throw new IllegalStateException("Note with this ID not found!");
        }
        if(!checkForNote.getUsername().equals(username) && username != null){
            checkForNote.setUsername(username);
        }
        if(!checkForNote.getTitle().equals(title) && title != null){
            checkForNote.setTitle(title);
        }
        if(!checkForNote.getContent().equals(content) && content != null){
            checkForNote.setContent(content);
        }
    }

    public void createNote(NoteEntity noteEntity) {
        NoteEntity checkForNote = noteRepository.findByTitleAndContentAndUsername(noteEntity.getTitle(), noteEntity.getContent(), noteEntity.getUsername()).orElse(null);
        if(checkForNote != null){
            throw new IllegalStateException("Note with this title and content already exists!");
        }
        noteRepository.save(noteEntity);
    }

    public void createUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

}
