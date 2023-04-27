package be.kdg.dinosaurs.repository.discoveries;

import be.kdg.dinosaurs.domain.DigSite;
import be.kdg.dinosaurs.domain.Discovery;
import be.kdg.dinosaurs.domain.DiscoveryId;
import be.kdg.dinosaurs.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SpringDataDiscoveryRepository extends JpaRepository<Discovery, DiscoveryId> {
    List<Discovery> findById_Species(Species species);
    List<Discovery> findById_DigSite(DigSite digSite);


    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM discoveries d WHERE d.species_id =?1 AND  d.digsite_id =?2", nativeQuery = true)
    boolean existsBySpeciesIdAndDigSiteId(int speciesId, int digSiteId);
}
