package pl.sda.finalProject.myOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.finalProject.myOrganizer.dao.IEventRepository;
import pl.sda.finalProject.myOrganizer.dao.IUserRepository;
import pl.sda.finalProject.myOrganizer.entity.Event;
import pl.sda.finalProject.myOrganizer.entity.MyUser;
import pl.sda.finalProject.myOrganizer.model.EventModel;
import pl.sda.finalProject.myOrganizer.service.EventService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class EventController {

    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private EventService eventService;

    @GetMapping("/organizer/events")
    public String showEventsPage(Model model, Principal principal){
        EventModel newEvent = new EventModel();
        MyUser activeUser = userRepository.findOne(principal.getName());
        model.addAttribute("events", eventService.showCurrentEvents(activeUser));
        model.addAttribute("newEvent", newEvent);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("currentEvents", eventService.showReminderForEvents(activeUser));
        return "events";
    }

    @PostMapping(path = "organizer/events")
    public String addEvent(@Valid @ModelAttribute("newEvent") EventModel newEvent, BindingResult bindingResult,
                           Principal principal, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validErrors", true);
            model.addAttribute("errorMsg",
                    "Adding the event failed, parameters name and date required!");
            return "events";
        }

        MyUser activeUser = userRepository.findOne(principal.getName());
        eventService.parseEventDateAndTime(newEvent);

        Event entity = Event.builder()
                .creationDate(LocalDate.now())
                .user(activeUser)
                .name(newEvent.getName())
                .eventDate(newEvent.getEventDate())
                .eventTime(newEvent.getEventTime())
                .minutesBefore(newEvent.getMinutesBefore())
                .hoursBefore(newEvent.getHoursBefore())
                .daysBefore(newEvent.getDaysBefore())
                .build();

        eventRepository.save(entity);

        return "redirect:/organizer/events";
    }

    @GetMapping(path = "/organizer/events/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){

        Event editEvent = eventRepository.findOne(id);

        if(editEvent == null){
            return "eventNotFound";
        }


        EventModel editModel = new EventModel(editEvent);
        editModel.setStringEventDate(eventService.parseEventDate(editEvent.getEventDate()));
        if(editEvent.getEventTime() != null) {
            editModel.setStringEventTime(eventService.parseEventTime(editEvent.getEventTime()));
        }
        model.addAttribute("edit", editModel);
        model.addAttribute("today", LocalDate.now());

        return "editEvent";
    }

    @PostMapping(path = "/organizer/events/edit/{id}")
    public String editEvent(@PathVariable("id") Long id, @Valid @ModelAttribute("edit") EventModel eventModel,
                            BindingResult bindingResult){

        Event entity = eventRepository.findOne(id);

        if(entity == null){
            return "eventNotFound";
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/organizer/events/edit/{id}";
        }

        eventService.parseEventDateAndTime(eventModel);

        entity.setCreationDate(LocalDate.now());
        entity.setEventDate(eventModel.getEventDate());
        entity.setName(eventModel.getName());
        entity.setEventTime(eventModel.getEventTime());
        entity.setMinutesBefore(eventModel.getMinutesBefore());
        entity.setHoursBefore(eventModel.getHoursBefore());
        entity.setDaysBefore(eventModel.getDaysBefore());

        eventRepository.save(entity);

        return "redirect:/organizer/events";
    }

    @GetMapping(path = "/organizer/events/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id){

        if(eventRepository.findOne(id) == null){
            return "eventNotFound";
        }

        eventRepository.delete(id);

        return "redirect:/organizer/events";
    }
}
