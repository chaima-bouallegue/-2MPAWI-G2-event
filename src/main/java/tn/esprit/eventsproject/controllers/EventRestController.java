package tn.esprit.eventsproject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventRestController {

    private final IEventServices eventServices;

    @PostMapping("/addPart")
    public Participant addParticipant(@RequestBody Participant participant) {
        return eventServices.addParticipant(participant);
    }

    @PostMapping("/addEvent/{id}")
    public Event addEventPart(@RequestBody Event event, @PathVariable("id") int idPart) {
        return eventServices.addAffectEvenParticipant(event, idPart);
    }

    @PutMapping("/addAffectLog/{description}")
    public Logistics addAffectLog(@RequestBody Logistics logistics, @PathVariable("description") String descriptionEvent) {
        return eventServices.addAffectLog(logistics, descriptionEvent);
    }

    @GetMapping("/getLogs/{d1}/{d2}")
    public List<Logistics> getLogisticsDates(
            @PathVariable("d1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @PathVariable("d2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return eventServices.getLogisticsDates(dateDebut, dateFin);
    }
}