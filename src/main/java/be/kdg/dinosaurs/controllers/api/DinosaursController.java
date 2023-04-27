package be.kdg.dinosaurs.controllers.api;

import be.kdg.dinosaurs.controllers.api.dtos.*;
import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.domain.User;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavorite;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavoriteId;
import be.kdg.dinosaurs.security.CustomUserDetails;
import be.kdg.dinosaurs.service.species.SpeciesService;
import be.kdg.dinosaurs.service.species_user_favorite_service.SpeciesUserFavoriteService;
import be.kdg.dinosaurs.service.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/dinosaurs")
public class DinosaursController {


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SpeciesService speciesService;

    private final UserService userService;

    private final SpeciesUserFavoriteService speciesUserFavoriteService;

    public DinosaursController(SpeciesService speciesService, UserService userService, SpeciesUserFavoriteService speciesUserFavoriteService) {
        this.speciesService = speciesService;
        this.userService = userService;
        this.speciesUserFavoriteService = speciesUserFavoriteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeciesDto> getDinosaur(@PathVariable("id") int dinosaurId) {
        LOGGER.info("DinosaursController is running getDinosaur");
        var species = speciesService.getSpeciesById(dinosaurId);
        return new ResponseEntity<>(
                new SpeciesDto(
                        species.getId(),
                        species.getName(),
                        species.getScientificName(),
                        species.getNumberSpecimensFound(),
                        species.getDiet(),
                        new PeriodDto(
                                species.getPeriod().getName(),
                                species.getPeriod().getStartMillionsYears(),
                                species.getPeriod().getEndMillionsYears())
                ),
                HttpStatus.OK);
    }


    @GetMapping("/{id}/digsites")
    public ResponseEntity<List<DigSiteDto>> getDigSitesOfDinosaur(@PathVariable("id") int dinosaurId) {
        LOGGER.info("DinosaursController is running getDigSitesOfDinosaur");
        var speciesDigSites = speciesService.getDiscoveriesOfSpecies(dinosaurId);
        if (speciesDigSites.size() == 0) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    speciesDigSites.stream().map(
                            speciesDigSite -> new DigSiteDto(
                                    speciesDigSite.getId().getDigSite().getId(),
                                    speciesDigSite.getId().getDigSite().getName(),
                                    speciesDigSite.getId().getDigSite().getCountry(),
                                    speciesDigSite.getId().getDigSite().getLatitude(),
                                    speciesDigSite.getId().getDigSite().getLongitude(),
                                    speciesDigSite.getId().getDigSite().getFirstExcavation())).toList()
                    , HttpStatus.OK);
        }
    }

    @GetMapping()
    public ResponseEntity<List<SpeciesDto>> getAllDinosaurs() {
        LOGGER.info("DinosaursController is running getAllDinosaurs");
        List<Species> speciesList = speciesService.getSpecies();
        if (speciesList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<SpeciesDto> speciesDtos = speciesList.stream().map(
                species -> new SpeciesDto(
                        species.getId(),
                        species.getName(),
                        species.getScientificName(),
                        species.getNumberSpecimensFound(),
                        species.getDiet(),
                        new PeriodDto(
                                species.getPeriod().getName(),
                                species.getPeriod().getStartMillionsYears(),
                                species.getPeriod().getEndMillionsYears())
                )).toList();
        return new ResponseEntity<>(speciesDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> deleteDinosaur(@PathVariable("id") int dinosaurId) {
        LOGGER.info("DinosaursController is running deleteDinosaur");
        speciesService.removeSpecies(dinosaurId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<Void> updateDinosaur(@PathVariable("id") int id, @RequestBody @Valid UpdateSpeciesDto updatedSpeciesDto) {
        LOGGER.info("DinosaursController is running updateDinosaur");
        speciesService.updateSpecies(id, updatedSpeciesDto.getScientificName(), updatedSpeciesDto.getNumberOfSpecimensFound(), updatedSpeciesDto.getDietName(), updatedSpeciesDto.getPeriodName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping
    public ResponseEntity<SpeciesDto> addDinosaur(
            @RequestBody @Valid NewSpeciesDto newSpeciesDto, @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("DinosaursController is running addDinosaur");
        User author = userService.getUserByName(user.getUsername());
        Species createdSpecies = new Species(
                newSpeciesDto.getName(),
                newSpeciesDto.getScientificName(),
                newSpeciesDto.getNumberOfSpecimensFound(),
                Diet.valueOf(newSpeciesDto.getDietName().toUpperCase()),
                newSpeciesDto.getImage());
        createdSpecies.setAuthor(author);
        speciesService.setPeriodOfSpecies(createdSpecies, newSpeciesDto.getPeriodName());
        speciesService.addSpecies(createdSpecies);
        speciesService.addDigSitesOfSpecies(createdSpecies, newSpeciesDto.getDigSiteIdsDatesFound());
        LOGGER.debug("DinosaurController added Species {} with author {}", createdSpecies.getName(), createdSpecies.getAuthor().getUsername());
        return new ResponseEntity<>(
                new SpeciesDto(
                        createdSpecies.getId(),
                        createdSpecies.getName(),
                        createdSpecies.getScientificName(),
                        createdSpecies.getNumberSpecimensFound(),
                        createdSpecies.getDiet(),
                        new PeriodDto(
                                createdSpecies.getPeriod().getName(),
                                createdSpecies.getPeriod().getStartMillionsYears(),
                                createdSpecies.getPeriod().getEndMillionsYears()
                        )
                ),
                HttpStatus.CREATED);
    }

    @PostMapping("/{id}/favorites")
    public ResponseEntity<SpeciesUserFavoriteDto> favoriteDinosaur(@PathVariable("id") int dinosaurId,
                                                                   @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("DinosaursController is running favoriteDinosaur");
        SpeciesUserFavorite speciesUserFavorite = new SpeciesUserFavorite(new SpeciesUserFavoriteId(), LocalDateTime.now());
        speciesUserFavoriteService.setSpeciesOfSpeciesUserFavorite(speciesUserFavorite, dinosaurId);
        speciesUserFavoriteService.setUserOfSpeciesUserFavorite(speciesUserFavorite, user.getUserId());
        speciesUserFavorite = speciesUserFavoriteService.addFavorite(speciesUserFavorite);
        return new ResponseEntity<>(
                new SpeciesUserFavoriteDto(
                        new UserDto(
                                speciesUserFavorite.getId().getUser().getId(),
                                speciesUserFavorite.getId().getUser().getUsername()
                        ),
                        new SpeciesDto(
                                speciesUserFavorite.getId().getSpecies().getId(),
                                speciesUserFavorite.getId().getSpecies().getName(),
                                speciesUserFavorite.getId().getSpecies().getScientificName(),
                                speciesUserFavorite.getId().getSpecies().getNumberSpecimensFound(),
                                speciesUserFavorite.getId().getSpecies().getDiet(),
                                new PeriodDto(
                                        speciesUserFavorite.getId().getSpecies().getPeriod().getName(),
                                        speciesUserFavorite.getId().getSpecies().getPeriod().getStartMillionsYears(),
                                        speciesUserFavorite.getId().getSpecies().getPeriod().getEndMillionsYears()
                                )
                        ),
                        speciesUserFavorite.getDateFavorited()
                ),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/favorites")
    public ResponseEntity<Integer> unFavoriteDinosaur(@PathVariable("id") int dinosaurId,
                                                      @AuthenticationPrincipal CustomUserDetails user) {
        speciesUserFavoriteService.removeFavorite(dinosaurId, user.getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
