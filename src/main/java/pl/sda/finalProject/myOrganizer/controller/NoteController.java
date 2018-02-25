package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.INoteRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Note;
import pl.sda.finalProject.myOrganizer.service.NoteService;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;
    @Autowired
    private INoteRepository noteRepository;

    @GetMapping("/organizer/notes")
    public String showNotesPage(Model model, Principal principal) {
        Note newNote = new Note();
        MyUser activeUser = userService.findUserByEmail(principal.getName());
        model.addAttribute("newNote", newNote);
        model.addAttribute("notes", noteService.findNotesByUser(activeUser));
        return "notes";
    }

    @PostMapping("/organizer/notes")
    public String addNote(@Valid @ModelAttribute("newNote") Note newNote, BindingResult bindingResult,
                          Principal principal) {
        MyUser activeUser = userService.findUserByEmail(principal.getName());
        newNote.setUser(activeUser);
        newNote.setCreationDate(LocalDate.now());
        if (bindingResult.hasErrors()) {
            return "redirect:/organizer/notes";
        }
        noteService.addNote(newNote);
        return "redirect:/organizer/notes";
    }

    @GetMapping("/organizer/notes/edit")
    public String prepareToEdit(Model model){
        Note newNote = new Note();
        model.addAttribute("editNote", newNote);
        return "redirect:/organizer/notes";
    }

    @PostMapping("/organizer/notes/edit")
    public String editNote(@Valid @ModelAttribute("editNote") Note editNote, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/organizer/notes/edit";
        }
        noteService.addNote(editNote);
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


