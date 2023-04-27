package be.kdg.dinosaurs.repository.periods;

import be.kdg.dinosaurs.domain.Period;
import be.kdg.dinosaurs.repository.jdbc_row_mappers.PeriodRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("jdbcbrepo")
@Qualifier("H2")
public class H2BPeriodRepository implements PeriodRepository{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert periodInserter;

    private PeriodRowMapper periodRowMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public H2BPeriodRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.periodInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("periods")
                .usingGeneratedKeyColumns("period_id");
        this.periodRowMapper = new PeriodRowMapper();

    }
    @Override
    public Period createPeriod(Period period) {
        LOGGER.info("H2BPeriodRepository is adding period {} to H2 database", period);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("period_name", period.getName());
        parameters.put("start_millions_years", period.getStartMillionsYears());
        parameters.put("end_millions_years", period.getEndMillionsYears());
        period.setId(periodInserter.executeAndReturnKey(parameters).intValue());
        return period;
    }

    @Override
    public Period readByName(String name) {
        LOGGER.info("H2BPeriodRepository is getting period with name {} from H2 database", name);
        Period period = jdbcTemplate.queryForObject(
                "SELECT * FROM periods WHERE LOWER(period_name)=?",
                periodRowMapper::mapRow,
                name);
        return period;
    }

    @Override
    public List<Period> readPeriods() {
        LOGGER.info("H2BPeriodRepository is getting all periods from H2 database");
        return jdbcTemplate.query("SELECT * FROM periods;",
                periodRowMapper::mapRow);
    }
}
