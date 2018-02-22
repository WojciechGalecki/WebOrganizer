package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.UserRole;
import pl.sda.finalProject.myOrganizer.model.UserModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public void registerUser(UserModel userModel) {
        createUserOrAdmin(userModel, UserRole.USER);
    }

    public void registerAdmin(UserModel userModel) {
        createUserOrAdmin(userModel, UserRole.ADMIN);
    }

    private void createUserOrAdmin(UserModel userModel, UserRole userRole) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        MyUser newUser = MyUser.builder()
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .userName(userModel.getUserName())
                .userRole(userRole).build();

        userRepository.save(newUser);
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

