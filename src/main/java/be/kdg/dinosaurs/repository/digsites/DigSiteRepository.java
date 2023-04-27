package be.kdg.dinosaurs.repository.digsites;

import be.kdg.dinosaurs.domain.DigSite;

import java.time.LocalDate;
import java.util.List;

public interface DigSiteRepository  {




    public List<DigSite> readDigSites();

    public List<DigSite> readDigSites(String country);

    public DigSite createDigSite(DigSite site);

    public DigSite readDigSiteById(int digSiteId);

    public void updateDigSite(DigSite digSite);

    public void deleteDigSiteById(int id);

    public List<String> readCountries();

    public List<DigSite> readDigSitesExcavatedBeforeDate(LocalDate beforeDate);

    public List<DigSite> readDigSitesExcavatedAfterDate(LocalDate afterDate);

    public List<DigSite> readDigSitesExcavatedAfterDateAndBeforeDate(LocalDate afterDate, LocalDate beforeDate);

}
