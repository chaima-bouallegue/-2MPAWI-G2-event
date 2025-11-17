package tn;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires avec Mockito pour EventServicesImpl
 * Ces tests utilisent des mocks pour isoler la logique métier
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventServiceImplTestMock {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServicesImpl eventService;

    private Participant mockParticipant;
    private Event mockEvent;

    @BeforeEach
    public void setUp() {
        log.info("Initialisation des objets mock avant chaque test");

        // Initialiser un participant mock
        mockParticipant = new Participant();
        mockParticipant.setIdPart(1);
        mockParticipant.setNom("Madien");
        mockParticipant.setPrenom("Nouha");

        // Initialiser un événement mock
        mockEvent = new Event();
        mockEvent.setIdEvent(1);
        mockEvent.setDescription("Conférence DevOps 2024");
        mockEvent.setDateDebut(LocalDate.now().plusDays(10));
        mockEvent.setDateFin(LocalDate.now().plusDays(12));
        mockEvent.setCout(200.0f);
        mockEvent.setParticipants(new HashSet<>());
    }

    @Test
    @Order(1)
    @DisplayName("Test 1: Ajout d'un participant avec mock")
    public void testAddParticipant() {
        log.info("=== Test Mock 1: Ajout d'un participant ===");

        // Arrange - Créer un participant à ajouter
        Participant newParticipant = new Participant();
        newParticipant.setNom("Madien");
        newParticipant.setPrenom("Nouha");

        log.info("Création d'un participant : Nom = {}, Prénom = {}",
                newParticipant.getNom(), newParticipant.getPrenom());

        // Simuler le comportement du repository
        when(participantRepository.save(any(Participant.class))).thenReturn(mockParticipant);

        // Act - Appeler la méthode du service
        Participant result = eventService.addParticipant(newParticipant);

        // Assert
        assertNotNull(result, "Le participant retourné ne doit pas être nul");
        assertNotNull(result.getIdPart(), "L'ID du participant ne doit pas être nul");
        assertEquals(1, result.getIdPart(), "L'ID doit être 1");
        assertEquals("Madien", result.getNom(), "Le nom doit être 'Madien'");
        assertEquals("Nouha", result.getPrenom(), "Le prénom doit être 'Nouha'");

        // Vérifier que save a été appelé une fois avec n'importe quel Participant
        verify(participantRepository, times(1)).save(any(Participant.class));

        log.info("✓ Test mock réussi - Participant ajouté avec ID: {}", result.getIdPart());
    }

    @Test
    @Order(2)
    @DisplayName("Test 2: Ajout d'un événement avec mock")
    public void testAddEvent() {
        log.info("=== Test Mock 2: Ajout d'un événement ===");

        // Arrange
        Event newEvent = new Event();
        newEvent.setDescription("Conférence DevOps 2024");
        newEvent.setDateDebut(LocalDate.now().plusDays(10));
        newEvent.setDateFin(LocalDate.now().plusDays(12));
        newEvent.setCout(200.0f);

        log.info("Création d'un événement : Description = {}, Coût = {}",
                newEvent.getDescription(), newEvent.getCout());

        // Simuler le comportement du repository
        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);

        // Act
        Event result = eventService.addAffectEvenParticipant(newEvent);

        // Assert
        assertNotNull(result, "L'événement retourné ne doit pas être nul");
        assertNotNull(result.getIdEvent(), "L'ID de l'événement ne doit pas être nul");
        assertEquals(1, result.getIdEvent(), "L'ID doit être 1");
        assertEquals("Conférence DevOps 2024", result.getDescription());
        assertEquals(200.0f, result.getCout());
        assertTrue(result.getDateDebut().isBefore(result.getDateFin()),
                "La date de début doit être avant la date de fin");

        // Vérifier que save a été appelé
        verify(eventRepository, times(1)).save(any(Event.class));

        log.info("✓ Test mock réussi - Événement ajouté avec ID: {}", result.getIdEvent());
    }

    @Test
    @Order(3)
    @DisplayName("Test 3: Recherche d'un participant par ID avec mock")
    public void testFindParticipantById() {
        log.info("=== Test Mock 3: Recherche d'un participant par ID ===");

        // Arrange
        int participantId = 1;
        log.info("Recherche du participant avec ID = {}", participantId);

        // Simuler le comportement du repository
        when(participantRepository.findById(participantId))
                .thenReturn(Optional.of(mockParticipant));

        // Act
        Optional<Participant> result = participantRepository.findById(participantId);

        // Assert
        assertTrue(result.isPresent(), "Le participant doit être trouvé");
        assertEquals(1, result.get().getIdPart(), "L'ID doit correspondre");
        assertEquals("Madien", result.get().getNom(), "Le nom doit être 'Madien'");
        assertEquals("Nouha", result.get().getPrenom(), "Le prénom doit être 'Nouha'");

        // Vérifier que findById a été appelé exactement une fois
        verify(participantRepository, times(1)).findById(participantId);

        log.info("✓ Test mock réussi - Participant trouvé: {} {}",
                result.get().getPrenom(), result.get().getNom());
    }

    @Test
    @Order(4)
    @DisplayName("Test 4: Récupération de tous les participants avec mock")
    public void testGetAllParticipants() {
        log.info("=== Test Mock 4: Récupération de tous les participants ===");

        // Arrange - Créer une liste de participants mockés
        Participant participant2 = new Participant();
        participant2.setIdPart(2);
        participant2.setNom("Smith");
        participant2.setPrenom("Alice");

        Participant participant3 = new Participant();
        participant3.setIdPart(3);
        participant3.setNom("Brown");
        participant3.setPrenom("Bob");

        List<Participant> mockParticipants = Arrays.asList(
                mockParticipant,
                participant2,
                participant3);

        log.info("Simulation de la récupération de {} participants", mockParticipants.size());

        // Simuler le comportement du repository
        when(participantRepository.findAll()).thenReturn(mockParticipants);

        // Act
        List<Participant> result = participantRepository.findAll();

        // Assert
        assertNotNull(result, "La liste ne doit pas être nulle");
        assertFalse(result.isEmpty(), "La liste ne doit pas être vide");
        assertEquals(3, result.size(), "La liste doit contenir exactement 3 participants");

        // Vérifier les détails de chaque participant
        assertEquals("Madien", result.get(0).getNom());
        assertEquals("Nouha", result.get(0).getPrenom());
        assertEquals("Smith", result.get(1).getNom());
        assertEquals("Alice", result.get(1).getPrenom());
        assertEquals("Brown", result.get(2).getNom());
        assertEquals("Bob", result.get(2).getPrenom());

        // Vérifier que findAll a été appelé exactement une fois
        verify(participantRepository, times(1)).findAll();

        log.info("✓ Test mock réussi - {} participants récupérés", result.size());
        result.forEach(p -> log.info("  - Participant: {} {} (ID: {})",
                p.getPrenom(), p.getNom(), p.getIdPart()));
    }

    @AfterEach
    public void tearDown() {
        log.info("Nettoyage après le test");
    }
}