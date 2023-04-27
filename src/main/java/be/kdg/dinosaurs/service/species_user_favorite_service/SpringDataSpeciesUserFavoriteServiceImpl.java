package be.kdg.dinosaurs.service.species_user_favorite_service;

import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.domain.User;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavorite;
import be.kdg.dinosaurs.repository.species.SpeciesRepository;
import be.kdg.dinosaurs.repository.species.SpringDataSpeciesRepository;
import be.kdg.dinosaurs.repository.species_user_favorites.SpeciesUserFavoriteRepository;
import be.kdg.dinosaurs.repository.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Profile(value = {"springdatarepo", "test"})
public class SpringDataSpeciesUserFavoriteServiceImpl implements SpeciesUserFavoriteService {

    private final SpringDataSpeciesRepository speciesRepository;

    private final UserRepository userRepository;

    private final SpeciesUserFavoriteRepository speciesUserFavoriteRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public SpringDataSpeciesUserFavoriteServiceImpl(SpringDataSpeciesRepository speciesRepository, UserRepository userRepository, SpeciesUserFavoriteRepository speciesUserFavoriteRepository) {
        this.speciesRepository = speciesRepository;
        this.userRepository = userRepository;
        this.speciesUserFavoriteRepository = speciesUserFavoriteRepository;
    }

    public void setUserOfSpeciesUserFavorite(SpeciesUserFavorite speciesUserFavorite, long userId){
        LOGGER.info("SpringDataSpeciesUserFavoriteServiceImpl is running setUserOfSpeciesUserFavorite");
        speciesUserFavorite.getId().setUser(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }

    public void setSpeciesOfSpeciesUserFavorite(SpeciesUserFavorite speciesUserFavorite, int speciesId){
        LOGGER.info("SpringDataSpeciesUserFavoriteServiceImpl is running setSpeciesOfSpeciesUserFavorite");
        speciesUserFavorite.getId().setSpecies(speciesRepository.findById(speciesId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public SpeciesUserFavorite addFavorite(SpeciesUserFavorite speciesUserFavorite) {
        LOGGER.info("SpringDataSpeciesUserFavoriteServiceImpl is running addFavorite");
        return speciesUserFavoriteRepository.save(speciesUserFavorite);
    }

    @Override
    public boolean userFavoritedSpecies(int speciesId, long userId) {
        LOGGER.info("SpringDataSpeciesUserFavoriteServiceImpl is running userFavoritedSpecies");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Species species = speciesRepository.findById(speciesId).orElseThrow(EntityNotFoundException::new);
        return speciesUserFavoriteRepository.existsById_UserAndId_Species(user, species);
    }

    public List<SpeciesUserFavorite> getFavoritesOfSpecies(int speciesId){
        LOGGER.info("SpringDataSpeciesUserFavoriteServiceImpl is running getFavoritesOfSpecies");
        Species species = speciesRepository.findById(speciesId).orElseThrow(EntityNotFoundException::new);
        return speciesUserFavoriteRepository.findById_Species(species);
    }

    public List<SpeciesUserFavorite> getFavoritesOfUser(long userId){
        LOGGER.info("SpringDataSpeciesUserFavoriteServiceImpl is running getFavoritesOfUser");
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return speciesUserFavoriteRepository.findById_User(user);
    }

    @Override
    @Transactional
    public void removeFavorite(int speciesId, long userId) {
        LOGGER.info("SpringDataSpeciesUserFavoriteServiceImpl is running removeFavorite");
        Species species = speciesRepository.findById(speciesId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        speciesUserFavoriteRepository.deleteById_UserAndId_Species(user, species);

    }
}
