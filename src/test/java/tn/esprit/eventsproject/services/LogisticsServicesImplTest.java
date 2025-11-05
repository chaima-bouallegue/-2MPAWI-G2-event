package tn.esprit.eventsproject.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.repositories.LogisticsRepository;

import java.util.List;

@Slf4j
@SpringBootTest
public class LogisticsServicesImplTest {

    @Autowired
    private LogisticsRepository logisticsRepository;

    @Test
    public void testAddLogistics() {
        log.info("Début du test d'ajout d'un matériel logistique");

        Logistics logistics = new Logistics();
        logistics.setDescription("Ordinateur portable Dell");
        logistics.setReserve(false);
        logistics.setPrixUnit(1200f);
        logistics.setQuantite(10);

        Logistics saved = logisticsRepository.save(logistics);

        Assertions.assertNotNull(saved.getIdLog(), "L'ID ne doit pas être nul");
        Assertions.assertEquals("Ordinateur portable Dell", saved.getDescription());
        Assertions.assertFalse(saved.isReserve());
        Assertions.assertEquals(1200f, saved.getPrixUnit());
        Assertions.assertEquals(10, saved.getQuantite());

        log.info("Test d'ajout réussi - ID du matériel : {}", saved.getIdLog());
    }

    @Test
    public void testFindLogisticsById() {
        log.info("Début du test de recherche d’un matériel logistique par ID");

        Logistics logistics = new Logistics();
        logistics.setDescription("Projecteur Epson");
        logistics.setReserve(true);
        logistics.setPrixUnit(800f);
        logistics.setQuantite(5);

        Logistics saved = logisticsRepository.save(logistics);
        log.info("Matériel sauvegardé avec l'ID : {}", saved.getIdLog());

        Logistics found = logisticsRepository.findById(saved.getIdLog()).orElse(null);

        Assertions.assertNotNull(found, "Le matériel doit être trouvé");
        Assertions.assertEquals("Projecteur Epson", found.getDescription());
        Assertions.assertTrue(found.isReserve());

        log.info("Test de recherche réussi pour l'ID {}", saved.getIdLog());
    }

    @Test
    public void testFindAllLogistics() {
        log.info("Début du test de récupération de tous les matériels logistiques");

        Logistics l1 = new Logistics();
        l1.setDescription("Chaise pliante");
        l1.setReserve(false);
        l1.setPrixUnit(30f);
        l1.setQuantite(50);

        Logistics l2 = new Logistics();
        l2.setDescription("Table ronde");
        l2.setReserve(true);
        l2.setPrixUnit(120f);
        l2.setQuantite(20);

        logisticsRepository.save(l1);
        logisticsRepository.save(l2);

        List<Logistics> list = logisticsRepository.findAll();

        Assertions.assertNotNull(list, "La liste ne doit pas être nulle");
        Assertions.assertTrue(list.size() >= 2, "Il doit y avoir au moins 2 matériels");

        log.info("Nombre total de matériels : {}", list.size());
    }

    @Test
    public void testUpdateLogistics() {
        log.info("Début du test de mise à jour d’un matériel logistique");

        Logistics logistics = new Logistics();
        logistics.setDescription("Écran de projection");
        logistics.setReserve(false);
        logistics.setPrixUnit(400f);
        logistics.setQuantite(3);

        Logistics saved = logisticsRepository.save(logistics);
        log.info("Matériel initial sauvegardé avec l'ID : {}", saved.getIdLog());

        saved.setReserve(true);
        Logistics updated = logisticsRepository.save(saved);

        Assertions.assertTrue(updated.isReserve(), "Le matériel devrait être marqué comme réservé");
        Assertions.assertEquals("Écran de projection", updated.getDescription());

        log.info("Test de mise à jour réussi - Réservé = {}", updated.isReserve());
    }
}
