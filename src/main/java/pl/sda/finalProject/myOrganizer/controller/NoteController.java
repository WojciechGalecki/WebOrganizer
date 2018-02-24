package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Note;
import pl.sda.finalProject.myOrganizer.service.NoteService;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/organizer")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @GetMapping("/notes")
    public String showNotesPage(Model model) {
        Note newNote = new Note();
        model.addAttribute("newNote", newNote);
        return "notes";
    }

    @PostMapping("/notes")
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
}


