package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalProject.myOrganizer.dao.ITaskRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Task;
import pl.sda.finalProject.myOrganizer.service.TaskService;
import pl.sda.finalProject.myOrganizer.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/organizer/tasks")
    public String showTasksPage(Model model, Principal principal) {
        Task newTask = new Task();
        MyUser activeUser = userService.findUserByEmail(principal.getName());
        model.addAttribute("newTask", newTask);
        model.addAttribute("tasks", taskRepository.findByUser(activeUser));
        return "tasks";
    }

    @PostMapping("/organizer/tasks")
    public String addTask(@Valid @ModelAttribute("newTask") Task newTask, BindingResult bindingResult,
                          Principal principal, Model model) {
        MyUser activeUser = userService.findUserByEmail(principal.getName());
        newTask.setUser(activeUser);
        newTask.setCreationDate(LocalDate.now());
        if (bindingResult.hasErrors()) {
            model.addAttribute("validErrors", true);
            return "tasks";
        }
        taskService.addTask(newTask);
        return "redirect:/organizer/tasks";
    }

    @GetMapping(path = "/organizer/tasks/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {

        Task editTask = taskRepository.findOne(id);

        if (editTask == null) {
            return "taskNotFound";
        }

        model.addAttribute("edit", editTask);

        return "editTask";
    }

    @PostMapping(path = "/organizer/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, @Valid @ModelAttribute("edit") Task editTask,
                           BindingResult bindingResult) {

        Task entityTask = taskRepository.findOne(id);

        if (entityTask == null) {
            return "taskNotFound";
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/organizer/tasks/edit/{id}";
        }
        entityTask.setCreationDate(LocalDate.now());
        entityTask.setName(editTask.getName());
        entityTask.setPriority(editTask.getPriority());
        taskRepository.save(entityTask);

        return "redirect:/organizer/tasks";
    }

    @GetMapping(path = "organizer/tasks/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        if (taskRepository.findOne(id) == null) {
            return "taskNotFound";
        }
        taskRepository.delete(id);

        return "redirect:/organizer/tasks";
    }
}
