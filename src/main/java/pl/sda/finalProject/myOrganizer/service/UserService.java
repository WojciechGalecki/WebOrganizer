package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.model.MyUser;
import pl.sda.finalProject.myOrganizer.model.Role;
import pl.sda.finalProject.myOrganizer.model.UserRoleType;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public void addUser(MyUser user) {
        createUserOrAdmin(user, UserRoleType.USER);
    }

    public void addAdmin(MyUser user) {
        createUserOrAdmin(user, UserRoleType.ADMIN);
    }

    private void createUserOrAdmin(MyUser user, UserRoleType roleType) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = Role.builder()
                .userRoleType(roleType).build();
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public MyUser findUserByEmail(String email) {
        return userRepository.findOne(email);
    }

    public List<MyUser> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean isUserExist(String email) {
        MyUser user = userRepository.findOne(email);
        if (user != null) {
            return true;
        } else return false;
    }
}

