package pl.sda.finalProject.myOrganizer.service;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.IEventRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.Event;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.model.EventModel;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private DateTimeFormatter dateFormatter;
    @Autowired
    private DateTimeFormatter timeFormatter;

    public void parseEventDateAndTime(EventModel eventModel) {
        // eventDate required
        eventModel.setEventDate(LocalDate.parse(eventModel.getStringEventDate(), dateFormatter));
        // optional
        if (!eventModel.getStringEventTime().isEmpty()) {
            eventModel.setEventTime(LocalTime.parse(eventModel.getStringEventTime(), timeFormatter));
        }
    }

    public String parseEventDate(LocalDate localDate) {
        return localDate.format(dateFormatter);
    }

    public String parseEventTime(LocalTime localTime) {
        return localTime.format(timeFormatter);
    }

    public List<Event> getCurrentEvents(MyUser activeUser) {
        List<Event> events = eventRepository.findByUserOrderByEventDateAsc(activeUser);

        for (Iterator<Event> iterator = events.iterator(); iterator.hasNext(); ) {
            Event event = iterator.next();
            if (event.getEventDate().isBefore(LocalDate.now())) {
                eventRepository.delete(event);
                iterator.remove();
            }
        }
        return events;
    }

    public List<Event> getEventsToRemind(MyUser activeUser) {
        // case Days Before Reminder
        // TODO: create method for all cases!!!
        return eventRepository.findAllByUser(activeUser).stream().filter(
               // event -> event.getDaysBefore() > 0 && event.getEventDate().
                        //minusDays((long) event.getDaysBefore()).isEqual(LocalDate.now())
                event -> isEventToRemind(event)
        ).collect(Collectors.toList());
    }

    private boolean isEventToRemind(Event event){
        if(event.getDaysBefore() > 0 && event.getEventDate().
                minusDays((long) event.getDaysBefore()).isEqual(LocalDate.now())){
            return true;
        } else return false;
    }

}
