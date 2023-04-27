package be.kdg.dinosaurs.service.species;

import be.kdg.dinosaurs.domain.*;
import be.kdg.dinosaurs.repository.digsites.SpringDataDigSiteRepository;
import be.kdg.dinosaurs.repository.periods.SpringDataPeriodRepository;
import be.kdg.dinosaurs.repository.species.SpringDataSpeciesRepository;
import be.kdg.dinosaurs.repository.discoveries.SpringDataDiscoveryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringDataSpeciesServiceImplTest {


    @Autowired
    private SpringDataSpeciesServiceImpl speciesService;
    @Autowired
    private SpringDataSpeciesRepository speciesRepository;
    @Autowired
    private SpringDataDigSiteRepository digSiteRepository;
    @Autowired
    private SpringDataPeriodRepository periodRepository;

    @Autowired
    private SpringDataDiscoveryRepository speciesDiscoveryRepository;

    int speciesId;

    int digSite1Id;

    int digSite2Id;

    int digSite3Id;

    private String name;

    private String scientificName;

    private int numberOfSpecimens;

    private String dietName;

    private String periodName;

    private int period1Id;

    private int period2Id;



    @BeforeEach
    public void setup() {
        name = "Test species";
        scientificName = "Test species";
        numberOfSpecimens = 1;
        dietName = "HERBIVORE";
        periodName = "Triassic";
        Species species = new Species(name, scientificName, numberOfSpecimens, Diet.valueOf(dietName), "image.jpg");
        Period triassic = new Period("Triassic", 251.902, 201.36);
        Period cretaceous = new Period("Cretaceous", 145.0, 100.5);
        period1Id = periodRepository.save(triassic).getId();
        period2Id = periodRepository.save(cretaceous).getId();
        species.setPeriod(triassic);
        DigSite digSite1 = new DigSite("Test digsite1", "Test country1", 0, 0, LocalDate.now());
        DigSite digSite2 = new DigSite("Test digsite2", "Test country2", 0, 0, LocalDate.now());
        DigSite digSite3 = new DigSite("Test digsite3", "Test country3", 0, 0, LocalDate.now());
        speciesId = speciesRepository.save(species).getId();
        digSite1Id = digSiteRepository.save(digSite1).getId();
        digSite2Id = digSiteRepository.save(digSite2).getId();
        digSite3Id = digSiteRepository.save(digSite3).getId();
    }

    @Test
    public void addDigSitesOfSpeciesShouldFailForNonExistentDigSite() {

        // Given there is a Species "species" with no associated digsites
        // And there is a map of digsite ids [100, digsite1Id] and associated discovery dates
        // And the digsite with id 100  does not exist
        // When the user adds digsites with the ids 100 and digsite1Id to "species"
        // Then the system throws an EntityNotFoundException
        // And "species" has no associated digsites

        Species species = speciesRepository.findById(speciesId).get();
        HashMap<Integer, LocalDate> digSiteIdsDatesFound = new HashMap<>();
        digSiteIdsDatesFound.put(100, LocalDate.now());
        digSiteIdsDatesFound.put(digSite1Id, LocalDate.now());


        // Assert
        assertThrows(EntityNotFoundException.class, () -> {
            // Act
            speciesService.addDigSitesOfSpecies(species, digSiteIdsDatesFound);
        });

        // Assert
        assertFalse(speciesDiscoveryRepository.existsBySpeciesIdAndDigSiteId(speciesId, 100));
        assertFalse(speciesDiscoveryRepository.existsBySpeciesIdAndDigSiteId(speciesId, digSite1Id));

    }

    @Test
    public void addDigSitesOfSpeciesShouldCreateSpeciesDigSite() {

        // Given there is a Species "species" with id speciesId
        // And "species" has no associated digsites
        // And there is a map of digsite ids [digsite1Id, digsite2Id] and associated discovery dates
        // And a digsite with id digsite1Id and a digsite with id digsite2Id exist
        // When the user adds a digsite with id digsite1Id and a digsite with id digsite2Id to "species"
        // Then there exists Discovery object with a reference to "species" and a digsite with id digsite1Id
        // And there exists Discovery object with a reference to "species" and a digsite with id digsite2Id

        Species species = speciesRepository.findById(speciesId).get();
        HashMap<Integer, LocalDate> digSiteIdsDatesFound = new HashMap<>();
        digSiteIdsDatesFound.put(digSite1Id, LocalDate.now());
        digSiteIdsDatesFound.put(digSite2Id, LocalDate.now());

        // Act
        speciesService.addDigSitesOfSpecies(species, digSiteIdsDatesFound);

        // Assert
        assertTrue(speciesDiscoveryRepository.existsBySpeciesIdAndDigSiteId(speciesId, digSite1Id));
        assertTrue(speciesDiscoveryRepository.existsBySpeciesIdAndDigSiteId(speciesId, digSite2Id));
    }


    @Test
    public void updateSpeciesShouldFailForNonExistentSpecies() {
        // Given there is no species with id 100
        // When the user updates species with id 100 to have the scientific name "New scientific name", number of specimens 20,
        // diet name "carnivore", and geologic period name "cretaceous"
        // Then the system throws an EntityNotFoundException

        String newScientificName = "New scientificname";
        int newNumberOfSpecimens = 20;
        String newDietName = "CARNIVORE";
        String newPeriodName = "CRETACEOUS";
        // Assert
        assertThrows(EntityNotFoundException.class, () -> {
            // Act
            speciesService.updateSpecies(100, newScientificName, newNumberOfSpecimens, newDietName, newPeriodName);
        });
    }

    @Test
    public void updateSpeciesShouldFailForInvalidDietName() {
        // Given there is a species with id "speciesId" with scientific name "scientificName", number of specimens "numberOfSpecimens",
        // diet name "dietName", and period with name "periodName"
        // And there is no Diet with name stored in "invalidDietName"
        // And "scientificName" is not equal to "newScientificName", "numberOfSpecimens" is not equal to "newNumberOfSpecimens",
        // "dietName" is not equal to "invalidDietName", and "periodName" is not equal to "newPeriodName"
        // Then the system throws an EntityNotFoundException
        // And the species with id "speciesId" does not have the scientific name "newScientificName", number of specimens  "numberOfSpecimens",
        // diet name "invalidDietName" with value "Invalid", and geologic period name "newPeriodName"

        Species species = speciesRepository.findById(speciesId).get();

        String newScientificName = "New scientificname";
        int newNumberOfSpecimens = 20;
        String invalidDietName = "Invalid";
        String newPeriodName = "CRETACEOUS";

        // Assert species did not already have these attributes
        assertNotEquals(species.getScientificName(), newScientificName);
        assertNotEquals(species.getNumberSpecimensFound(), newNumberOfSpecimens);
        assertNotEquals(species.getDiet().toString(), invalidDietName);
        assertNotEquals(species.getPeriod().getName(), newPeriodName);


        // Assert exception is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            // Act
            speciesService.updateSpecies(speciesId, newScientificName, newNumberOfSpecimens, invalidDietName, newPeriodName);
        });

        // Assert species STILL does not have these attributes
        assertNotEquals(species.getScientificName(), newScientificName);
        assertNotEquals(species.getNumberSpecimensFound(), newNumberOfSpecimens);
        assertNotEquals(species.getDiet().toString(), invalidDietName);
        assertNotEquals(species.getPeriod().getName(), newPeriodName);
    }

    @Test
    public void updateSpeciesShouldFailForInvalidPeriodName() {
        // Given there is a species with id "speciesId" with scientific name "scientificName", number of specimens "numberOfSpecimens",
        // diet name "dietName", and period with name "periodName"
        // And there is no Diet enum with name stored in "invalidDietName"
        // And "scientificName" is not equal to "newScientificName", "numberOfSpecimens" is not equal to "newNumberOfSpecimens",
        // "dietName" is not equal to "newDietName", and "periodName" is not equal to "newPeriodName"
        // Then the system throws an EntityNotFoundException
        // And the species with id "speciesId" does not have the scientific name "newScientificName", number of specimens 20,
        // diet name "invalidDietName" with value "Invalid", and geologic period name "newPeriodName"

        Species species = speciesRepository.findById(speciesId).get();

        String newScientificName = "New scientificname";
        int newNumberOfSpecimens = 20;
        String newDietName = "CARNIVORE";
        String invalidPeriodName = "Invalid";

        // Assert species did not already have these attributes
        assertNotEquals(species.getScientificName(), newScientificName);
        assertNotEquals(species.getNumberSpecimensFound(), newNumberOfSpecimens);
        assertNotEquals(species.getDiet().toString(), newDietName);
        assertNotEquals(species.getPeriod().getName(), invalidPeriodName);

//         Assert exception is thrown
        assertThrows(EntityNotFoundException.class, () -> {
            // Act
            speciesService.updateSpecies(speciesId, newScientificName, newNumberOfSpecimens, newDietName, invalidPeriodName);
        });

        // Assert species STILL does not have these attributes
        assertNotEquals(species.getScientificName(), newScientificName);
        assertNotEquals(species.getNumberSpecimensFound(), newNumberOfSpecimens);
        assertNotEquals(species.getDiet().toString(), newDietName);
        assertNotEquals(species.getPeriod().getName(), invalidPeriodName);
    }


    @Test
    public void updateSpeciesDietNameShouldBeCaseInsensitive() {

        // Given there is a species with id "speciesId" with scientific name "scientificName", number of specimens "numberOfSpecimens",
        // diet name "dietName", and period with name "periodName"
        // And "scientificName" is not equal to "newScientificName", "numberOfSpecimens" is not equal to "newNumberOfSpecimens",
        // "dietName" is not equal to "newDietName", and "periodName" is not equal to "newPeriodName"
        // When the user updates species with id "speciesId"  scientific name "newScientificName", number of specimens "newNumberOfSpecimens",
        // diet name "newDietName", and geologic period name "newPeriodName"
        // Then the species with id "speciesId" has the scientific name "newScientificName", number of specimens "newNumberOfSpecimens",
        // diet name "newDietName", and geologic period name "newPeriodName"

        // Assert that species does not already have the diet that will be set
        Diet expectedNewDiet = Diet.CARNIVORE;
        Species species = speciesRepository.findById(speciesId).get();
        assertNotEquals(species.getDiet(), expectedNewDiet);

        // updateSpecies dietname arguments
        String newDietNameLowerCase = "carnivore";
        String newDietNameUppercase = "CARNIVORE";
        String newDietNameMixedCase = "CaRNivORe";

        // updateSpecies all other arguments
        String newScientificName = "New scientificname";
        int newNumberOfSpecimens = 20;
        String newPeriodName = "Cretaceous";



        // Act
        speciesService.updateSpecies(speciesId, newScientificName, newNumberOfSpecimens, newDietNameLowerCase, newPeriodName);

        // Assert that the species now has the new attributes
        species = speciesRepository.findById(speciesId).get();
        assertEquals(species.getDiet(), expectedNewDiet);

        // Act
        speciesService.updateSpecies(speciesId, newScientificName, newNumberOfSpecimens, newDietNameMixedCase, newPeriodName);

        // Assert that the species now has the new attributes
        species = speciesRepository.findById(speciesId).get();
        assertEquals(species.getDiet(), expectedNewDiet);

        // Act
        speciesService.updateSpecies(speciesId, newScientificName, newNumberOfSpecimens, newDietNameUppercase, newPeriodName);

        // Assert that the species now has the new attributes
        species = speciesRepository.findById(speciesId).get();
        assertEquals(species.getDiet(), expectedNewDiet);

    }

    @Test
    public void updateSpeciesShouldUpdateSpecies() {

        // Given there is a species with id "speciesId" with scientific name "scientificName", number of specimens "numberOfSpecimens",
        // diet name "dietName", and period with name "periodName"
        // And "scientificName" is not equal to "newScientificName", "numberOfSpecimens" is not equal to "newNumberOfSpecimens",
        // "dietName" is not equal to "newDietName", and "periodName" is not equal to "newPeriodName"
        // When the user updates species with id "speciesId"  scientific name "newScientificName", number of specimens "newNumberOfSpecimens",
        // diet name "newDietName", and geologic period name "newPeriodName"
        // Then the species with id "speciesId" has the scientific name "newScientificName", number of specimens "newNumberOfSpecimens",
        // diet name "newDietName", and geologic period name "newPeriodName"

        // Assert that species already has the following attributes
        Species species = speciesRepository.findById(speciesId).get();
        assertEquals(species.getScientificName(), scientificName);
        assertEquals(species.getNumberSpecimensFound(), numberOfSpecimens);
        assertEquals(species.getDiet().toString(), dietName);
        assertEquals(species.getPeriod().getName(), periodName);


        String newScientificName = "New scientificname";
        int newNumberOfSpecimens = 20;
        String newDietName = "CARNIVORE";
        String newPeriodName = "Cretaceous";

        // Assert old values and new values are different
        assertNotEquals(scientificName, newScientificName);
        assertNotEquals(numberOfSpecimens, newNumberOfSpecimens);
        assertNotEquals(dietName, newDietName);
        assertNotEquals(periodName, newPeriodName);

        // Act
        speciesService.updateSpecies(speciesId, newScientificName, newNumberOfSpecimens, newDietName, newPeriodName);



        // Assert that the species now has the new attributes
        species = speciesRepository.findById(speciesId).get();
        assertEquals(species.getScientificName(), newScientificName);
        assertEquals(species.getNumberSpecimensFound(), newNumberOfSpecimens);
        assertEquals(species.getDiet().toString(), newDietName);
        assertEquals(species.getPeriod().getName(), newPeriodName);
    }

    @AfterEach
    public void tearDown() {
        speciesRepository.deleteById(speciesId);
        periodRepository.deleteById(period1Id);
        periodRepository.deleteById(period2Id);
        digSiteRepository.deleteById(digSite1Id);
        digSiteRepository.deleteById(digSite2Id);
        digSiteRepository.deleteById(digSite3Id);
    }
}

