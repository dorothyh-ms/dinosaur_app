package be.kdg.dinosaurs.repository.species;

import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Species;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile(value = {"springdatarepo", "test"})
public interface SpringDataSpeciesRepository extends JpaRepository<Species, Integer> {


    @Query(value="SELECT * FROM SPECIES s JOIN PERIODS p ON s.period_id = p.period_id WHERE UPPER(p.period_name) = UPPER(?1)", nativeQuery=true)
    List<Species> findSpeciesByPeriodName(String periodName);

    @Query(value="SELECT DISTINCT s FROM Species s LEFT JOIN FETCH s.discoveries d LEFT JOIN FETCH s.period p")
    List<Species> findAll();
    List<Species> findByDiet(Diet diet);

    @Query(value="SELECT DISTINCT s FROM Species s LEFT JOIN FETCH s.discoveries d LEFT JOIN FETCH s.period p WHERE s.id = ?1")
    public Optional<Species> findById(int id);
}
