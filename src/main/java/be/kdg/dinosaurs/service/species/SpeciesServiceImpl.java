//package be.kdg.dinosaurs.service.species;
//
//import be.kdg.dinosaurs.domain.*;
//import be.kdg.dinosaurs.repository.JSONWriter;
//import be.kdg.dinosaurs.repository.digsites.DigSiteRepository;
//import be.kdg.dinosaurs.repository.periods.PeriodRepository;
//import be.kdg.dinosaurs.repository.species.SpeciesRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//@Profile("!springdatarepo")
//public class SpeciesServiceImpl implements SpeciesService {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//    private JSONWriter jsonWriter;
//
//    private SpeciesRepository speciesRepository;
//    private PeriodRepository periodRepository;
//
//    private DigSiteRepository digSiteRepository;
//
//    public SpeciesServiceImpl(SpeciesRepository speciesRepository, PeriodRepository periodRepository, DigSiteRepository digSiteRepository, JSONWriter jsonWriter) {
//        this.speciesRepository = speciesRepository;
//        this.periodRepository = periodRepository;
//        this.digSiteRepository = digSiteRepository;
//        this.jsonWriter=jsonWriter;
//
//    }
//
//    @Override
//    public void removeSpecies(int speciesId) {
//        LOGGER.info("SpeciesServiceImpl is deleting species with id {}", speciesId);
//        speciesRepository.deleteSpeciesById(speciesId);
//    }
//
//    @Override
//    @Transactional
//    public List<Species> getSpecies() {
//        LOGGER.info("SpeciesServiceImpl is getting species");
//        return speciesRepository.readSpecies();
//    }
//
//
//    @Override
//    public Species addSpecies(Species species) {
//        LOGGER.debug("SpeciesServiceImpl is adding species {}", species);
//        return speciesRepository.createSpecies(species);
//    }
//
//
//    @Override
//    public Species getSpeciesById(int speciesId) {
//        LOGGER.info("SpeciesServiceImpl is getting species with id {}", speciesId);
//        Species species = speciesRepository.readSpeciesById(speciesId);
//        LOGGER.debug("SpeciesServiceImpl is returning species {}", species);
//        return species;
//    }
//
//    @Override
//    @Transactional
//    public List<Discovery> getDigSitesOfSpecies(int speciesId) {
//        LOGGER.info("SpeciesServiceImpl is getting digsites where species with id {} were found", speciesId);
//        List<DigSite> digSites = speciesRepository.readSpeciesById(speciesId).getDigSites();
//        LOGGER.debug("SpeciesServiceImpl is returning digsite list {}", digSites);
//        return digSites;
//    }
//
//    @Override
//    public void changeSpecies(Species species) {
//        LOGGER.info("SpeciesServiceImpl is editing species {}", species);
//        speciesRepository.updateSpecies(species);
//    }
//
//    @Override
//    public List<Period> getPeriods() {
//        LOGGER.info("SpeciesServiceImpl is getting all recorded geologic periods");
//        List<Period> periods = periodRepository.readPeriods();
//        LOGGER.debug("SpeciesServiceImpl is returning list of geologic periods {}", periods);
//        return periods;
//    }
//
//    @Override
//    public List<Species> getSpeciesByPeriod(String periodName) {
//        LOGGER.info("SpeciesServiceImpl is getting all species of the geologic period {}", periodName);
//        List<Species> species = speciesRepository.readSpeciesByPeriod(periodName);
//        LOGGER.debug("SpeciesServiceImpl is returning list of species {}", species);
//        return species;
//    }
//
//    @Override
//    public List<Diet> getDiets() {
//        LOGGER.info("SpeciesServiceImpl is getting all recorded diets");
//        List<Diet> diets = speciesRepository.readDiets();
//        LOGGER.debug("SpeciesServiceImpl is returning list of diets {}", diets);
//        return diets;
//    }
//
//    @Override
//    public List<Species> getSpeciesByDiet(String dietName) {
//        LOGGER.info("SpeciesServiceImpl is getting all species with the diet {}", dietName);
//        List<Species> species = speciesRepository.readSpeciesByDiet(dietName);
//        LOGGER.debug("SpeciesServiceImpl is returning list of species {}", species);
//        return species;
//    }
//
//    @Override
//    @Transactional
//    public void setPeriodOfSpecies(Species species, String periodName) {
//        LOGGER.info("SpeciesServiceImpl is setting species {} to period with name {}", species, periodName);
//        Period period = periodRepository.readByName(periodName);
//        if (period != null) {
//            species.setPeriod(period);
//            LOGGER.debug("SpeciesServiceImpl set species' {} period to {} ", species, period);
//        } else {
//            LOGGER.error("SpeciesServiceImpl could not find period with id {}", periodName);
//        }
//    }
//
//    public void writeSpeciesToJSON(List<Species> species){
//        LOGGER.info("SpeciesServiceImpl is writing all species to a JSON file");
//        jsonWriter.writeSpecies(species);
//    }
//
//    @Override
//    @Transactional
//    public void addDigSitesOfSpecies(Species species, Set<Integer> digSiteIds) {
//        LOGGER.info("SpeciesServiceImpl is adding digsites with ids {} to species {}", digSiteIds, species);
//        List<DigSite> digSites = digSiteIds.stream().map(digSiteRepository::readDigSiteById).collect(Collectors.toList());
//        speciesRepository.addDigSitesOfSpecies(species, digSites);
//        LOGGER.debug("SpeciesServiceImpl added {} to {}", digSites, species);
//    }
//
//    @Override
//    public boolean speciesExists(int id) {
//        return false;
//    }
//
//    @Override
//    public boolean updateSpecies(int id, String scientificName, int numberOfSpecimens, String dietName, String periodName) {
//        return false;
//    }
//}
