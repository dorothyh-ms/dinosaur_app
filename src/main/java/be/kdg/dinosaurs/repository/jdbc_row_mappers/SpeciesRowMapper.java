package be.kdg.dinosaurs.repository.jdbc_row_mappers;

import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Species;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeciesRowMapper implements RowMapper {
    @Override
    public Species mapRow(ResultSet rs, int rowNum) throws SQLException {
        Species species =  new Species(
                rs.getString("species_name"),
                rs.getString("scientific_name"),
                rs.getInt("number_specimens_found"),
                Diet.valueOf(rs.getString("diet")),
                rs.getString("image_url"));
        species.setId(rs.getInt("species_id"));
        return species;
    }
}
