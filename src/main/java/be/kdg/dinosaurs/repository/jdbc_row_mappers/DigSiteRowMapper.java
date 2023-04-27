package be.kdg.dinosaurs.repository.jdbc_row_mappers;

import be.kdg.dinosaurs.domain.DigSite;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DigSiteRowMapper implements RowMapper {
    @Override
    public DigSite mapRow(ResultSet rs, int rowNum) throws SQLException {
        DigSite digSite =  new DigSite(
                rs.getString("digsite_name"),
                rs.getString("country"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getDate("first_excavation_date").toLocalDate());
        digSite.setId(rs.getInt("digsite_id"));
        return digSite;
    }
}
