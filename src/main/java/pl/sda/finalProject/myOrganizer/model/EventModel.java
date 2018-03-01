package pl.sda.finalProject.myOrganizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.finalProject.myOrganizer.entity.Event;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.service.EventService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {

    private Long id;

    @NotEmpty
    private String name;

    private LocalDate creationDate;

    @NotEmpty
    private String stringEventDate;
    private LocalDate eventDate;

    private String stringEventTime;
    private LocalTime eventTime;

    @Min(0L)
    @Max(59L)
    private int minutesBefore;

    @Min(0L)
    @Max(23L)
    private int hoursBefore;

    @Min(0L)
    @Max(366L)
    private int daysBefore;

    private MyUser user;

    public EventModel(Event eventEntity){
        this.id = eventEntity.getId();
        this.name = eventEntity.getName();
        this.creationDate = eventEntity.getCreationDate();
        this.eventDate = eventEntity.getEventDate();
        this.eventTime = eventEntity.getEventTime();
        this.minutesBefore = eventEntity.getMinutesBefore();
        this.hoursBefore = eventEntity.getHoursBefore();
        this.daysBefore = eventEntity.getDaysBefore();
        this.user = eventEntity.getUser();
    }
}
