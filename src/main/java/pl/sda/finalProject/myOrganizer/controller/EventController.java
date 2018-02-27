package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.finalProject.myOrganizer.dao.IEventRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.Event;
import pl.sda.finalProject.myOrganizer.entity.MyUser;

import java.security.Principal;

@Controller
public class EventController {

    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/organizer/events")
    public String showEventsPage(Model model, Principal principal){
        Event newEvent = new Event();
        MyUser activeUser = userRepository.findOne(principal.getName());
        model.addAttribute("events",)
        return "events";
    }
}
