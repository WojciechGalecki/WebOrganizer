package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import pl.sda.finalProject.myOrganizer.dao.INoteRepository;
import pl.sda.finalProject.myOrganizer.dao.ITaskRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.UserRole;
import pl.sda.finalProject.myOrganizer.model.UserModel;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private INoteRepository noteRepository;
    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserModel userModel) {
        createUserOrAdmin(userModel, UserRole.USER);
    }

    public void registerAdmin(UserModel userModel) {
        createUserOrAdmin(userModel, UserRole.ADMIN);
    }

    private void createUserOrAdmin(UserModel userModel, UserRole userRole) {

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

    @Transactional
    public void deleteUser(String email) {
        MyUser userToDelete = userRepository.findOne(email);
        noteRepository.deleteAllByUser(userToDelete);
        taskRepository.deleteAllByUser(userToDelete);
        userRepository.delete(userToDelete);
    }
}

