package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Note;
import pl.sda.finalProject.myOrganizer.entity.Task;
import pl.sda.finalProject.myOrganizer.service.TaskService;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/organizer")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping("/tasks")
    public String showTasksPage(Model model, Principal principal){
        Task newTask = new Task();
        MyUser activeUser = userService.findUserByEmail(principal.getName());
        model.addAttribute("newTask", newTask);
        model.addAttribute("tasks",taskService.findTasksByUser(activeUser));
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addNote(@Valid @ModelAttribute("newTask") Task newTask, BindingResult bindingResult,
                          Principal principal) {
        MyUser activeUser = userService.findUserByEmail(principal.getName());
        newTask.setUser(activeUser);
        newTask.setCreationDate(LocalDate.now());
        if (bindingResult.hasErrors()) {
            return "redirect:/organizer/tasks";
        }
        taskService.addTask(newTask);
        return "redirect:/organizer/tasks";
    }
}
