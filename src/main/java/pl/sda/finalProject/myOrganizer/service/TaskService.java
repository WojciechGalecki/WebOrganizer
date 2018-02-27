package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.ITaskRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Task;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;

    public void addTask(Task task){
        task.setCreationDate(LocalDate.now());
        taskRepository.save(task);
    }
}
