package tn.esprit.eventsproject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventServicesImpl implements IEventServices {

    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final LogisticsRepository logisticsRepository;

    @Override
    public Participant addParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    @Override
    public Event addAffectEvenParticipant(Event event, int idParticipant) {
        Participant participant = participantRepository.findById(idParticipant).orElse(null);
        if (participant != null) {
            if (participant.getEvents() == null) participant.setEvents(new HashSet<>());
            participant.getEvents().add(event);
        }
        return eventRepository.save(event);
    }

    @Override
    public Event addAffectEvenParticipant(Event event) {
        Set<Participant> participants = event.getParticipants();
        if (participants != null) {
            for (Participant aParticipant : participants) {
                Participant participant = participantRepository.findById(aParticipant.getIdPart()).orElse(null);
                if (participant != null) {
                    if (participant.getEvents() == null) participant.setEvents(new HashSet<>());
                    participant.getEvents().add(event);
                }
            }
        }
        return eventRepository.save(event);
    }

    @Override
    public Logistics addAffectLog(Logistics logistics, String descriptionEvent) {
        System.out.println("Execution du service : ajout/logistique pour l'événement -> " + descriptionEvent);

        Event event = eventRepository.findByDescription(descriptionEvent);

        if (event.getLogistics() == null) {
            Set<Logistics> logisticsSet = new HashSet<>();
            logisticsSet.add(logistics);
            event.setLogistics(logisticsSet);
            eventRepository.save(event);
        } else {
            event.getLogistics().add(logistics);
        }

        return logisticsRepository.save(logistics);
    }

    @Override
    public List<Logistics> getLogisticsDates(LocalDate dateDebut, LocalDate dateFin) {
        List<Event> events = eventRepository.findByDateDebutBetween(dateDebut, dateFin);
        List<Logistics> logisticsList = new ArrayList<>();

        for (Event event : events) {
            Set<Logistics> logisticsSet = event.getLogistics();
            if (logisticsSet != null) {
                for (Logistics logistics : logisticsSet) {
                    if (logistics.isReserve()) logisticsList.add(logistics);
                }
            }
        }
        return logisticsList;
    }

    @Scheduled(cron = "*/60 * * * * *")
    @Override
    public void calculCout() {
        List<Event> events = eventRepository.findByParticipants_NomAndParticipants_PrenomAndParticipants_Tache(
                "Tounsi", "Ahmed", Tache.ORGANISATEUR);

        for (Event event : events) {
            float somme = 0f;
            log.info("Event: {}", event.getDescription());
            Set<Logistics> logisticsSet = event.getLogistics();

            if (logisticsSet != null) {
                for (Logistics logistics : logisticsSet) {
                    if (logistics.isReserve())
                        somme += logistics.getPrixUnit() * logistics.getQuantite();
                }
            }

            event.setCout(somme);
            eventRepository.save(event);
            log.info("Cout de l'Event {} est {}", event.getDescription(), somme);
        }
    }
}
