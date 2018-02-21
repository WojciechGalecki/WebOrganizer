package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.model.MyUser;

@Controller
public class AdminController {

    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/organizer/users")
    public String showUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("MyUser", new MyUser());
        return "users";
    }

    @PostMapping("/organizer/users")
    public String deleteUser(MyUser user) {
        userRepository.delete(user);
        return "users";
    }

    @PostMapping("/organizer/users")
    public String editUser(MyUser user) {
        MyUser editedUser = userRepository.findOne(user.getEmail());
        editedUser = MyUser.builder()
                .password(user.getPassword())
                .userName(user.getUserName())
                .roles(user.getRoles()).build();
        return "users";
    }
}
