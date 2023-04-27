//package be.kdg.dinosaurs.repository.digsites;
//
//import be.kdg.dinosaurs.domain.DigSite;
//import be.kdg.dinosaurs.exceptions.DigSiteNotAvailableException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//@Profile("jparepo")
//public class JPADigSiteRepository implements DigSiteRepository {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Override
//    @Transactional
//    public List<DigSite> readDigSites() {
//        LOGGER.info("JPADigSiteRepository is getting all digsites from PostgresSQL database");
//        List<DigSite> digSites = entityManager.createQuery("SELECT d FROM DigSite d")
//                .getResultList();
//        return digSites;
//    }
//
//    @Override
//    @Transactional
//    public List<DigSite> readDigSites(String country) {
//        LOGGER.info("JPADigSiteRepository is getting digsites in country {} from PostgresSQL database", country);
//        Query query = entityManager.createQuery("SELECT d FROM DigSite d WHERE d.country = :country");
//        query.setParameter("country", country);
//        return query.getResultList();
//    }
//
//
//    @Override
//    @Transactional
//    public DigSite createDigSite(DigSite digSite) {
//        LOGGER.info("JPADigSiteRepository is adding digsite {} to PostgresSQL database", digSite);
//        entityManager.persist(digSite);
//        return digSite;
//    }
//
//    @Override
//    @Transactional
//    public DigSite readDigSiteById(int id) {
//        LOGGER.info("JPADigSiteRepository is getting digsite with id {} from PostgresSQL database", id);
//        DigSite digSite = entityManager.find(DigSite.class, id);
//        if (digSite == null){
//            throw new DigSiteNotAvailableException(String.format("Digsite with ID %d does not exist", id));
//        }
//        return digSite;
//
//    }
//
//    @Override
//    @Transactional
//    public void updateDigSite(DigSite digSite) {
//        LOGGER.info("JPADigSiteRepository is merging digsite {} in PostgresSQL database", digSite);
//        entityManager.merge(digSite);
//    }
//
//    @Override
//    @Transactional
//    public void deleteDigSiteById(int id) {
//        LOGGER.info("JPADigSiteRepository is deleting digsite with id {} from PostgresSQL database", id);
//        DigSite digSite = readDigSiteById(id);
//        LOGGER.info("JPADigSiteRepository is deleting digsite {} from each species in list {}", digSite, digSite.getSpeciesFound());
//        digSite.getSpeciesFound().forEach(s-> s.getDigSites().remove(digSite));
//        LOGGER.debug("JPADigSiteRepository is deleting digsite {} from PostgresSQL database", digSite);
//        entityManager.remove(entityManager.find(DigSite.class, id));
//    }
//
//    @Override
//    @Transactional
//    public List<String> readCountries() {
//        LOGGER.info("JPADigSiteRepository is getting all recorded countries from PostgresSQL database");
//        return entityManager.createNativeQuery("SELECT DISTINCT country FROM digsites").getResultList();
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedBeforeDate(LocalDate beforeDate) {
//        LOGGER.info("JPADigSiteRepository is getting all digsites excavated before {} from PostgresSQL database", beforeDate.toString());
//        Query query = entityManager.createQuery("SELECT d FROM DigSite d WHERE d.firstExcavation < :beforeDate");
//        query.setParameter("beforeDate", beforeDate);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedAfterDate(LocalDate afterDate) {
//        LOGGER.info("JPADigSiteRepository is getting all digsites excavated after {} from PostgresSQL database", afterDate.toString());
//        Query query = entityManager.createQuery("SELECT d FROM DigSite d WHERE d.firstExcavation > :afterDate");
//        query.setParameter("afterDate", afterDate);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedAfterDateAndBeforeDate(LocalDate afterDate, LocalDate beforeDate) {
//        LOGGER.info("JPADigSiteRepository is getting all digsites excavated after {} and before {} from PostgresSQL database", afterDate.toString(), beforeDate.toString());
//        Query query = entityManager.createQuery("SELECT d FROM DigSite d WHERE d.firstExcavation > :afterDate AND d.firstExcavation < :beforeDate");
//        query.setParameter("afterDate", afterDate);
//        query.setParameter("beforeDate", beforeDate);
//        return query.getResultList();
//    }
//}
