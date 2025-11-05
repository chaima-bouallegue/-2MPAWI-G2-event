package tn.esprit.eventsproject.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.ParticipantRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ParticipantServicesImplMockTest {

    @Mock
    private ParticipantRepository participantRepository;

    private Participant participant1;
    private Event event1;

    @BeforeEach
    void setUp() {
        participant1 = new Participant();
        participant1.setNom("Ben Ali");
        participant1.setPrenom("Mohamed");
        participant1.setTache(Tache.ORGANISATEUR);

        event1 = new Event();
        event1.setIdEvent(1);
        event1.setDescription("Workshop Spring Boot");
        event1.setDateDebut(LocalDate.of(2025, 12, 1));
        event1.setDateFin(LocalDate.of(2025, 12, 2));
        event1.setCout(1000f);
    }

    @Test
    public void testAddParticipant() {
        log.info("Création d'un participant : Nom = {}, Prénom = {}", participant1.getNom(), participant1.getPrenom());

        // Simuler le comportement du repository
        Participant savedParticipant = new Participant();
        savedParticipant.setIdPart(1);
        savedParticipant.setNom("Ben Ali");
        savedParticipant.setPrenom("Mohamed");
        savedParticipant.setTache(Tache.ORGANISATEUR);

        when(participantRepository.save(participant1)).thenReturn(savedParticipant);

        log.info("Appel de la méthode participantRepository.save");
        Participant result = participantRepository.save(participant1);

        log.info("Vérification des assertions pour le participant sauvegardé");
        Assertions.assertNotNull(result.getIdPart(), "L'ID du participant ne doit pas être nul");
        Assertions.assertEquals("Ben Ali", result.getNom());
        Assertions.assertEquals("Mohamed", result.getPrenom());
        Assertions.assertEquals(Tache.ORGANISATEUR, result.getTache());

        verify(participantRepository, times(1)).save(participant1);
        log.info("Méthode participantRepository.save vérifiée avec succès");
    }

    @Test
    public void testFindParticipantById() {
        log.info("Test de recherche du participant par ID");

        Participant foundParticipant = new Participant();
        foundParticipant.setIdPart(1);
        foundParticipant.setNom("Ben Ali");
        foundParticipant.setPrenom("Mohamed");
        foundParticipant.setTache(Tache.ORGANISATEUR);

        when(participantRepository.findById(1)).thenReturn(Optional.of(foundParticipant));

        log.info("Appel de la méthode participantRepository.findById(1)");
        Optional<Participant> result = participantRepository.findById(1);

        log.info("Vérification des assertions");
        Assertions.assertTrue(result.isPresent(), "Le participant doit être présent");
        Assertions.assertEquals(1, result.get().getIdPart());
        Assertions.assertEquals("Ben Ali", result.get().getNom());

        verify(participantRepository, times(1)).findById(1);
        log.info("Méthode participantRepository.findById vérifiée avec succès");
    }

    @Test
    public void testAddParticipantWithEvents() {
        log.info("Test d'ajout d'un participant avec événements");

        Set<Event> events = new HashSet<>();
        events.add(event1);
        participant1.setEvents(events);

        Participant savedParticipant = new Participant();
        savedParticipant.setIdPart(1);
        savedParticipant.setNom("Ben Ali");
        savedParticipant.setPrenom("Mohamed");
        savedParticipant.setTache(Tache.ORGANISATEUR);
        savedParticipant.setEvents(events);

        when(participantRepository.save(participant1)).thenReturn(savedParticipant);

        log.info("Appel de la méthode participantRepository.save avec événements");
        Participant result = participantRepository.save(participant1);

        log.info("Vérification des assertions");
        Assertions.assertNotNull(result.getEvents(), "Les événements ne doivent pas être nuls");
        Assertions.assertEquals(1, result.getEvents().size());
        Assertions.assertTrue(result.getEvents().contains(event1));

        verify(participantRepository, times(1)).save(participant1);
        log.info("Test avec événements réussi");
    }

    @Test
    public void testFindAllParticipants() {
        log.info("Test de récupération de tous les participants");

        Participant participant2 = new Participant();
        participant2.setIdPart(2);
        participant2.setNom("Trabelsi");
        participant2.setPrenom("Fatma");
        participant2.setTache(Tache.PARTICIPANT);

        List<Participant> participants = Arrays.asList(participant1, participant2);
        when(participantRepository.findAll()).thenReturn(participants);

        log.info("Appel de la méthode participantRepository.findAll");
        List<Participant> result = participantRepository.findAll();

        log.info("Vérification des assertions");
        Assertions.assertNotNull(result, "La liste ne doit pas être nulle");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Ben Ali", result.get(0).getNom());

        verify(participantRepository, times(1)).findAll();
        log.info("Récupération de tous les participants réussie");
    }
}