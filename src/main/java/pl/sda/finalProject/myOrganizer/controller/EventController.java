package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @GetMapping("/organizer/events")
    public String showEventsPage(){
        return "events";
    }
}
