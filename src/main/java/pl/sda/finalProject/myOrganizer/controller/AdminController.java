package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.model.UserModel;
import pl.sda.finalProject.myOrganizer.service.UserService;

@Controller
@RequestMapping("/organizer")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam("email") String email) {
        if (userService.findUserByEmail(email) == null) {
            return "userNotFound";
        }
        userService.deleteUser(email);
        return "redirect:/organizer/users";
    }


}
