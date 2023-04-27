package be.kdg.dinosaurs.service.species_user_favorite_service;

import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavorite;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface SpeciesUserFavoriteService {
    public void setUserOfSpeciesUserFavorite(SpeciesUserFavorite speciesUserFavorite, long userId);

    public void setSpeciesOfSpeciesUserFavorite(SpeciesUserFavorite speciesUserFavorite, int speciesId);

    public SpeciesUserFavorite addFavorite(SpeciesUserFavorite speciesUserFavorite);

    public boolean userFavoritedSpecies(int speciesId, long userId);

    public List<SpeciesUserFavorite> getFavoritesOfSpecies(int speciesId);

    public void removeFavorite(int speciesId, long userId);

    public List<SpeciesUserFavorite> getFavoritesOfUser(long userId);
}
