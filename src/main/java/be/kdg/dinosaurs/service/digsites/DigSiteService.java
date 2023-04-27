package be.kdg.dinosaurs.service.digsites;

import be.kdg.dinosaurs.domain.DigSite;
import be.kdg.dinosaurs.domain.Discovery;

import java.time.LocalDate;
import java.util.List;

public interface DigSiteService {

    public List<DigSite> getDigSites();

    public List<DigSite> getDigSites(String country);



    public DigSite addDigSite(DigSite digSite);

    DigSite getDigSiteById(int digSiteId);

    public void removeDigSite(int id);

    public boolean digSiteExists(int digSiteId);

    public List<Discovery> getSpeciesOfDigSite(int digSiteId);

    public void changeDigSite(DigSite digsite);

    public List<String> getCountries();

    public void writeDigSitesToJSON(List<DigSite> digSites);

    public List<DigSite> getDigSitesBeforeDate(LocalDate beforeDate);

    public List<DigSite> getDigSitesAfterDate(LocalDate afterDate);

    public List<DigSite> getDigSitesAfterDateAndBeforeDate(LocalDate afterDate, LocalDate beforeDate);

    public boolean updateDigSite(int digSiteId, String country, double latitude, double longitude, LocalDate firstExcavated);
}
