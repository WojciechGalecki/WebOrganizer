package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping(path = "/users")
    public String showUsers(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("pages",userRepository.findAll(new PageRequest(page, 4)));
        model.addAttribute("currentPage", page);
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
