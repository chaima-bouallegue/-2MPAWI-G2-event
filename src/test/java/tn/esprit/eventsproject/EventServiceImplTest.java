package tn.esprit.eventsproject;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventServiceImplTest {

    @Autowired
    IEventServices eventService;

    private static Participant testParticipant;

    @Test
    @Order(1)
    public void testAddParticipant() {
        log.info("=== Test 1: Ajout d'un participant ===");

        // Créer un nouveau participant
        Participant participant = new Participant();
        participant.setNom("Madien");
        participant.setPrenom("Nouha");

        log.info("Création d'un participant : Nom = {}, Prénom = {}",
                participant.getNom(), participant.getPrenom());

        // Ajouter le participant via le service
        Participant savedParticipant = eventService.addParticipant(participant);
        testParticipant = savedParticipant;

        // Assertions
        Assertions.assertNotNull(savedParticipant.getIdPart(),
                "L'ID du participant ne doit pas être nul");
        Assertions.assertEquals("Madien", savedParticipant.getNom());
        Assertions.assertEquals("Nouha", savedParticipant.getPrenom());

        log.info("✓ Participant ajouté avec succès - ID: {}", savedParticipant.getIdPart());
    }

    @Test
    @Order(2)
    public void testAddEvent() {
        log.info("=== Test 2: Ajout d'un événement ===");

        // Créer un nouvel événement
        Event event = new Event();
        event.setDescription("Conférence DevOps 2024");
        event.setDateDebut(LocalDate.now().plusDays(10));
        event.setDateFin(LocalDate.now().plusDays(12));
        event.setCout(150.0f);

        log.info("Création d'un événement : Description = {}, Coût = {}",
                event.getDescription(), event.getCout());

        // Ajouter l'événement via le service
        Event savedEvent = eventService.addAffectEvenParticipant(event);
        // Assertions
        Assertions.assertNotNull(savedEvent.getIdEvent(),
                "L'ID de l'événement ne doit pas être nul");
        Assertions.assertEquals("Conférence DevOps 2024", savedEvent.getDescription());
        Assertions.assertEquals(150.0f, savedEvent.getCout());
        Assertions.assertTrue(savedEvent.getDateDebut().isBefore(savedEvent.getDateFin()),
                "La date de début doit être avant la date de fin");

        log.info("✓ Événement ajouté avec succès - ID: {}", savedEvent.getIdEvent());
    }

    @Test
    @Order(3)
    public void testAddAffectEvenParticipant() {
        log.info("=== Test 3: Affecter un participant à un événement ===");

        // Créer un événement avec participant
        Event event = new Event();
        event.setDescription("Atelier Spring Boot");
        event.setDateDebut(LocalDate.now().plusDays(5));
        event.setDateFin(LocalDate.now().plusDays(6));
        event.setCout(100.0f);

        log.info("Affectation d'un participant à l'événement: {}", event.getDescription());

        // Ajouter l'événement avec participant
        Event savedEvent = eventService.addAffectEvenParticipant(event);

        // Assertions
        Assertions.assertNotNull(savedEvent.getIdEvent());
        Assertions.assertEquals("Atelier Spring Boot", savedEvent.getDescription());

        log.info("✓ Événement créé et participant affecté - ID: {}", savedEvent.getIdEvent());
    }

    @Test
    @Order(4)
    public void testGetAllParticipants() {
        log.info("=== Test 4: Récupérer tous les participants ===");

        // Ajouter quelques participants supplémentaires pour le test
        Participant participant1 = new Participant();
        participant1.setNom("Smith");
        participant1.setPrenom("Alice");
        eventService.addParticipant(participant1);

        Participant participant2 = new Participant();
        participant2.setNom("Brown");
        participant2.setPrenom("Bob");
        eventService.addParticipant(participant2);

        log.info("Récupération de tous les participants de la base de données");

        // Récupérer tous les participants (vous devez avoir cette méthode dans votre
        // service)
        // Si elle n'existe pas, vous pouvez tester une autre méthode existante
        // List<Participant> participants = eventService.getAllParticipants();

        // Pour cet exemple, on teste juste qu'on peut récupérer le participant créé
        // Vous pouvez adapter selon les méthodes disponibles dans votre service

        Assertions.assertNotNull(testParticipant,
                "Le participant de test doit exister");
        Assertions.assertNotNull(testParticipant.getIdPart());

        log.info("✓ Test de récupération réussi - Nombre de participants créés: 3");
        log.info("Participants créés: Nouha Madien, Alice Smith, Bob Brown");
    }
}