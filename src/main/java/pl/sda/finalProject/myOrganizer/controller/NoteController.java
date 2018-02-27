package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.INoteRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Note;
import pl.sda.finalProject.myOrganizer.service.NoteService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private INoteRepository noteRepository;

    @GetMapping("/organizer/notes")
    public String showNotesPage(Model model, Principal principal) {
        Note newNote = new Note();
        MyUser activeUser = userRepository.findOne(principal.getName());
        model.addAttribute("newNote", newNote);
        model.addAttribute("notes", noteService.findNotesByUser(activeUser));
        return "notes";
    }

    @PostMapping("/organizer/notes")
    public String addNote(@Valid @ModelAttribute("newNote") Note newNote, BindingResult bindingResult,
                          Principal principal) {
        MyUser activeUser = userRepository.findOne(principal.getName());
        newNote.setUser(activeUser);
        newNote.setCreationDate(LocalDate.now());
        if (bindingResult.hasErrors()) {
            return "notes";
        }
        noteService.addNote(newNote);
        return "redirect:/organizer/notes";
    }

    @GetMapping(path = "/organizer/notes/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Note editNote = noteRepository.findOne(id);

        if (editNote == null) {
            return "noteNotFound";
        }
        model.addAttribute("editNote", editNote);

        return "editNote";
    }

    @PostMapping(path = "/organizer/notes/edit/{id}")
    public String editNote(@PathVariable("id") Long id, @Valid @ModelAttribute("editNote") Note editNote,
                           BindingResult bindingResult) {
        Note entity = noteRepository.findOne(id);

        if (entity == null) {
            return "noteNotFound";
        }
        if (bindingResult.hasErrors()) {
            return "organizer/notes/edit/{id}";
        }
        entity.setDescription(editNote.getDescription());
        noteService.addNote(entity);
        return "redirect:/organizer/notes";
    }

    @GetMapping(path = "/organizer/notes/delete/{id}")
    public String deleteNote(@PathVariable("id") Long id) {
        if (noteRepository.findOne(id) == null) {
            return "noteNotFound";
        }
        noteRepository.delete(id);
        return "redirect:/organizer/notes";
    }
}


