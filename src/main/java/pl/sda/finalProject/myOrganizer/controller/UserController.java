package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.model.UserModel;
import pl.sda.finalProject.myOrganizer.service.EventService;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EventService eventService;

    @GetMapping("/organizer")
    public String showHomePage(Model model) {
        return "organizer";
    }

    @GetMapping("/organizer/register")
    public String showRegisterForm(Model model) {
        UserModel newUser = new UserModel();
        model.addAttribute("newUser", newUser);
        return "register";
    }

    @PostMapping("/organizer/register")
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

    @GetMapping("/organizer/profile")
    public String showProfilePage(Model model, Principal principal){
        MyUser activeUser = userRepository.findOne(principal.getName());
        UserModel activeModel = new UserModel(activeUser);
        model.addAttribute("activeModel", activeModel);
        return "profile";
    }

    @PostMapping("/organizer/profile")
    public String deleteUserAccount(@RequestParam("email") String email){
        userService.deleteUser(email);
        return "redirect:/organizer/register";
    }

    @GetMapping(path = "/organizer/profile/editPassword/{email}")
    public String showEditUserPasswordForm(@PathVariable("email") String email, Model model){

        MyUser user = userRepository.findOne(email);

        if(user == null){
            return "userNotFound";
        }

        UserModel activeModel = new UserModel(user);
        model.addAttribute("edit", activeModel);

        return "editPassword";
    }

    @PostMapping(path = "/organizer/profile/editPassword/{email}")
    public String editUserPassword(@PathVariable("email") String email,
                                   @Valid @ModelAttribute("edit") UserModel activeModel, BindingResult bindingResult){

        MyUser entity = userRepository.findOne(email);

        if(entity == null){
            return "userNotFound";
        }
        if(bindingResult.hasFieldErrors("password")){
            return "editPassword";
        }

        entity.setPassword(passwordEncoder.encode(activeModel.getPassword()));
        userRepository.save(entity);

        return "redirect:/organizer/login";
    }

    @GetMapping("/organizer/login")
    public String showLoginForm() {
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
