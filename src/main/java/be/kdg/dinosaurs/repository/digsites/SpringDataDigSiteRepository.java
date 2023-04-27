package be.kdg.dinosaurs.repository.digsites;

import be.kdg.dinosaurs.domain.DigSite;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
@Profile(value = {"springdatarepo", "test"})

public interface SpringDataDigSiteRepository extends JpaRepository<DigSite, Integer> {
    List<DigSite> findByCountry(String country);
    @Query(value="SELECT DISTINCT country FROM DIGSITES d", nativeQuery=true)
    List<String> findAllCountries();

    List<DigSite> findByFirstExcavationAfter(LocalDate date);

    List<DigSite> findByFirstExcavationBefore(LocalDate date);

    List<DigSite> findByFirstExcavationAfterAndFirstExcavationBefore(LocalDate after, LocalDate before);
}