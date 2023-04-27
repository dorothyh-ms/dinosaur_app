//package be.kdg.dinosaurs.repository.species;
//
//import be.kdg.dinosaurs.domain.Diet;
//import be.kdg.dinosaurs.domain.DigSite;
//import be.kdg.dinosaurs.domain.Species;
//import be.kdg.dinosaurs.exceptions.SpeciesNotAvailableException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Repository
//@Profile("listrepo")
//public class ListSpeciesRepository implements SpeciesRepository {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    private static final List<Species> dinosaurSpecies = new ArrayList<>();
//
//
//    public Species createSpecies(Species species) {
//        species.setId(dinosaurSpecies.size());
//        LOGGER.info("ListSpeciesRepository is adding species {} to its list of species", species);
//        dinosaurSpecies.add(species);
//        return species;
//    }
//
//    @Override
//    public List<Species> readSpecies() {
//        LOGGER.info("ListSpeciesRepository is getting all species from its list of species");
//        return dinosaurSpecies;
//    }
//
//
//    @Override
//    public Species readSpeciesById(int id) {
//        LOGGER.info("ListSpeciesRepository is getting species with id {} from its list of species", id);
//        Optional<Species> species = dinosaurSpecies.stream().filter(s -> s.getId() == id).findFirst();
//        if (species.isPresent()) {
//            return species.get();
//        }
//        throw new SpeciesNotAvailableException(String.format("Species with ID %d does not exist", id));
//    }
//
//    @Override
//    public void updateSpecies(Species species,) {
//        LOGGER.info("ListSpeciesRepository is updating species with id {} in its list of species to {}", species.getId(), species);
//        Species speciesToUpdate = dinosaurSpecies.stream().filter(s -> s.getId() == species.getId()).findFirst().get();
//        speciesToUpdate.setName(species.getName());
//        speciesToUpdate.setScientificName(species.getScientificName());
//        speciesToUpdate.setDiet(species.getDiet());
//        speciesToUpdate.setPeriod(species.getPeriod());
//        speciesToUpdate.setNumberSpecimensFound(species.getNumberSpecimensFound());
//        speciesToUpdate.setImage(species.getImage());
//        LOGGER.info("ListSpeciesRepository is clearing digsites of species {}", species);
//        speciesToUpdate.getDigSites().stream().forEach(d -> d.getSpeciesFound().remove(speciesToUpdate));
//        speciesToUpdate.getDigSites().clear();
//        addDigSitesOfSpecies(speciesToUpdate, species.getDigSites());
//    }
//
//    @Override
//    public void deleteSpeciesById(int id) {
//        LOGGER.info("ListSpeciesRepository is deleting species with id {} from its list of species", id);
//        Species species = readSpeciesById(id);
//        species.getDigSites().forEach(digSite -> digSite.getSpeciesFound().remove(species));
//        dinosaurSpecies.remove(species);
//    }
//
//
//    @Override
//    public List<Species> readSpeciesByPeriod(String periodName) {
//        LOGGER.info("ListSpeciesRepository is getting all species of period {} from its list of species", periodName);
//        return dinosaurSpecies.stream().filter(s -> s.getPeriod().getName().equals(periodName)).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Diet> readDiets() {
//        LOGGER.info("ListSpeciesRepository is getting all diets from its list of species");
//        return dinosaurSpecies.stream().map(Species::getDiet).distinct().collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Species> readSpeciesByDiet(String dietName) {
//        LOGGER.info("ListSpeciesRepository is getting all species with diet {} from its list of species", dietName);
//        return dinosaurSpecies.stream().filter(s -> s.getDiet().equals(Diet.valueOf(dietName))).collect(Collectors.toList());
//    }
//
//    @Override
//    public void addDigSitesOfSpecies(Species species, List<DigSite> digSites) {
//        LOGGER.info("ListSpeciesRepository is adding digsites {} to species {}", digSites, species);
//        digSites.forEach(d -> {
//            List<Integer> speciesIds = d.getSpeciesFound().stream().map(Species::getId).collect(Collectors.toList());
//            if (!speciesIds.contains(species.getId())){
//                d.addSpeciesFound(species);
//            }
//        });
//        digSites.forEach(species::addDigSite);
//    }
//}
