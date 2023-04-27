package be.kdg.dinosaurs.service.digsites;

import be.kdg.dinosaurs.domain.DigSite;
import be.kdg.dinosaurs.domain.Discovery;
import be.kdg.dinosaurs.repository.JSONWriter;
import be.kdg.dinosaurs.repository.digsites.SpringDataDigSiteRepository;
import be.kdg.dinosaurs.repository.species.SpringDataSpeciesRepository;
import be.kdg.dinosaurs.repository.discoveries.SpringDataDiscoveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@Profile(value = {"springdatarepo", "test"})
public class SpringDataDigSiteServiceImpl implements DigSiteService {
    private SpringDataDigSiteRepository digSiteRepository;
    private SpringDataSpeciesRepository speciesRepository;

    private SpringDataDiscoveryRepository speciesDigsiteRepository;

    private JSONWriter jsonWriter;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public SpringDataDigSiteServiceImpl(SpringDataDigSiteRepository digSiteRepository, SpringDataSpeciesRepository speciesRepository, SpringDataDiscoveryRepository speciesDigsiteRepository, JSONWriter jsonWriter) {
        this.digSiteRepository = digSiteRepository;
        this.speciesRepository = speciesRepository;
        this.speciesDigsiteRepository = speciesDigsiteRepository;
        this.jsonWriter = jsonWriter;
    }

    @Override
    public List<DigSite> getDigSites() {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting digsites");
        return digSiteRepository.findAll();
    }

    @Override
    public List<DigSite> getDigSites(String country) {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting digsites with country {}", country);
        List<DigSite> digSites = digSiteRepository.findByCountry(country);
        LOGGER.debug("DigSiteServiceImpl is returning species list {}", digSites);
        return digSites;
    }


    @Override
    public DigSite addDigSite(DigSite digSite) {
        LOGGER.debug("SpringDataDigSiteServiceImpl is saving digsite {}", digSite);
        digSiteRepository.save(digSite);
        return digSite;
    }

    @Override
    public DigSite getDigSiteById(int id) {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting digsite with id {}", id);
        DigSite digSite = digSiteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        LOGGER.debug("SpringDataDigSiteServiceImpl is returning digsite {}", digSite);
        return digSite;

    }

    @Override
    public void removeDigSite(int id) {
        LOGGER.info("SpringDataDigSiteServiceImpl is deleting digsite with id {}", id);
        DigSite digSite = getDigSiteById(id);
        digSiteRepository.deleteById(digSite.getId());
    }

    @Override
    public List<Discovery> getSpeciesOfDigSite(int digSiteId) {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting species found in digsite with id {}", digSiteId);
        DigSite digSite = digSiteRepository.findById(digSiteId).orElseThrow(EntityNotFoundException::new);
        List<Discovery> discoveries = speciesDigsiteRepository.findById_DigSite(digSite);
        LOGGER.debug("SpringDataSpeciesServiceImpl is returning speciesDigSite list {}", discoveries);
        return discoveries;
    }


    @Override
    public void changeDigSite(DigSite digSite) {
        LOGGER.info("SpringDataDigSiteServiceImpl is editing digsite {}", digSite);
        digSiteRepository.save(digSite);
    }

    @Override
    public List<String> getCountries() {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting all recorded countries");
        List<String> countries = digSiteRepository.findAllCountries();
        LOGGER.debug("SpringDataDigSiteServiceImpl is returning list of strings of country names {}", countries);
        return countries;
    }

    @Override
    public void writeDigSitesToJSON(List<DigSite> digSites) {

        LOGGER.info("SpringDataDigSiteServiceImpl is writing digsites to JSON");
        jsonWriter.writeDigSites(digSites);
    }


    @Override
    public List<DigSite> getDigSitesBeforeDate(LocalDate beforeDate) {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting digsites excavated before {}", beforeDate);
        return digSiteRepository.findByFirstExcavationBefore(beforeDate);
    }

    @Override
    public List<DigSite> getDigSitesAfterDate(LocalDate afterDate) {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting digsites excavated after {}", afterDate);
        return digSiteRepository.findByFirstExcavationAfter(afterDate);
    }

    @Override
    public List<DigSite> getDigSitesAfterDateAndBeforeDate(LocalDate afterDate, LocalDate beforeDate) {
        LOGGER.info("SpringDataDigSiteServiceImpl is getting digsites excavated before {} and after {}", beforeDate, afterDate);
        return digSiteRepository.findByFirstExcavationAfterAndFirstExcavationBefore(afterDate, beforeDate);
    }

    @Override
    public boolean digSiteExists(int digSiteId) {
        return digSiteRepository.existsById(digSiteId);
    }

    @Override
    public boolean updateDigSite(int digSiteId, String country, double latitude, double longitude, LocalDate firstExcavated) {
        LOGGER.info("SpringDataDigSiteServiceImpl is updating digsite with id {}", digSiteId);
        var digSite = digSiteRepository.findById(digSiteId).orElse(null);
        if (digSite == null) {
            return false;
        }
        digSite.setCountry(country);
        digSite.setLatitude(latitude);
        digSite.setLongitude(longitude);
        digSite.setFirstExcavation(firstExcavated);
        digSiteRepository.save(digSite);
        return true;
    }
}
