package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.finalProject.myOrganizer.service.UserService;

@Controller
@RequestMapping("/organizer")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showHomePage(){
        return "organizer";
    }
}
