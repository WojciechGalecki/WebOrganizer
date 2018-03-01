package pl.sda.finalProject.myOrganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalProject.myOrganizer.dao.IEventRepository;
import pl.sda.finalProject.myOrganizer.model.EventModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        if(!eventModel.getStringEventTime().isEmpty()){
            eventModel.setEventTime(LocalTime.parse(eventModel.getStringEventTime(), timeFormatter));
        }
    }

    public String parseEventDate(LocalDate localDate){
        return localDate.format(dateFormatter);
    }

    public String parseEventTime(LocalTime localTime){
        return localTime.format(timeFormatter);
    }
}
