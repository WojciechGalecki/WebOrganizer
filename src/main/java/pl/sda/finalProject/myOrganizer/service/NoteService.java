package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.INoteRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Note;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private INoteRepository noteRepository;

    public void addNote(Note note, MyUser user) {
        note.setUser(user);
        noteRepository.save(note);
    }

    public List<Note> findNotesByUser(MyUser user){
        return noteRepository.findByUser(user);
    }
}
