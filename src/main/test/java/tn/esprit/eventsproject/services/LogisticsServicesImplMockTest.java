package tn.esprit.eventsproject.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.repositories.LogisticsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class LogisticsServicesImplMockTest {

    @Mock
    private LogisticsRepository logisticsRepository;

    private Logistics logistics1;

    @BeforeEach
    void setUp() {
        logistics1 = new Logistics();
        logistics1.setDescription("Projecteur Full HD");
        logistics1.setReserve(false);
        logistics1.setPrixUnit(500f);
        logistics1.setQuantite(3);
    }

    @Test
    public void testAddLogistics() {
        log.info("Création d’un objet logistics : {}", logistics1.getDescription());

        Logistics savedLogistics = new Logistics();
        savedLogistics.setIdLog(1);
        savedLogistics.setDescription("Projecteur Full HD");
        savedLogistics.setReserve(false);
        savedLogistics.setPrixUnit(500f);
        savedLogistics.setQuantite(3);

        when(logisticsRepository.save(logistics1)).thenReturn(savedLogistics);

        log.info("Appel de la méthode logisticsRepository.save");
        Logistics result = logisticsRepository.save(logistics1);

        log.info("Vérification des assertions pour l’objet sauvegardé");
        Assertions.assertNotNull(result.getIdLog(), "L'ID du matériel ne doit pas être nul");
        Assertions.assertEquals("Projecteur Full HD", result.getDescription());
        Assertions.assertFalse(result.isReserve());
        Assertions.assertEquals(500f, result.getPrixUnit());
        Assertions.assertEquals(3, result.getQuantite());

        verify(logisticsRepository, times(1)).save(logistics1);
        log.info("Méthode logisticsRepository.save vérifiée avec succès");
    }

    @Test
    public void testFindLogisticsById() {
        log.info("Test de recherche d’un matériel par ID");

        Logistics foundLogistics = new Logistics();
        foundLogistics.setIdLog(1);
        foundLogistics.setDescription("Ordinateur Portable");
        foundLogistics.setReserve(true);
        foundLogistics.setPrixUnit(1200f);
        foundLogistics.setQuantite(5);

        when(logisticsRepository.findById(1)).thenReturn(Optional.of(foundLogistics));

        log.info("Appel de la méthode logisticsRepository.findById(1)");
        Optional<Logistics> result = logisticsRepository.findById(1);

        log.info("Vérification des assertions");
        Assertions.assertTrue(result.isPresent(), "L’objet doit être présent");
        Assertions.assertEquals(1, result.get().getIdLog());
        Assertions.assertEquals("Ordinateur Portable", result.get().getDescription());

        verify(logisticsRepository, times(1)).findById(1);
        log.info("Méthode logisticsRepository.findById vérifiée avec succès");
    }

    @Test
    public void testFindAllLogistics() {
        log.info("Test de récupération de tous les objets logistiques");

        Logistics logistics2 = new Logistics();
        logistics2.setIdLog(2);
        logistics2.setDescription("Microphone sans fil");
        logistics2.setReserve(true);
        logistics2.setPrixUnit(250f);
        logistics2.setQuantite(10);

        List<Logistics> logisticsList = Arrays.asList(logistics1, logistics2);
        when(logisticsRepository.findAll()).thenReturn(logisticsList);

        log.info("Appel de la méthode logisticsRepository.findAll");
        List<Logistics> result = logisticsRepository.findAll();

        log.info("Vérification des assertions");
        Assertions.assertNotNull(result, "La liste ne doit pas être nulle");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Projecteur Full HD", result.get(0).getDescription());

        verify(logisticsRepository, times(1)).findAll();
        log.info("Récupération de tous les objets logistiques réussie");
    }

    @Test
    public void testUpdateLogistics() {
        log.info("Test de mise à jour d’un matériel logistique");

        logistics1.setIdLog(1);
        logistics1.setReserve(false);

        Logistics updatedLogistics = new Logistics();
        updatedLogistics.setIdLog(1);
        updatedLogistics.setDescription("Projecteur Full HD");
        updatedLogistics.setReserve(true);
        updatedLogistics.setPrixUnit(500f);
        updatedLogistics.setQuantite(3);

        when(logisticsRepository.save(logistics1)).thenReturn(updatedLogistics);

        log.info("Appel de la méthode logisticsRepository.save pour mise à jour");
        Logistics result = logisticsRepository.save(logistics1);

        log.info("Vérification des assertions après mise à jour");
        Assertions.assertTrue(result.isReserve(), "Le matériel devrait être marqué comme réservé");
        Assertions.assertEquals("Projecteur Full HD", result.getDescription());

        verify(logisticsRepository, times(1)).save(logistics1);
        log.info("Mise à jour du matériel réussie");
    }
}
