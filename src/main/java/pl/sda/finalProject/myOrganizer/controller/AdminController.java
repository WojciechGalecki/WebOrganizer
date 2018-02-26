package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.UserRole;
import pl.sda.finalProject.myOrganizer.model.UserModel;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/organizer/users")
    public String showUsers(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("pages", userRepository.findAll(new PageRequest(page, 4)));
        model.addAttribute("currentPage", page);
        return "users";
    }

    @PostMapping("organizer/users/delete")
    public String deleteUser(@RequestParam("email") String email) {
        if (userService.findUserByEmail(email) == null) {
            return "userNotFound";
        }
        userService.deleteUser(email);
        return "redirect:/organizer/users";
    }

   /* @GetMapping("organizer/users/edit")
    @ResponseBody
    public MyUser findUser(String email) {
        return userService.findUserByEmail(email);
    }*/

    @PostMapping("organizer/users/edit")
    //@ResponseBody
    public String test(@Validated @ModelAttribute String userName, String email, String password, String userRole, BindingResult b){
        if(b.hasErrors()){
            return "redirect:/users/edit";
        }

        MyUser user = MyUser.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .userRole(UserRole.valueOf(userRole)).build();
        userRepository.save(user);
        return "redirect:/organizer/users";
    }

}

   
