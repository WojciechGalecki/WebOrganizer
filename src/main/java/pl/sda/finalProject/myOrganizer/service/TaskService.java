package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.ITaskRepository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.PriorityType;
import pl.sda.finalProject.myOrganizer.entity.Task;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private ITaskRepository taskRepository;

    public void addTask(Task task) {
        task.setCreationDate(LocalDate.now());
        taskRepository.save(task);
    }

    public List<Task> findTasksByUser(MyUser user) {
        return taskRepository.findByUser(user);
    }

    /*@Transactional
    public List<Task> sortUserTasksByPriority(MyUser user) {

        List<Task> tasksToSort = taskRepository.findByUser(user);

        for (Task task : tasksToSort) {
            if (task.getPriority().equals(PriorityType.NONE)) {
                task.setPriorityValue(0);
            }
            if (task.getPriority().equals(PriorityType.LOW)) {
                task.setPriorityValue(1);
            }
            if (task.getPriority().equals(PriorityType.MEDIUM)) {
                task.setPriorityValue(2);
            }
            if (task.getPriority().equals(PriorityType.HIGH)) {
                task.setPriorityValue(3);
            }
            taskRepository.save(task);
        }

        return taskRepository.orderByPriorityValueDesc(tasksToSort);
    }*/
}

