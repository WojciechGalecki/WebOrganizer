package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.finalProject.myOrganizer.model.MyUser;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/organizer")
    public String showHomePage(Model model) {
       /* MyUser user = MyUser.builder()
                .userName("Antek")
                .email("antek@gmail.com")
                .password("12345").build();
        MyUser user2 = MyUser.builder()
                .userName("Barbara")
                .email("baska@wp.pl")
                .password("12345").build();
        userService.addUser(user);
        userService.addUser(user2);*/
        model.addAttribute("users", userService.findAllUsers());
        return "organizer";
    }

    @GetMapping("/organizer/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new MyUser());
        return "register";
    }

    @PostMapping("organizer/register")
    public String registerUser(@Valid MyUser user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.isUserExist(user.getEmail())) {
            model.addAttribute("userExist", true);
            return "register";
        }
        userService.addUser(user);
        return "success";
    }
}
