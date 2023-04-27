package be.kdg.dinosaurs.controllers.mvc;

import be.kdg.dinosaurs.controllers.mvc.viewmodel.EditSpeciesViewModel;
import be.kdg.dinosaurs.controllers.mvc.viewmodel.SpeciesUserFavoriteViewModel;
import be.kdg.dinosaurs.controllers.mvc.viewmodel.SpeciesViewModel;
import be.kdg.dinosaurs.domain.Diet;
import be.kdg.dinosaurs.domain.Discovery;
import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.domain.favorites.SpeciesUserFavorite;
import be.kdg.dinosaurs.exceptions.SpeciesNotAvailableException;
import be.kdg.dinosaurs.security.CustomUserDetails;
import be.kdg.dinosaurs.service.digsites.DigSiteService;
import be.kdg.dinosaurs.service.species.SpeciesService;
import be.kdg.dinosaurs.service.species_user_favorite_service.SpeciesUserFavoriteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static be.kdg.dinosaurs.controllers.mvc.ViewingHistoryRecorder.addHistory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/dinosaurs")
public class DinosaurController {
    private SpeciesService speciesService;
    private DigSiteService digSiteService;
    private final ModelMapper modelMapper;

    private final SpeciesUserFavoriteService speciesUserFavoriteService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public DinosaurController(SpeciesService speciesService, DigSiteService digSiteService, ModelMapper modelMapper, SpeciesUserFavoriteService speciesUserFavoriteService) {
        this.speciesService = speciesService;
        this.digSiteService = digSiteService;
        this.modelMapper = modelMapper;
        this.speciesUserFavoriteService = speciesUserFavoriteService;
    }

    @GetMapping
    public String showDinosaurs(HttpSession httpSession, Model model) {
        LOGGER.info("DinosaurController is running showDinosaurs");
        addHistory(httpSession, "/dinosaurs");
        List<Species> speciesList = speciesService.getSpecies();
        speciesList.stream().forEach(s -> {
            s.setDiscoveries(speciesService.getDiscoveriesOfSpecies(s.getId()));
            s.setSpeciesUserFavorites(speciesUserFavoriteService.getFavoritesOfSpecies(s.getId()));
        });
        var speciesViewModels = speciesList
                .stream()
                .map(species -> modelMapper.map(species, SpeciesViewModel.class))
                .toList();
        model.addAttribute("dinosaurs", speciesViewModels);
        model.addAttribute("periods", speciesService.getPeriods());
        model.addAttribute("diets", Arrays.asList(Diet.values()));
        return "dinosaurs";
    }

    @RequestMapping(params = "period")
    public String showDinosaursByPeriod(@RequestParam("period") String periodName, HttpSession httpSession, Model model) {
        LOGGER.info("DinosaurController is running showDinosaursByPeriod");
        addHistory(httpSession, String.format("/dinosaurs?period=%s", periodName));
        var speciesViewModels = speciesService.getSpeciesByPeriod(periodName).stream()
                .map(species -> modelMapper.map(species, SpeciesViewModel.class))
                .toList();
        model.addAttribute("dinosaurs", speciesViewModels);
        model.addAttribute("periods", speciesService.getPeriods());
        model.addAttribute("diets", Arrays.asList(Diet.values()));
        return "dinosaurs";
    }

    @RequestMapping(params = "diet")
    public String showDinosaursByDiet(@RequestParam("diet") String dietName, HttpSession httpSession, Model model) {
        LOGGER.info("DinosaurController is running showDinosaursByDiet");
        addHistory(httpSession, String.format("/dinosaurs?diet=%s", dietName));
        var speciesViewModels = speciesService.getSpeciesByDiet(dietName).stream()
                .map(species -> modelMapper.map(species, SpeciesViewModel.class))
                .toList();
        model.addAttribute("dinosaurs", speciesViewModels);
        model.addAttribute("periods", speciesService.getPeriods());
        model.addAttribute("diets", Arrays.asList(Diet.values()));
        return "dinosaurs";
    }

    @GetMapping("/add")
    public String showAddDinosaur(HttpSession httpSession, Model model) {
        LOGGER.info("DinosaurController is running showAddDinosaur");
        addHistory(httpSession, "/dinosaurs/add");
        model.addAttribute("periods", speciesService.getPeriods());
        model.addAttribute("digsites", digSiteService.getDigSites());
        return "adddinosaur";
    }

    @GetMapping("/delete")
    public String showDeleteDinosaur(@RequestParam("dinosaurId") int dinosaurId) {
        LOGGER.info("DinosaurController is running showDeleteDinosaur");
        speciesService.removeSpecies(dinosaurId);
        return "redirect:/dinosaurs";
    }

    @GetMapping("/save")
    public String showSaveDinosaurs() {
        LOGGER.info("DinosaurController is running showSaveDinosaurs");
        speciesService.writeSpeciesToJSON(speciesService.getSpecies());
        return "redirect:/dinosaurs";
    }


    @GetMapping("/dinosaur")
    public String showDinosaur(HttpSession httpSession, @RequestParam("dinosaurId") int dinosaurId,
                               Model model,
                               @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("DinosaurController is running showDinosaur");
        addHistory(httpSession, String.format("/dinosaurs/dinosaur?dinosaurId=%d", dinosaurId));
        Species species = speciesService.getSpeciesById(dinosaurId);
        species.setDiscoveries(speciesService.getDiscoveriesOfSpecies(dinosaurId));
        species.setSpeciesUserFavorites(speciesUserFavoriteService.getFavoritesOfSpecies(species.getId()));
        List<Discovery> discoveries = speciesService.getDiscoveriesOfSpecies(dinosaurId);
        model.addAttribute("dinosaur", modelMapper.map(species, SpeciesViewModel.class));
        if (user != null) {
            model.addAttribute("userFavorited", speciesUserFavoriteService.userFavoritedSpecies(dinosaurId, user.getUserId()));
        }
        model.addAttribute("discoveries", discoveries);
        return "dinosaur";
    }



    @ExceptionHandler(SpeciesNotAvailableException.class)
    public String handleSpeciesNotAvailableException(HttpServletRequest request, Exception e, Model model) {
        LOGGER.info("DinosaurController is running handleSpeciesNotAvailableException");
        LOGGER.error(e.getMessage());
        model.addAttribute("exception", e);
        model.addAttribute("error", "SpeciesNotAvailableException");
        model.addAttribute("url", request.getRequestURL());
        return "error";
    }

    @GetMapping("/favorites")
    public String showFavorites(HttpSession httpSession, @AuthenticationPrincipal CustomUserDetails user, Model model){
        LOGGER.info("DinosaurController is running showFavorites");
        addHistory(httpSession, "/favorites");
        List<SpeciesUserFavorite> favorites = speciesUserFavoriteService.getFavoritesOfUser(user.getUserId());
        var speciesUserFavoriteViewModels = favorites
                .stream()
                .map(species -> modelMapper.map(species, SpeciesUserFavoriteViewModel.class))
                .toList();
        model.addAttribute("favorites", speciesUserFavoriteViewModels);
        return "favorites";
    }

}
