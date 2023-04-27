package be.kdg.dinosaurs.controllers.api;


import be.kdg.dinosaurs.controllers.api.dtos.DigSiteDto;
import be.kdg.dinosaurs.controllers.api.dtos.UpdateDigSiteDto;
import be.kdg.dinosaurs.controllers.api.dtos.UpdateSpeciesDto;
import be.kdg.dinosaurs.domain.DigSite;
import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.service.digsites.DigSiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/digsites")
public class DigSitesController {

    private final DigSiteService digSiteService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public DigSitesController(DigSiteService digSiteService) {
        this.digSiteService = digSiteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigSiteDto> getDigSite(@PathVariable("id") int digSiteId){
        LOGGER.info("DigSitesController is running getDigSite");
        var digSite = digSiteService.getDigSiteById(digSiteId);
        if (digSite == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(
                    new DigSiteDto(
                            digSite.getId(),
                            digSite.getName(),
                            digSite.getCountry(),
                            digSite.getLatitude(),
                            digSite.getLongitude(),
                            digSite.getFirstExcavation()),
                            HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<DigSiteDto>> getAllDigSites(){
        LOGGER.info("DigSitesController is running getAllDigSites");
        List<DigSite> digSites = digSiteService.getDigSites();
        List<DigSiteDto> digSiteDTOs = digSites.stream().map(
                digSite -> new DigSiteDto(
                        digSite.getId(),
                        digSite.getName(),
                        digSite.getCountry(),
                        digSite.getLatitude(),
                        digSite.getLongitude(),
                        digSite.getFirstExcavation())
        ).toList();
        return new ResponseEntity<>(digSiteDTOs, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateDigSite(@PathVariable("id") int id, @RequestBody @Valid UpdateDigSiteDto updatedDigSiteDto) {
        LOGGER.info("DigSitesController is running updateDigSite");
        if (digSiteService.updateDigSite(id, updatedDigSiteDto.getCountry(), updatedDigSiteDto.getLatitude(), updatedDigSiteDto.getLongitude(), updatedDigSiteDto.getFirstExcavation())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteDigSite(@PathVariable("id") int digSiteId){
        LOGGER.info("DigSitesController is running deleteDigSite");
        if (digSiteService.digSiteExists(digSiteId)){
            digSiteService.removeDigSite(digSiteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
