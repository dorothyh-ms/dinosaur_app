package be.kdg.dinosaurs.repository.species_user_favorites;

import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.domain.User;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavorite;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavoriteId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile(value = {"springdatarepo", "test"})
public interface SpeciesUserFavoriteRepository extends JpaRepository<SpeciesUserFavorite, SpeciesUserFavoriteId> {
    public boolean existsById_UserAndId_Species(User user, Species species);

    public List<SpeciesUserFavorite> findById_Species(Species species);

    public void deleteById_UserAndId_Species(User user, Species species);

    public List<SpeciesUserFavorite> findById_User(User user);
}
