//package be.kdg.dinosaurs.repository.digsites;
//
//import be.kdg.dinosaurs.domain.DigSite;
//import be.kdg.dinosaurs.exceptions.DigSiteNotAvailableException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Component
//@Profile("listrepo")
//public class ListDigSiteRepository implements DigSiteRepository {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    private static final List<DigSite> digSites = new ArrayList<>();
//
//
//    @Override
//    public DigSite createDigSite(DigSite digSite) {
//        digSite.setId(digSites.size());
//        LOGGER.info("ListDigSiteRepository is adding digsite {} to its list of digsites", digSite);
//        digSites.add(digSite);
//        return digSite;
//    }
//
//    @Override
//    public List<DigSite> readDigSites() {
//        LOGGER.info("ListDigSiteRepository is getting all digsites from its list of digsites");
//        return digSites;
//    }
//
//
//    @Override
//    public List<DigSite> readDigSites(String country) {
//        LOGGER.info("ListDigSiteRepository is getting digsites in country {} from its list of digsites", country);
//        List<DigSite> filteredSites = digSites.stream().filter(d -> d.getCountry().equals(country)).collect(Collectors.toList());
//        return filteredSites;
//    }
//
//    @Override
//    public DigSite readDigSiteById(int id) {
//        LOGGER.info("ListDigSiteRepository is getting digsite with id {} from its list of digsites", id);
//        Optional<DigSite> digSite = digSites.stream().filter(d -> d.getId() == id).findFirst();
//        if (digSite.isPresent()) {
//            return digSite.get();
//        }
//        throw new DigSiteNotAvailableException(String.format("Digsite with ID %d does not exist", id));
//    }
//
//    @Override
//    public void updateDigSite(DigSite digSite) {
//        LOGGER.info("ListDigSiteRepository is updating digsite with id {} in its list of digsites to {}", digSite.getId(), digSite);
//        DigSite digSiteToUpdate = digSites.stream().filter(d -> d.getId() == digSite.getId()).findFirst().get();
//        digSiteToUpdate.setName(digSite.getName());
//        digSiteToUpdate.setCountry(digSite.getCountry());
//        digSiteToUpdate.setLatitude(digSite.getLatitude());
//        digSiteToUpdate.setLongitude(digSite.getLongitude());
//        digSiteToUpdate.setFirstExcavation(digSite.getFirstExcavation());
//    }
//
//    @Override
//    public void deleteDigSiteById(int id) {
//        LOGGER.info("ListDigSiteRepository is deleting digsites with id {} from its list of digsites", id);
//        DigSite digSite = readDigSiteById(id);
//        digSite.getSpeciesFound().forEach(species -> species.getDigSites().remove(digSite));
//        digSites.remove(digSite);
//    }
//
//    @Override
//    public List<String> readCountries() {
//        LOGGER.info("ListDigSiteRepository is getting all recorded countries from its list of digsites");
//        return digSites.stream().map(d -> d.getCountry()).distinct().collect(Collectors.toList());
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedBeforeDate(LocalDate beforeDate) {
//        LOGGER.info("ListDigSiteRepository is getting all digsites excavated before {} from its list of digsites", beforeDate.toString());
//        return digSites.stream().filter(d -> d.getFirstExcavation().isBefore(beforeDate)).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedAfterDate(LocalDate afterDate) {
//        LOGGER.info("ListDigSiteRepository is getting all digsites excavated after {} from its list of digsites", afterDate.toString());
//        return digSites.stream().filter(d -> d.getFirstExcavation().isAfter(afterDate)).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedAfterDateAndBeforeDate(LocalDate afterDate, LocalDate beforeDate) {
//        LOGGER.info("ListDigSiteRepository is getting all digsites excavated after {} and before {} from its list of digsites", afterDate.toString(), beforeDate.toString());
//        return digSites.stream().filter(d -> d.getFirstExcavation().isAfter(afterDate) && d.getFirstExcavation().isBefore(beforeDate)).collect(Collectors.toList());
//    }
//}
