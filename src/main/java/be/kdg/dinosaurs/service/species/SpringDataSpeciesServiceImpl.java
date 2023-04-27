package be.kdg.dinosaurs.service.species;

import be.kdg.dinosaurs.domain.*;
import be.kdg.dinosaurs.repository.JSONWriter;
import be.kdg.dinosaurs.repository.digsites.SpringDataDigSiteRepository;
import be.kdg.dinosaurs.repository.periods.SpringDataPeriodRepository;
import be.kdg.dinosaurs.repository.species.SpringDataSpeciesRepository;
import be.kdg.dinosaurs.repository.discoveries.SpringDataDiscoveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@Profile(value = {"springdatarepo", "test"})
public class SpringDataSpeciesServiceImpl implements SpeciesService {
    private SpringDataSpeciesRepository speciesRepository;
    private SpringDataPeriodRepository periodRepository;

    private SpringDataDigSiteRepository digSiteRepository;
    private SpringDataDiscoveryRepository speciesDigsiteRepository;

    private JSONWriter jsonWriter;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public SpringDataSpeciesServiceImpl(SpringDataSpeciesRepository speciesRepository, SpringDataPeriodRepository periodRepository, SpringDataDigSiteRepository digSiteRepository, SpringDataDiscoveryRepository speciesDigsiteRepository, JSONWriter jsonWriter) {
        this.speciesRepository = speciesRepository;
        this.periodRepository = periodRepository;
        this.digSiteRepository = digSiteRepository;
        this.speciesDigsiteRepository = speciesDigsiteRepository;
        this.jsonWriter = jsonWriter;
    }

    @Override
    @Transactional
    public List<Species> getSpecies() {
        LOGGER.info("SpringDataSpeciesServiceImpl is getting species");
        return speciesRepository.findAll();
    }

    @Override
    @Transactional
    public Species getSpeciesById(int id) {
        LOGGER.info("SpringDataSpeciesServiceImpl is getting species with id {}", id);
        Species species = speciesRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("SpringDataSpeciesServiceImpl is returning species {}", species);
        return species;
    }

    @Override
    public Species addSpecies(Species species) {
        LOGGER.debug("SpringDataSpeciesServiceImpl is adding species {}", species);
        return speciesRepository.save(species);
    }

    @Override
    public void removeSpecies(int id) {
        LOGGER.info("SpringDataSpeciesServiceImpl is deleting species with id {}", id);
        Species species = getSpeciesById(id);
        speciesRepository.deleteById(species.getId());
    }

    @Override
    @Transactional
    public List<Discovery> getDiscoveriesOfSpecies(int id) {
        LOGGER.info("SpringDataSpeciesServiceImpl is running getDiscoveriesOfSpecies with id {}", id);
        Species species = speciesRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Discovery> discoveries = speciesDigsiteRepository.findById_Species(species);
        LOGGER.debug("SpringDataSpeciesServiceImpl is returning discoveries list {}", discoveries);
        return discoveries;
    }

    @Override
    @Transactional
    public void changeSpecies(Species species) {
        LOGGER.info("SpringDataSpeciesServiceImpl is editing species {}", species);
        Species speciesToEdit = speciesRepository.findById(species.getId()).orElseThrow(EntityNotFoundException::new);

        speciesToEdit.setName(species.getName());
        speciesToEdit.setScientificName(species.getScientificName());
        speciesToEdit.setNumberSpecimensFound(species.getNumberSpecimensFound());
        speciesToEdit.setDiet(species.getDiet());
        speciesToEdit.setPeriod(species.getPeriod());
        speciesToEdit.setImage(species.getImage());
        LOGGER.debug("SpringDataSpeciesServiceImpl is saving changes to species {}", species);
        speciesRepository.save(speciesToEdit);
    }


    @Override
    public List<Period> getPeriods() {
        LOGGER.info("SpringDataSpeciesServiceImpl is getting all recorded geologic periods");
        List<Period> periods = periodRepository.findAll();
        LOGGER.debug("SpringDataDigSiteServiceImpl is returning list of geologic periods {}", periods);
        return periods;
    }

    @Override
    public List<Species> getSpeciesByPeriod(String periodName) {
        LOGGER.info("SpringDataSpeciesServiceImpl is getting all species of the geologic period {}", periodName);
        List<Species> species = speciesRepository.findSpeciesByPeriodName(periodName);
        LOGGER.debug("SpringDataDigSiteServiceImpl is returning list of species {}", species);
        return species;
    }

    @Override
    public List<Diet> getDiets() {
        LOGGER.info("SpringDataSpeciesServiceImpl is getting all recorded diets");
        List<Diet> diets = speciesRepository.findAll().stream().map(s -> s.getDiet()).distinct().collect(toList());
        LOGGER.debug("SpringDataDigSiteServiceImpl is returning list of diets {}", diets);
        return diets;
    }

    @Override
    public List<Species> getSpeciesByDiet(String diet) {
        LOGGER.info("SpringDataSpeciesServiceImpl is getting all species with the diet {}", diet);
        List<Species> species = speciesRepository.findByDiet(Diet.valueOf(diet));
        LOGGER.debug("SpringDataDigSiteServiceImpl is returning list of species {}", species);
        return species;
    }

    @Override
    @Transactional
    public void setPeriodOfSpecies(Species species, String periodName) {
        LOGGER.info("SpringDataSpeciesServiceImpl is setting species {} to period with name {}", species, periodName);
        Period period = periodRepository.findByNameIgnoreCase(periodName).orElseThrow(EntityNotFoundException::new);
        species.setPeriod(period);
        LOGGER.debug("SpringDataSpeciesServiceImpl set species' {} period to {} ", species, period);

    }

    public void writeSpeciesToJSON(List<Species> species) {
        LOGGER.info("SpringDataSpeciesServiceImpl is writing all species to a JSON file");
        jsonWriter.writeSpecies(species);
    }

    @Override
    @Transactional
    public void addDigSitesOfSpecies(Species species, Map<Integer, LocalDate> digSiteIdsDatesFound) {
        LOGGER.info("SpringDataSpeciesServiceImpl is running addDigSitesOfSpecies");
        List<Discovery> discoveries = new ArrayList<>();
        digSiteIdsDatesFound.forEach((digSiteId, dateDiscovered) -> {
            DigSite digSite = digSiteRepository.findById(digSiteId).orElseThrow(EntityNotFoundException::new);
            discoveries.add(
                    new Discovery(
                            new DiscoveryId(species, digSite),
                            dateDiscovered));
        });
        LOGGER.debug("Adding discoveries {}", discoveries);
        discoveries.stream().forEach(speciesDigsiteRepository::save);
        LOGGER.debug("SpringDataSpeciesServiceImpl saved SpeciesDigSites", discoveries);
    }

    @Override
    public boolean speciesExists(int id) {
        return speciesRepository.existsById(id);
    }

    @Override
    @Transactional
    public void updateSpecies(int id, String scientificName, int numberOfSpecimens, String dietName, String periodName) {
        LOGGER.info("SpringDataSpeciesServiceImpl is updating species with id {}", id);
        var species = speciesRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        species.setScientificName(scientificName);
        species.setNumberSpecimensFound(numberOfSpecimens);
        species.setDiet(Diet.valueOf(dietName.toUpperCase()));
        species.setPeriod(periodRepository.findByNameIgnoreCase(periodName).orElseThrow(EntityNotFoundException::new));
        speciesRepository.save(species);
    }

}
