package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.finalProject.myOrganizer.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/organizer/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }
}
