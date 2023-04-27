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
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Repository
//@Profile("jparepo")
//public class JPASpeciesRepository implements SpeciesRepository {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    @Override
//    public List<Species> readSpecies() {
//        LOGGER.info("JPASpeciesRepository is getting all species from PostgresSQL database");
//        List<Species> species = entityManager.createQuery("SELECT s FROM Species s", Species.class).getResultList();
//        return species;
//    }
//
//
//    @Override
//    @Transactional
//    public Species createSpecies(Species species) {
//        LOGGER.info("JPASpeciesRepository is adding species {} to PostgresSQL database", species);
//        entityManager.persist(species);
//        return species;
//    }
//
//    @Override
//    public Species readSpeciesById(int id) {
//        LOGGER.info("JPASpeciesRepository is getting species with id {} from PostgresSQL database", id);
//        Species species = entityManager.find(Species.class, id);
//        if (species == null){
//            throw new SpeciesNotAvailableException(String.format("Species with ID %d does not exist", id));
//        }
//        LOGGER.debug("JPASpeciesRepository is returning species {} from PostgresSQL database", species);
//        return species;
//    }
//
//    @Override
//    @Transactional
//    public void updateSpecies(Species species) {
//        LOGGER.info("JPASpeciesRepository is merging species {} in PostgresSQL database", species);
//        entityManager.merge(species);
//    }
//
//    @Override
//    @Transactional
//    public void deleteSpeciesById(int id) {
//        LOGGER.info("JPASpeciesRepository is deleting species with id {} from PostgresSQL database", id);
//        Species species = readSpeciesById(id);
//        LOGGER.debug("JPASpeciesRepository is deleting species {} from PostgresSQL database", species);
//        entityManager.remove(species);
//    }
//
//
//
//    @Override
//    public List<Species> readSpeciesByPeriod(String periodName) {
//        LOGGER.info("JPASpeciesRepository is getting all species of period {} from PostgresSQL database", periodName);
//        TypedQuery<Species> query = entityManager.createQuery("select s from Species s join s.period p where p.name = :period_name", Species.class);
//        query.setParameter("period_name", periodName);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Diet> readDiets() {
//        LOGGER.info("JPASpeciesRepository is getting all recorded diets from PostgresSQL database");
//        return entityManager.createQuery("SELECT DISTINCT diet FROM Species s").getResultList();
//    }
//
//    @Override
//    public List<Species> readSpeciesByDiet(String dietName) {
//        LOGGER.info("JPASpeciesRepository is getting species with diet {} from PostgresSQL database", dietName);
//        TypedQuery<Species> query = entityManager.createQuery("SELECT s FROM Species s WHERE s.diet = :diet", Species.class);
//        query.setParameter("diet", Diet.valueOf(dietName));
//        return query.getResultList();
//
//    }
//
//    @Override
//    @Transactional
//    public void addDigSitesOfSpecies(Species species, List<DigSite> digSites) {
//        LOGGER.info("JPASpeciesRepository is adding digsites {} to species {}", digSites, species);
//        digSites.forEach(d -> species.getDigSites().add(d));
//    }
//}
