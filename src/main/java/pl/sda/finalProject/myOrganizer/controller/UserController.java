package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.model.UserModel;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/organizer")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showHomePage() {
        return "organizer";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        UserModel newUser = new UserModel();
        model.addAttribute("newUser", newUser);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("newUser") UserModel newUser, BindingResult bindingResult) {

        if (userService.isUserExist(newUser.getEmail())) {
            bindingResult.addError(new FieldError("newUser", "email", newUser.getEmail(),
                    false, new String[]{"userExists"}, new Object[]{},
                    "User with this email already exists!"));
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerUser(newUser);
        return "success";
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal){
        MyUser activeUser = userService.findUserByEmail(principal.getName());
        model.addAttribute("activeUser", activeUser);
        return "profile";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        UserModel newUser = new UserModel();
        model.addAttribute("newUser", newUser);
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/login-failure")
    public String loginFailure() {
        return "login-failure";
    }
}
