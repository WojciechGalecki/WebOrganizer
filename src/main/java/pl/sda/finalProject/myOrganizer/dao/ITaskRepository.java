package pl.sda.finalProject.myOrganizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.entity.Task;
import pl.sda.finalProject.myOrganizer.entityAttributes.PriorityType;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {

    void deleteAllByUser(MyUser user);
    List<Task> findByUserOrderByPriorityValueAsc(MyUser user);

}
