package be.kdg.dinosaurs.repository.periods;

import be.kdg.dinosaurs.domain.Period;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile(value = {"springdatarepo", "test"})
public interface SpringDataPeriodRepository extends JpaRepository<Period, Integer> {
    Optional<Period> findByNameIgnoreCase(String periodName);
}
