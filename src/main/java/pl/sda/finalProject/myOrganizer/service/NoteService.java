package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.INoteRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Note;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private INoteRepository noteRepository;
    @Autowired
    private UserService userService;

    public void addNote(Note note) {
        note.setCreationDate(LocalDate.now());
        noteRepository.save(note);
    }

    public List<Note> findNotesByUser(MyUser user){
        return noteRepository.findByUser(user);
    }
}
