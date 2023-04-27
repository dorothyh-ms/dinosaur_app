//package be.kdg.dinosaurs.service.digsites;
//
//import be.kdg.dinosaurs.domain.DigSite;
//import be.kdg.dinosaurs.domain.Species;
//import be.kdg.dinosaurs.exceptions.DigSiteNotAvailableException;
//import be.kdg.dinosaurs.repository.JSONWriter;
//import be.kdg.dinosaurs.repository.digsites.DigSiteRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@Profile("!springdatarepo")
//public class DigSiteServiceImpl implements DigSiteService {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    private DigSiteRepository digSiteRepository;
//    private JSONWriter jsonWriter;
//
//
//
//    public DigSiteServiceImpl(DigSiteRepository digSiteRepository, JSONWriter jsonWriter) {
//        this.digSiteRepository = digSiteRepository;
//        this.jsonWriter=jsonWriter;
//
//    }
//
//    @Override
//    public List<DigSite> getDigSites() {
//        LOGGER.info("DigSiteServiceImpl is getting digsites");
//        return digSiteRepository.readDigSites();
//
//    }
//
//
//
//    @Override
//    public List<DigSite> getDigSites(String country) {
//        LOGGER.info("DigSiteServiceImpl is getting digsites in country {}", country);
//        List<DigSite> digSites = digSiteRepository.readDigSites(country);
//        LOGGER.debug("DigSiteServiceImpl is returning digsite list {}", digSites);
//        return digSites;
//    }
//
//    @Override
//    public DigSite addDigSite(DigSite digSite) {
//        LOGGER.debug("DigSiteServiceImpl is adding digsite {}", digSite);
//        return digSiteRepository.createDigSite(digSite);
//    }
//
//    public DigSite getDigSiteById(int id){
//        LOGGER.info("DigSiteServiceImpl is getting digsite with id {}", id);
//        DigSite digSite = digSiteRepository.readDigSiteById(id);
//        LOGGER.debug("DigSiteServiceImpl is returning digsite {}", digSite);
//        if (digSite == null){
//            throw new DigSiteNotAvailableException(String.format("Digsite with ID %d does not exist", id));
//        }
//        return digSite;
//    };
//
//    @Override
//    public void removeDigSite(int id) {
//        LOGGER.info("DigSiteServiceImpl is deleting digsite with id {}", id);
//        digSiteRepository.deleteDigSiteById(id);
//    }
//
//    @Override
//    @Transactional
//    public List<Species> getSpeciesOfDigSite(int id){
//        LOGGER.info("DigSiteServiceImpl is getting species found in digsite with id {}", id);
//        List<Species> speciesFound = digSiteRepository.readDigSiteById(id).getSpeciesFound();
//        LOGGER.debug("DigSiteServiceImpl is returning species list {}", speciesFound);
//        return speciesFound;
//    }
//
//    @Override
//    public void changeDigSite(DigSite digsite) {
//        digSiteRepository.updateDigSite(digsite);
//    }
//
//    @Override
//    public List<String> getCountries() {
//        LOGGER.info("DigSiteServiceImpl is getting all recorded countries");
//        List<String> countries = digSiteRepository.readCountries();
//        LOGGER.debug("DigSiteServiceImpl is returning list of strings of country names {}", countries);
//        return countries;
//    }
//
//    @Override
//    public void writeDigSitesToJSON(List<DigSite> digSites) {
//        LOGGER.info("DigSiteServiceImpl is writing digsites to JSON");
//        jsonWriter.writeDigSites(digSites);
//    }
//
//    @Override
//    public List<DigSite> getDigSitesBeforeDate(LocalDate beforeDate) {
//        LOGGER.info("DigSiteServiceImpl is getting digsites excavated before {}", beforeDate);
//        return digSiteRepository.readDigSitesExcavatedBeforeDate(beforeDate);
//    }
//
//    @Override
//    public List<DigSite> getDigSitesAfterDate(LocalDate afterDate) {
//        LOGGER.info("DigSiteServiceImpl is getting digsites excavated after {}", afterDate);
//        return digSiteRepository.readDigSitesExcavatedAfterDate(afterDate);
//    }
//
//    @Override
//    public List<DigSite> getDigSitesAfterDateAndBeforeDate(LocalDate afterDate, LocalDate beforeDate) {
//        LOGGER.info("DigSiteServiceImpl is getting digsites excavated before {} and after {}", beforeDate, afterDate);
//        return digSiteRepository.readDigSitesExcavatedAfterDateAndBeforeDate(afterDate, beforeDate);
//    }
//
//    @Override
//    public boolean digSiteExists(int digSiteId) {
//        //TODO
//        return false;
//    }
//
//    @Override
//    public boolean updateDigSite(int digSiteId, String country, double latitude, double longitude, LocalDate firstExcavated) {
//        return false;
//    }
//}
