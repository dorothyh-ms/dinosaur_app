package be.kdg.dinosaurs.controllers.mvc;

import be.kdg.dinosaurs.domain.DigSite;
import be.kdg.dinosaurs.domain.Discovery;
import be.kdg.dinosaurs.exceptions.DigSiteNotAvailableException;
import be.kdg.dinosaurs.controllers.mvc.viewmodel.DigSiteViewModel;
import be.kdg.dinosaurs.service.digsites.DigSiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static be.kdg.dinosaurs.controllers.mvc.ViewingHistoryRecorder.addHistory;

@Controller
@RequestMapping("/digsites")
public class  DigSiteController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private DigSiteService digSiteService;


    public DigSiteController(DigSiteService digSiteService) {
        this.digSiteService = digSiteService;
    }

    @RequestMapping
    public String showDigSites(HttpSession httpSession, Model model){
        LOGGER.info("DigSiteController is running showDigSites");
        addHistory(httpSession, "/digsites");
        model.addAttribute("countries", digSiteService.getCountries());
        model.addAttribute("digsites", digSiteService.getDigSites());
        return "digsites";
    }

    @RequestMapping( params = "country")
    public String showDigSitesByCountry(@RequestParam("country") String country, HttpSession httpSession, Model model){
        LOGGER.info("DigSiteController is running showDigSitesByCountry");
        addHistory(httpSession, String.format("/digsites?country=%s", country));
        model.addAttribute("countries", digSiteService.getCountries());
        model.addAttribute("digsites", digSiteService.getDigSites(country));
        return "digsites";
    }

    @RequestMapping( params = {"excavatedBefore", "excavatedAfter"})
    public String showDigSitesByDates(@RequestParam("excavatedBefore") Optional<LocalDate> excavatedBefore, @RequestParam("excavatedAfter") Optional<LocalDate> excavatedAfter, HttpSession httpSession, Model model){
        LOGGER.info("DigSiteController is running showDigSitesByDates");
        model.addAttribute("countries", digSiteService.getCountries());
        if (excavatedBefore.isPresent() && excavatedAfter.isPresent()){
            addHistory(httpSession, String.format("/digsites?excavatedAfter=%s&excavatedBefore=%s", excavatedAfter.get().toString(), excavatedBefore.get().toString()));
            model.addAttribute("digsites", digSiteService.getDigSitesAfterDateAndBeforeDate(excavatedAfter.get(), excavatedBefore.get()));
            return "digsites";
        } if (excavatedBefore.isEmpty() && excavatedAfter.isPresent()){
            addHistory(httpSession, String.format("/digsites?excavatedAfter=%s&excavatedBefore=", excavatedAfter.get().toString()));
            model.addAttribute("digsites", digSiteService.getDigSitesAfterDate(excavatedAfter.get()));
            return "digsites";
        } if (excavatedBefore.isPresent()){
            addHistory(httpSession, String.format("/digsites?excavatedAfter=&excavatedBefore=%s", excavatedBefore.get().toString()));
            model.addAttribute("digsites", digSiteService.getDigSitesBeforeDate(excavatedBefore.get()));
            return "digsites";
        }
        model.addAttribute("digsites", digSiteService.getDigSites());
        return "digsites";
    }

    @GetMapping("/add")
    public String showAddDigSite(HttpSession httpSession, Model model){
        LOGGER.info("DigSiteController is running showAddDigSite");
        addHistory(httpSession, "/digsites/add");
        model.addAttribute("digsite", new DigSiteViewModel());
        return "adddigsite";
    }

    @PostMapping("/add")
    public String processAddDigSite(@Valid @ModelAttribute("digsite") DigSiteViewModel digSiteViewModel, BindingResult errors){
        LOGGER.info("DigSiteController is running processAddDigSite");
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> {LOGGER.error(error.toString());
            });
            return "adddigsite";
        }
        LOGGER.info("Processing " + digSiteViewModel);
        DigSite digSite = new DigSite(digSiteViewModel.getName(), digSiteViewModel.getCountry(), digSiteViewModel.getLatitude(), digSiteViewModel.getLongitude(), digSiteViewModel.getFirstExcavation());
        digSiteService.addDigSite(digSite);
        return "redirect:/digsites";
    }

    @GetMapping("/digsite")
    public String showDigSite(@RequestParam("digsiteId")int digSiteId, HttpSession httpSession, Model model){
        LOGGER.info("DigSiteController is running showDigSite");
        addHistory(httpSession, String.format("/digsites/digsite?digsiteId=%d", digSiteId));
        DigSite digSite = digSiteService.getDigSiteById(digSiteId);
        List<Discovery> discoveries = digSiteService.getSpeciesOfDigSite(digSiteId);
        model.addAttribute("digSite", digSite);
        model.addAttribute("discoveries", discoveries);
        return "digsite";
    }

    @GetMapping("/delete")
    public String showDeleteDigSite(@RequestParam("digsiteId") int digSiteId) {
        LOGGER.info("DigSiteController is running showDeleteDigSite");
        digSiteService.removeDigSite(digSiteId);
        return "redirect:/digsites";
    }

    @GetMapping("/save")
    public String showSaveDigSites(){
        LOGGER.info("DigSiteController is running showSaveDigSites");
        digSiteService.writeDigSitesToJSON(digSiteService.getDigSites());
        return "redirect:/digsites";
    }


    @ExceptionHandler(DigSiteNotAvailableException.class)
    public String handleDigSiteNotAvailableException(HttpServletRequest request, Exception e, Model model){
        LOGGER.info("DigSiteController is running handleDigSiteNotAvailableException");
        LOGGER.error(e.getMessage());
        model.addAttribute("exception", e);
        model.addAttribute("error", "DigSiteNotAvailableException");
        model.addAttribute("url", request.getRequestURL());
        return "error";
    }

}
