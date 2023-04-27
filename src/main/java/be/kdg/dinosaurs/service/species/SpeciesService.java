package be.kdg.dinosaurs.service.species;

import be.kdg.dinosaurs.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SpeciesService {


    public List<Species> getSpecies();


    public Species getSpeciesById(int id);

    public Species addSpecies(Species species);


    public void removeSpecies(int id);

    public List<Discovery> getDiscoveriesOfSpecies(int id);

    public void changeSpecies(Species species);

    public List<Period> getPeriods();

    public List<Species> getSpeciesByPeriod(String periodName);

    public List<Diet> getDiets();

    public List<Species> getSpeciesByDiet(String diet);

    public void setPeriodOfSpecies(Species species, String periodName);

    public void writeSpeciesToJSON(List<Species> species);

    public void addDigSitesOfSpecies(Species species, Map<Integer, LocalDate> digSiteIdsDatesFound);

    public boolean speciesExists(int id);

    public void updateSpecies(int id, String scientificName, int numberOfSpecimens, String dietName, String periodName);

}
