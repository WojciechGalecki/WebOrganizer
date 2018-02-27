package pl.sda.finalProject.myOrganizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.finalProject.myOrganizer.entity.Event;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {
}
