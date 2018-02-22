package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/organizer/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("MyUser", new MyUser());
        return "users";
    }

   @PostMapping("/organizer/users")
    public String deleteUser(MyUser user) {
       // roleService.deleteRoleByUserEmail(user.getEmail());
        userService.deleteUser(user);
        return "users";
    }

    /*@PostMapping("/organizer/users")
    public String editUser(MyUser user) {
        MyUser editedUser = userRepository.findOne(user.getEmail());
        editedUser = MyUser.builder()
                .password(user.getPassword())
                .userName(user.getUserName())
                .roles(user.getRoles()).build();
        return "users";
    }*/
}
