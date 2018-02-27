package pl.sda.finalProject.myOrganizer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.finalProject.myOrganizer.entity.Reminder;

@Repository
public interface IReminderRepository extends JpaRepository<Reminder, Long> {
}
