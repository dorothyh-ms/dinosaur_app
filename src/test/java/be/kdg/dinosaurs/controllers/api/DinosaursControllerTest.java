package be.kdg.dinosaurs.controllers.api;

import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Period;
import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.repository.periods.SpringDataPeriodRepository;
import be.kdg.dinosaurs.repository.species.SpeciesRepository;
import be.kdg.dinosaurs.repository.species.SpringDataSpeciesRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // allows you to add a mock mvc object
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DinosaursControllerTest {

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

    private String diet;

    private int periodId;

    private String periodName;

    private double startMillionsYears;

    private double endMillionsYears;

    @BeforeEach
    void setUp() {
        name = "test";
        scientificName = "test test";
        numberOfSpecimensFound = 1;
        diet = "HERBIVORE";
        periodName = "Cretaceous";
        startMillionsYears = 145.0;
        endMillionsYears = 100.5;
        Species species = new Species(name, scientificName, numberOfSpecimensFound, Diet.valueOf(diet));
        Period cretaceous = new Period(periodName, startMillionsYears, endMillionsYears);
        periodId = periodRepository.save(cretaceous).getId();
        species.setPeriod(cretaceous);
        speciesId = speciesRepository.save(species).getId();
    }


    @Test
    void getDinosaurShouldReturnResponseWithDinosaurAndWithStatusCode200() throws Exception {
        mockMvc.perform(
                        get("/api/dinosaurs/{id}", String.valueOf(speciesId))
                                .accept("application/json"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(header().string(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.scientificName", equalTo(scientificName)))
                .andExpect(jsonPath("$.numberSpecimensFound", equalTo(numberOfSpecimensFound)))
                .andExpect(jsonPath("$.diet", equalTo(diet)))
                .andExpect(jsonPath("$.period.name", equalTo(periodName)))
                .andExpect(jsonPath("$.period.startMillionsYears", equalTo(startMillionsYears)))
                .andExpect(jsonPath("$.period.endMillionsYears", equalTo(endMillionsYears)));
    }

    @Test
    void getDinosaurShouldReturnResponseWithStatusCode404() throws Exception {
        mockMvc.perform(
                        get("/api/dinosaurs/{id}", String.valueOf(100))
                                .accept("application/json"))
                // Assert
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteDinosaurShouldReturnResponseWithStatusCode204() throws Exception {
        mockMvc.perform(
                        delete("/api/dinosaurs/{id}", String.valueOf(speciesId))
                                .accept("application/json"))
                // Assert
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDinosaurShouldReturnResponseWithStatusCode404() throws Exception {
        mockMvc.perform(
                        delete("/api/dinosaurs/{id}", String.valueOf(100))
                                .accept("application/json"))
                // Assert
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