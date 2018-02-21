package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.finalProject.myOrganizer.model.MyUser;
import pl.sda.finalProject.myOrganizer.service.UserService;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/organizer")
    public String showHomePage() {
        return "organizer";
    }

    @GetMapping("/organizer/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("MyUser", new MyUser());
        return "register";
    }

    @PostMapping("/organizer/register")
    public String registerUser(@Valid @ModelAttribute("MyUser") MyUser user, BindingResult bindingResult) {
        if (userService.isUserExist(user.getEmail())) {
            bindingResult.addError(new FieldError("MyUser","email", user.getEmail(),
                    false, new String[] {"userExists"}, new Object[]{}, "User with this email already exists!"));
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }



        userService.addUser(user);
        return "success";
    }
}
