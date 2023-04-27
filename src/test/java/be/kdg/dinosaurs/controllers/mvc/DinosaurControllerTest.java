package be.kdg.dinosaurs.controllers.mvc;

import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Period;
import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.repository.periods.SpringDataPeriodRepository;
import be.kdg.dinosaurs.repository.species.SpringDataSpeciesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // allows you to add a mock mvc object
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DinosaurControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpringDataSpeciesRepository speciesRepository;
    @Autowired
    private SpringDataPeriodRepository periodRepository;
    private int speciesId;


    private String name;

    private String scientificName;

    private int numberOfSpecimensFound;

    private Diet diet;

    private int periodId;

    private String periodName;

    private double startMillionsYears;

    private double endMillionsYears;

    @BeforeEach
    void setUp() {
        name = "test";
        scientificName = "test test";
        numberOfSpecimensFound = 1;
        diet = Diet.HERBIVORE;
        periodName = "Cretaceous";
        startMillionsYears = 145.0;
        endMillionsYears = 100.5;
        Species species = new Species(name, scientificName, numberOfSpecimensFound, diet);
        Period cretaceous = new Period(periodName, startMillionsYears, endMillionsYears);
        species.setPeriod(cretaceous);
        periodId = periodRepository.save(cretaceous).getId();
        speciesId = speciesRepository.save(species).getId();
    }


    @Test
    void showDinosaurShouldShowCorrectViewModel() throws Exception {
        mockMvc.perform(
                        get("/dinosaurs/dinosaur").queryParam("dinosaurId", String.valueOf(speciesId))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("dinosaur"))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("id", equalTo(speciesId))))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("name", equalTo(name))))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("scientificName", equalTo(scientificName))))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("numberSpecimensFound", equalTo(numberOfSpecimensFound))))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("diet", equalTo(diet))))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("periodName", equalTo(periodName))))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("periodStartMillionsYears", equalTo(startMillionsYears))))
                .andExpect(model().attribute("dinosaur",
                        hasProperty("periodEndMillionsYears", equalTo(endMillionsYears))));
    }

    @Test
    void showDinosaurShouldReturnNotFoundResponseCodeInvalidSpeciesIdQueryParameter() throws Exception {
        mockMvc.perform(
                        get("/dinosaurs/dinosaur").queryParam("dinosaurId", String.valueOf(1000))
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void showDeleteDinosaurShouldRedirectToDinosaursPageForValidSpeciesIdQueryParameter() throws Exception {
        mockMvc.perform(
                        get("/dinosaurs/delete").queryParam("dinosaurId", String.valueOf(speciesId))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dinosaurs"));
    }

    @Test
    void showDeleteDinosaurShouldReturnNotFoundResponseCodeInvalidSpeciesIdQueryParameter() throws Exception {
        mockMvc.perform(
                        get("/dinosaurs/delete").queryParam("dinosaurId", String.valueOf(1000))
                )
                .andExpect(status().isNotFound());
    }
    @AfterEach
    void tearDown() {
        if (speciesRepository.existsById(speciesId)) {
            speciesRepository.deleteById(speciesId);
        }
        if (periodRepository.existsById(periodId)) {
            periodRepository.deleteById(periodId);
        }
    }
}