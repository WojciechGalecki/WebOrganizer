package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.model.UserModel;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/organizer/users")
    public String showUsers(Model model, Pageable pageRequest) {
        Page<MyUser> page = userRepository.findAll(pageRequest);
        model.addAttribute("pages", page);
        model.addAttribute("users", page.getContent());
        return "users";
    }

    @GetMapping(path = "/organizer/users/edit/{email}")
    public String showEditForm(@PathVariable("email") String email, Model model) {
        MyUser editUser = userRepository.findOne(email);

        if (editUser == null) {
            return "userNotFound";
        }

        UserModel editModel = new UserModel(editUser);
        model.addAttribute("edit", editModel);

        return "editUser";
    }

    @PostMapping(path = "/organizer/users/edit/{email}")
    public String editUser(@PathVariable("email") String email, @Valid @ModelAttribute("edit") UserModel editModel,
                           BindingResult bindingResult) {

        MyUser entity = userRepository.findOne(email);

        if (entity == null) {
            return "userNotFound";
        }
        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        entity.setUserName(editModel.getUserName());
        entity.setPassword(passwordEncoder.encode(editModel.getPassword()));
        entity.setUserRole(editModel.getUserRole());

        userRepository.save(entity);

        return "redirect:/organizer/users";
    }

    @GetMapping(path = "/organizer/users/delete/{email}")
    public String deleteUser(@PathVariable("email") String email) {
        if (userRepository.findOne(email) == null) {
            return "userNotFound";
        }
        userService.deleteUser(email);

        return "redirect:/organizer/users";
    }
}
