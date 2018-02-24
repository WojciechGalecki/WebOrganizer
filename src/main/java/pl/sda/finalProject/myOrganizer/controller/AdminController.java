package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.UserRole;
import pl.sda.finalProject.myOrganizer.model.UserModel;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;

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
        model.addAttribute("pages", userRepository.findAll(new PageRequest(page, 4)));
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

    @GetMapping("/users/edit")
    public String showEditForm(@RequestParam("email") String email, Model model) {
        MyUser editedUser = userService.findUserByEmail(email);
        if (editedUser == null) {
            return "userNotFound";
        }
        UserModel editedModel = new UserModel(editedUser);
        model.addAttribute("edited", editedModel);
        return "redirect:/organizer/users";
    }

    @PostMapping("users/edit")
    public String editUser(@Valid @ModelAttribute("edited") UserModel editedModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/edit";
        }
        MyUser editedUser = userService.findUserByEmail(editedModel.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        editedUser.setUserName(editedModel.getUserName());
        editedUser.setPassword(passwordEncoder.encode(editedModel.getPassword()));
        editedUser.setUserRole(editedModel.getUserRole());
        userRepository.save(editedUser);
        return "edit";
    }
}
