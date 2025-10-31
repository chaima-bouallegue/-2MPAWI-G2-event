package tn.esprit.eventsproject.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@SpringBootTest
public class ParticipantServicesImplTest {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testAddParticipant() {
        log.info("Début du test d'ajout d'un participant");

        // Créer un participant
        Participant participant = new Participant();
        participant.setNom("Ben Ali");
        participant.setPrenom("Mohamed");
        participant.setTache(Tache.ORGANISATEUR);

        log.info("Sauvegarde du participant : {} {}", participant.getPrenom(), participant.getNom());
        Participant savedParticipant = participantRepository.save(participant);

        // Assertions
        log.info("Vérification des assertions");
        Assertions.assertNotNull(savedParticipant.getIdPart(), "L'ID du participant ne doit pas être nul");
        Assertions.assertEquals("Ben Ali", savedParticipant.getNom());
        Assertions.assertEquals("Mohamed", savedParticipant.getPrenom());
        Assertions.assertEquals(Tache.ORGANISATEUR, savedParticipant.getTache());

        log.info("Test d'ajout réussi - ID du participant : {}", savedParticipant.getIdPart());
    }

    @Test
    public void testFindParticipantById() {
        log.info("Début du test de recherche par ID");

        // Créer et sauvegarder un participant
        Participant participant = new Participant();
        participant.setNom("Trabelsi");
        participant.setPrenom("Fatma");
        participant.setTache(Tache.PARTICIPANT);

        Participant savedParticipant = participantRepository.save(participant);
        log.info("Participant sauvegardé avec l'ID : {}", savedParticipant.getIdPart());

        // Rechercher le participant
        log.info("Recherche du participant par ID");
        Participant foundParticipant = participantRepository.findById(savedParticipant.getIdPart()).orElse(null);

        // Assertions
        log.info("Vérification des assertions");
        Assertions.assertNotNull(foundParticipant, "Le participant doit être trouvé");
        Assertions.assertEquals(savedParticipant.getIdPart(), foundParticipant.getIdPart());
        Assertions.assertEquals("Trabelsi", foundParticipant.getNom());
        Assertions.assertEquals("Fatma", foundParticipant.getPrenom());

        log.info("Test de recherche par ID réussi");
    }

    @Test
    public void testAddParticipantWithEvents() {
        log.info("Début du test d'ajout d'un participant avec événements");

        // Créer un événement
        Event event = new Event();
        event.setDescription("Workshop Spring Boot");
        event.setDateDebut(LocalDate.of(2025, 12, 1));
        event.setDateFin(LocalDate.of(2025, 12, 2));
        event.setCout(1000f);

        Event savedEvent = eventRepository.save(event);
        log.info("Événement sauvegardé : {}", savedEvent.getDescription());

        // Créer un participant avec l'événement
        Participant participant = new Participant();
        participant.setNom("Gharbi");
        participant.setPrenom("Ahmed");
        participant.setTache(Tache.ORGANISATEUR);

        Set<Event> events = new HashSet<>();
        events.add(savedEvent);
        participant.setEvents(events);

        log.info("Sauvegarde du participant avec événement");
        Participant savedParticipant = participantRepository.save(participant);

        // Assertions
        log.info("Vérification des assertions");
        Assertions.assertNotNull(savedParticipant.getEvents(), "Les événements ne doivent pas être nuls");
        Assertions.assertEquals(1, savedParticipant.getEvents().size());

        log.info("Test d'ajout avec événements réussi - Nombre d'événements : {}",
                savedParticipant.getEvents().size());
    }

    @Test
    public void testFindAllParticipants() {
        log.info("Début du test de récupération de tous les participants");

        // Créer deux participants
        Participant participant1 = new Participant();
        participant1.setNom("Sassi");
        participant1.setPrenom("Sami");
        participant1.setTache(Tache.PARTICIPANT);

        Participant participant2 = new Participant();
        participant2.setNom("Mezni");
        participant2.setPrenom("Maha");
        participant2.setTache(Tache.ORGANISATEUR);

        participantRepository.save(participant1);
        participantRepository.save(participant2);
        log.info("Deux participants sauvegardés");

        log.info("Récupération de tous les participants");
        List<Participant> participants = participantRepository.findAll();

        // Assertions
        log.info("Vérification des assertions");
        Assertions.assertNotNull(participants, "La liste ne doit pas être nulle");
        Assertions.assertTrue(participants.size() >= 2, "Il doit y avoir au moins 2 participants");

        log.info("Test de récupération réussi - Nombre total de participants : {}", participants.size());
    }
}