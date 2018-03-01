package pl.sda.finalProject.myOrganizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import pl.sda.finalProject.myOrganizer.entity.MyUser;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {

    @NotEmpty
    private String name;

    private LocalDate creationDate;

    @NotEmpty
    private String stringEventDate;

    private String stringEventTime;

    private String stringReminderDate;

    private String stringReminderTime;

    private MyUser user;
}
