package be.kdg.dinosaurs.repository.jdbc_row_mappers;

import be.kdg.dinosaurs.domain.Period;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodRowMapper implements RowMapper {
    @Override
    public Period mapRow(ResultSet rs, int rowNum) throws SQLException {
        Period period = new Period(
                rs.getString("period_name"),
                rs.getDouble("start_millions_years"),
                rs.getDouble("end_millions_years"));
        period.setId(rs.getInt("period_id"));
        return period;
    }
}
