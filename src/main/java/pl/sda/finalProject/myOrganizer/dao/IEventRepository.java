package pl.sda.finalProject.myOrganizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.finalProject.myOrganizer.entity.Event;
import pl.sda.finalProject.myOrganizer.entity.MyUser;

import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {

    List<Event> findByUserOrderByEventDateAsc(MyUser user);
    void deleteAllByUser(MyUser user);

}
