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
@CrossOrigin("*")
public class EventRestController {

    private final IEventServices eventServices;

    // ✅ Ajouter un participant
    @PostMapping("/participants")
    public Participant addParticipant(@RequestBody Participant participant) {
        return eventServices.addParticipant(participant);
    }

    // ✅ Ajouter un événement et l'affecter à un participant
    @PostMapping("/participants/{participantId}/events")
    public Event addEventToParticipant(@RequestBody Event event, @PathVariable int participantId) {
        return eventServices.addAffectEvenParticipant(event, participantId);
    }

    // ✅ Ajouter un événement sans affectation
    @PostMapping
    public Event addEvent(@RequestBody Event event) {
        return eventServices.addAffectEvenParticipant(event);
    }

    // ✅ Affecter une logistique à un événement
    @PutMapping("/events/{description}/logistics")
    public Logistics addLogisticsToEvent(@RequestBody Logistics logistics,
                                         @PathVariable String description) {
        return eventServices.addAffectLog(logistics, description);
    }

    // ✅ Récupérer les logistiques entre deux dates
    @GetMapping("/logistics/{startDate}/{endDate}")
    public List<Logistics> getLogisticsBetweenDates(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return eventServices.getLogisticsDates(startDate, endDate);
    }
}
