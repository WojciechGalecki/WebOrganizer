package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.IEventRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.Event;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.model.EventModel;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private IUserRepository userRepository;
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

    public List<Event> showCurrentEvents(MyUser activeUser) {
        List<Event> events = eventRepository.findByUserOrderByEventDateAsc(activeUser);

       List<Event> currentEvents = events.stream().filter(event ->
               event.getEventDate().isAfter(LocalDate.now()) || event.getEventDate().isEqual(LocalDate.now()))
               .collect(Collectors.toList());
       return currentEvents;
    }

    public List<String> showReminderForEvents(MyUser activeUser) {
        List<String> eventsToReminder = new ArrayList<>();

        List<Event> events = eventRepository.findAllByUser(activeUser);

        for (Event event : events) {
            if (event.getEventDate().equals(LocalDate.now())) {
                eventsToReminder.add(event.getName());
            }
        }
        return eventsToReminder;
    }
}
