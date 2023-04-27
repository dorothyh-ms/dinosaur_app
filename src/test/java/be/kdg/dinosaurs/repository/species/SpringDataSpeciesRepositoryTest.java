package be.kdg.dinosaurs.repository.species;

import be.kdg.dinosaurs.domain.*;
import be.kdg.dinosaurs.repository.digsites.SpringDataDigSiteRepository;
import be.kdg.dinosaurs.repository.discoveries.SpringDataDiscoveryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringDataSpeciesRepositoryTest {
    @Autowired
    private SpringDataSpeciesRepository speciesRepository;
    @Autowired
    private SpringDataDigSiteRepository digSiteRepository;

    @Autowired
    private SpringDataDiscoveryRepository speciesDigsiteRepository;

    private int speciesId;

    private int digSiteId;

    private DiscoveryId discoveryId;

    @BeforeEach
    public void setup() {
        Species species = new Species("Test species", "Test species", 1, Diet.HERBIVORE, "image.jpg");
        DigSite digSite = new DigSite("Test digsite", "Test country", 0, 0, LocalDate.now());
        speciesId = speciesRepository.save(species).getId();
        digSiteId = digSiteRepository.save(digSite).getId();
        Discovery discovery = new Discovery(new DiscoveryId(species, digSite), LocalDate.now());
        discoveryId = speciesDigsiteRepository.save(discovery).getId();
    }


    @Test
    public void deleteSpeciesDeletesAssociationClassButNotLinkedDigSite() {
        // Act
        speciesRepository.deleteById(speciesId);

        // Assert
        assertTrue(digSiteRepository.existsById(digSiteId));
        assertFalse(speciesDigsiteRepository.existsById(discoveryId));
    }

    @Test
    public void discoveriesOfSpeciesAreLoadedWithJoinFetch() {
        Species species = speciesRepository.findById(speciesId).get();
        assertEquals(species.getDiscoveries().size(), 1);
    }

    @Test
    public void speciesScientificNameIsMandatory() {
        // Arrange
        Species species1 = new Species("Test species2", "Test species2", 1, Diet.HERBIVORE, "image.jpg");
        Species species2 = new Species("Test species2", null, 1, Diet.HERBIVORE, "image.jpg");

        // Act
        speciesRepository.save(species1);
        // Assert
        assertTrue(species1.getId() > 0);
        assertThrows(DataIntegrityViolationException.class,
                () -> speciesRepository.save(species2));
    }

    @AfterEach
    public void tearDown() {

        if (digSiteRepository.existsById(digSiteId)) {
            digSiteRepository.deleteById(digSiteId);
        }
        if (speciesDigsiteRepository.existsById(discoveryId)){
            speciesDigsiteRepository.deleteById(discoveryId);
        }
        if (speciesRepository.existsById(speciesId)){
            speciesRepository.deleteById(speciesId);
        }
    }

}