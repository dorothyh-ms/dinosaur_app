//package be.kdg.dinosaurs.repository.digsites;
//
//import be.kdg.dinosaurs.domain.DigSite;
//import be.kdg.dinosaurs.domain.Species;
//import be.kdg.dinosaurs.exceptions.DigSiteNotAvailableException;
//import be.kdg.dinosaurs.repository.jdbc_row_mappers.DigSiteRowMapper;
//import be.kdg.dinosaurs.repository.jdbc_row_mappers.SpeciesRowMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Component;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@Component
//@Profile("jdbcbrepo")
//@Qualifier("H2")
//public class H2BDigSiteRepository implements DigSiteRepository {
//
//    private JdbcTemplate jdbcTemplate;
//    private SimpleJdbcInsert digSiteInserter;
//    private SpeciesRowMapper speciesRowMapper;
//    private DigSiteRowMapper digSiteRowMapper;
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//
//
//
//    @Autowired
//    public H2BDigSiteRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.digSiteInserter = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("digsites")
//                .usingGeneratedKeyColumns("digsite_id");
//        this.speciesRowMapper = new SpeciesRowMapper();
//        this.digSiteRowMapper = new DigSiteRowMapper();
//    }
//
//    @Override
//    public List<DigSite> readDigSites() {
//        List<DigSite> digSites = jdbcTemplate.query("SELECT * FROM digsites",
//                digSiteRowMapper::mapRow);
//        System.out.println(digSites);
//        return digSites;
//    }
//
//    @Override
//    public List<DigSite> readDigSites(String country) {
//        LOGGER.info("H2BDigSiteRepository is getting digsites in country {} from H2 database", country);
//        return jdbcTemplate.query("SELECT * FROM digsites WHERE country=?;",
//                digSiteRowMapper::mapRow,
//                country);
//    }
//
//
//    @Override
//    public DigSite createDigSite(DigSite site) {
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("digsite_name", site.getName());
//        parameters.put("country", site.getCountry());
//        parameters.put("latitude", site.getLatitude());
//        parameters.put("longitude", site.getLongitude());
//        parameters.put("first_excavation_date", Date.valueOf(site.getFirstExcavation()));
//        site.setId(digSiteInserter.executeAndReturnKey(parameters).intValue());
//        return site;
//    }
//
//    @Override
//    public DigSite readDigSiteById(int digSiteId) {
//        List<DigSite> digsiteList =  jdbcTemplate.query("SELECT * FROM digsites WHERE digsite_id = ?;",
//                digSiteRowMapper::mapRow,
//                digSiteId);
//        if (digsiteList.isEmpty()){
//            throw new DigSiteNotAvailableException(String.format("Digsite with ID %d does not exist", digSiteId));
//        }
//        DigSite digSite = digsiteList.get(0);
//        digSite.getSpeciesFound().addAll(getLinkedSpeciesList(digSite.getId()) );
//        return digSite;
//    }
//
//    private List<Species> getLinkedSpeciesList(int digSiteId) {
//        List<Species> species = jdbcTemplate.query("SELECT * FROM species JOIN discoveries USING (species_id) JOIN periods USING (period_id) WHERE digsite_id = ?",
//                speciesRowMapper::mapRow,
//                digSiteId);
//        return species;
//    }
//
//    @Override
//    public void updateDigSite(DigSite digSite) {
//        LOGGER.info("H2BDigSiteRepository is updating digsite with id {} in H2 database to {}", digSite.getId(), digSite);
//        jdbcTemplate.update(
//                "UPDATE digsites SET " +
//                        "digsite_name = ?, " +
//                        "country = ?, " +
//                        "diet = ?, " +
//                        "latitude = ?, " +
//                        "longitude = ?, " +
//                        "first_excavation_date =? " +
//                        "WHERE digsite_id = ?",
//                digSite.getName(),
//                digSite.getCountry(),
//                digSite.getLatitude(),
//                digSite.getLongitude(),
//                Date.valueOf(digSite.getFirstExcavation()),
//                digSite.getId());
//    }
//
//    @Override
//    public void deleteDigSiteById(int id) {
//        LOGGER.info("H2BDigSiteRepository is deleting digsite with id {} from H2 database", id);
//        DigSite digSite = readDigSiteById(id);
//        jdbcTemplate.update("DELETE FROM discoveries WHERE digsite_id=?", digSite.getId());
//        jdbcTemplate.update("DELETE FROM digsites WHERE digsite_id=?", digSite.getId());
//    }
//
//    @Override
//    public List<String> readCountries() {
//        LOGGER.info("H2BDigSiteRepository is getting all recorded countries from H2 database");
//        List<String> countries = jdbcTemplate.queryForList("SELECT DISTINCT country FROM digsites", String.class);
//        return countries;
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedBeforeDate(LocalDate beforeDate) {
//        LOGGER.info("H2BDigSiteRepository is getting all digsites excavated before {} from H2 database", beforeDate.toString());
//        return jdbcTemplate.query("SELECT * FROM digsites WHERE first_excavation_date < ?;",
//                digSiteRowMapper::mapRow,
//                Date.valueOf(beforeDate));
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedAfterDate(LocalDate afterDate) {
//        LOGGER.info("H2BDigSiteRepository is getting all digsites excavated after {} from H2 database", afterDate.toString());
//        return jdbcTemplate.query("SELECT * FROM digsites WHERE first_excavation_date > ?;",
//                digSiteRowMapper::mapRow,
//                Date.valueOf(afterDate));
//    }
//
//    @Override
//    public List<DigSite> readDigSitesExcavatedAfterDateAndBeforeDate(LocalDate afterDate, LocalDate beforeDate) {
//        LOGGER.info("H2BDigSiteRepository is getting all digsites excavated after {} and before {} from H2 database", afterDate.toString(), beforeDate.toString());
//        return jdbcTemplate.query("SELECT * FROM digsites WHERE first_excavation_date > ? AND first_excavation_date < ?;",
//                digSiteRowMapper::mapRow,
//                Date.valueOf(afterDate),
//                Date.valueOf(beforeDate));
//    }
//}
