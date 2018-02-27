package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {

    @GetMapping("/organizer/calendar")
    public String showCalendarPage(){
        return "calendar";
    }
}
