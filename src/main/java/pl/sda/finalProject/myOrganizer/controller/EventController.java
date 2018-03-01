package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.finalProject.myOrganizer.dao.IEventRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.Event;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.service.EventService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class EventController {

    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private EventService eventService;

    @GetMapping("/organizer/events")
    public String showEventsPage(Model model, Principal principal){
        Event newEvent = new Event();
        MyUser activeUser = userRepository.findOne(principal.getName());
        model.addAttribute("events", eventRepository.findByUser(activeUser));
        model.addAttribute("newEvent", newEvent);
        model.addAttribute("today", LocalDate.now());
        return "events";
    }

    @PostMapping(path = "organizer/events")
    public String addEvent(@Valid @ModelAttribute("newEvent") Event newEvent, BindingResult bindingResult,
                           Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validErrors", true);
            model.addAttribute("errorMsg",
                    "Adding the event failed, parameters name and date required!");
            return "events";
        }

        /*MyUser activeUser = userRepository.findOne(principal.getName());
        newEvent.setUser(activeUser);
        newEvent.setCreationDate(LocalDate.now());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        newEvent.setEventDate(LocalDate.parse(newEvent.getStringEventDate(), formatter));
        eventRepository.save(newEvent);*/

        return "redirect:/organizer/events";
    }
}
