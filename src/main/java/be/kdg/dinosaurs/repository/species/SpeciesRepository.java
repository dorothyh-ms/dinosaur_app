package be.kdg.dinosaurs.repository.species;

import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.DigSite;
import be.kdg.dinosaurs.domain.Species;

import java.util.List;

public interface SpeciesRepository {


    public List<Species> readSpecies();


    public Species createSpecies(Species species);

    public Species readSpeciesById(int id);

    public void updateSpecies(Species species);
    public void deleteSpeciesById(int id);


    public List<Species> readSpeciesByPeriod(String periodName);

    public List<Diet> readDiets();

    public List<Species> readSpeciesByDiet(String dietName);

    public void addDigSitesOfSpecies(Species species, List<DigSite> digSites);


}
