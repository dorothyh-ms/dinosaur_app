//package be.kdg.dinosaurs.repository.species;
//
//import be.kdg.dinosaurs.domain.Diet;
//import be.kdg.dinosaurs.domain.DigSite;
//import be.kdg.dinosaurs.domain.Species;
//import be.kdg.dinosaurs.exceptions.SpeciesNotAvailableException;
//import be.kdg.dinosaurs.repository.jdbc_row_mappers.DigSiteRowMapper;
//import be.kdg.dinosaurs.repository.jdbc_row_mappers.PeriodRowMapper;
//import be.kdg.dinosaurs.repository.jdbc_row_mappers.SpeciesRowMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import be.kdg.dinosaurs.domain.Period;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@Profile("jdbcbrepo")
//@Qualifier("H2")
//public class H2BSpeciesRepository implements SpeciesRepository {
//    private JdbcTemplate jdbcTemplate;
//    private SimpleJdbcInsert speciesInserter;
//    private SpeciesRowMapper speciesRowMapper;
//    private DigSiteRowMapper digSiteRowMapper;
//    private PeriodRowMapper periodRowMapper;
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//
//    @Autowired
//    public H2BSpeciesRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.speciesInserter = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("species")
//                .usingGeneratedKeyColumns("species_id");
//        this.digSiteRowMapper = new DigSiteRowMapper();
//        this.speciesRowMapper = new SpeciesRowMapper();
//        this.periodRowMapper = new PeriodRowMapper();
//    }
//
//    @Override
//    public List<Species> readSpecies() {
//        LOGGER.info("H2BSpeciesRepository is getting all species from H2 database");
//        List<Species> species = jdbcTemplate.query("SELECT * FROM species JOIN periods USING(period_id)",
//                speciesRowMapper::mapRow);
//        species.forEach(this::loadPeriod);
//        return species;
//    }
//
//
//    @Override
//    public Species createSpecies(Species species) {
//        LOGGER.info("H2BSpeciesRepository is adding species {} to H2 database", species);
//        int period_id = jdbcTemplate.queryForObject(
//                "SELECT period_id from periods WHERE period_name = ?;",
//                Integer.class,
//                species.getPeriod().getName());
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("species_name", species.getName());
//        parameters.put("scientific_name", species.getScientificName());
//        parameters.put("number_specimens_found", species.getNumberSpecimensFound());
//        parameters.put("diet", species.getDiet().toString());
//        parameters.put("period_id", period_id);
//        parameters.put("image_url", species.getImage());
//        species.setId(speciesInserter.executeAndReturnKey(parameters).intValue());
//        return species;
//    }
//
//    @Override
//    public Species readSpeciesById(int id) {
//        LOGGER.info("H2BSpeciesRepository is getting species with id {} from H2 database", id);
//        List<Species> speciesList = jdbcTemplate.query(
//                "SELECT * FROM species WHERE species_id=?",
//                speciesRowMapper::mapRow,
//                id);
//        if (speciesList.isEmpty()) {
//            throw new SpeciesNotAvailableException(String.format("Species with ID %d does not exist", id));
//        }
//        Species species = speciesList.get(0);
//        species.getDigSites().addAll(getLinkedDigSitesList(species.getId()));
//        loadPeriod(species);
//        return species;
//    }
//
//    private List<DigSite> getLinkedDigSitesList(int id) {
//        LOGGER.info("H2BSpeciesRepository is getting digsites of species with id {} from H2 database", id);
//        List<DigSite> digsites = jdbcTemplate.query("SELECT * FROM digsites JOIN discoveries USING (digsite_id) WHERE species_id = ?",
//                digSiteRowMapper::mapRow,
//                id);
//        return digsites;
//    }
//
//    @Override
//    public void updateSpecies(Species species) {
//        LOGGER.info("H2BSpeciesRepository is updating species {} in H2 database", species);
//        jdbcTemplate.update(
//                "UPDATE species SET " +
//                        "species_name = ?, " +
//                        "scientific_name = ?, " +
//                        "diet = ?, " +
//                        "number_specimens_found = ?, " +
//                        "period_id = (SELECT period_id FROM periods WHERE period_name = ?), " +
//                        "image_url = ? " +
//                        "WHERE species_id = ?",
//                species.getName(),
//                species.getScientificName(),
//                species.getDiet().toString(),
//                species.getNumberSpecimensFound(),
//                species.getPeriod().getName(),
//                species.getImage(),
//                species.getId());
//    }
//
//    @Override
//    public void deleteSpeciesById(int id) {
//        LOGGER.info("H2BSpeciesRepository is deleting species with id {} from H2 database", id);
//        Species species = readSpeciesById(id);
//        jdbcTemplate.update("DELETE FROM discoveries WHERE species_id = ?", species.getId());
//        jdbcTemplate.update("DELETE FROM species WHERE species_id = ?", species.getId());
//    }
//
//
//    @Override
//    public List<Species> readSpeciesByPeriod(String periodName) {
//        LOGGER.info("H2BSpeciesRepository is getting all species of period {} from H2 database", periodName);
//        List<Species> species = jdbcTemplate.query("SELECT * FROM species JOIN periods USING(period_id) WHERE period_name=?",
//                speciesRowMapper::mapRow,
//                periodName);
//        species.forEach(this::loadPeriod);
//        return species;
//    }
//
//    @Override
//    public List<Diet> readDiets() {
//        LOGGER.info("H2BSpeciesRepository is getting all recorded diets from H2 database");
//        return jdbcTemplate.query("SELECT DISTINCT diet FROM species", new RowMapper<Diet>() {
//            public Diet mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return Diet.valueOf(rs.getString(1));
//            }
//        });
//    }
//
//    @Override
//    public List<Species> readSpeciesByDiet(String dietName) {
//        LOGGER.info("H2BSpeciesRepository is getting species with diet {} from H2 database", dietName);
//        List<Species> species = jdbcTemplate.query("SELECT * FROM species WHERE diet=?",
//                speciesRowMapper::mapRow,
//                dietName);
//        species.forEach(this::loadPeriod);
//        return species;
//    }
//
//    private void loadPeriod(Species species) {
//        LOGGER.info("H2BSpeciesRepository is loading period of species {} from H2 database", species);
//        Period period = jdbcTemplate.queryForObject("SELECT * FROM periods WHERE period_id = (SELECT period_id FROM species WHERE species_id =?)",
//                periodRowMapper::mapRow,
//                species.getId());
//        LOGGER.debug("H2SpeciesRepository found period {}", period);
//        species.setPeriod(period);
//    }
//
//    @Override
//    public void addDigSitesOfSpecies(Species species, List<DigSite> digSites) {
//        LOGGER.info("H2BSpeciesRepository is clearing species' digsites {} from H2 database", species);
//        jdbcTemplate.update("DELETE FROM discoveries WHERE species_id=?",
//                species.getId());
//        LOGGER.info("H2BSpeciesRepository is adding digsites {} to species {} from H2 database", digSites, species);
//        digSites.forEach(d -> jdbcTemplate.update("INSERT INTO discoveries(species_id, digsite_id) VALUES (?, ?)",
//                species.getId(),
//                d.getId()));
//    }
//}
