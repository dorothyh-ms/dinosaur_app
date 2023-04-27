//package be.kdg.dinosaurs.repository;
//
//import be.kdg.dinosaurs.domain.Diet;
//import be.kdg.dinosaurs.domain.DigSite;
//import be.kdg.dinosaurs.domain.Period;
//import be.kdg.dinosaurs.domain.Species;
//import be.kdg.dinosaurs.repository.digsites.DigSiteRepository;
//import be.kdg.dinosaurs.repository.periods.PeriodRepository;
//import be.kdg.dinosaurs.repository.species.SpeciesRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Profile("listrepo")
//public class DatabaseSeeder implements CommandLineRunner {
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//    private DigSiteRepository digSiteRepository;
//    private SpeciesRepository speciesRepository;
//    private PeriodRepository periodRepository;
//
//
//
//    public DatabaseSeeder(DigSiteRepository digSiteRepository, SpeciesRepository speciesRepository, PeriodRepository periodRepository) {
//        this.digSiteRepository = digSiteRepository;
//        this.speciesRepository = speciesRepository;
//        this.periodRepository = periodRepository;
//    }
//
//
//    public void seed(){
//        LOGGER.info("DatabaseSeeder is loading data into digSiteRepository and speciesRepository");
//        Period cretaceous = new Period("Cretaceous", 145.0, 100.5);
//        Period jurassic = new Period("Jurassic", 201.3, 145.0);
//        Period triassic = new Period ("Triassic", 251.902, 201.36);
//        List<Period> periods = new ArrayList<>();
//        periods.add(cretaceous);
//        periods.add(jurassic);
//        periods.add(triassic);
//
//
//        Species iguanodon = new Species("Iguanodon", "Iguanodon bernissartensis", 38, Diet.HERBIVORE, "https://a-z-animals.com/media/2022/07/Iguanodon-header.jpg");
//        iguanodon.setPeriod(cretaceous);
//        Species archeopteryx = new Species("Archeopteryx", "Archaeopteryx lithographica", 12, Diet.CARNIVORE, "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/Archaeopteryx_lithographica_%28Berlin_specimen%29.jpg/1200px-Archaeopteryx_lithographica_%28Berlin_specimen%29.jpg");
//        archeopteryx.setPeriod(jurassic);
//        Species tRex = new Species("T. rex", "Tyrannosaurus rex", 32, Diet.CARNIVORE,  "https://media-cldnry.s-nbcnews.com/image/upload/rockcms/2021-04/210415-tyrannosaurus-rex-mn-1550-9612a9.jpg");
//        tRex.setPeriod(cretaceous);
//        Species triceratops = new Species("Triceratops","Triceratops horridus", 166, Diet.HERBIVORE,  "https://static01.nyt.com/images/2021/01/08/science/08TB-DINOSEED1/merlin_182090331_43cb9149-c54d-4c56-aa38-5d6345713165-articleLarge.jpg?quality=75&auto=webp&disable=upscale");
//        triceratops.setPeriod(cretaceous);
//        Species ankylosaurus = new Species("Ankylosaurus", "Ankylosaurus magniventris", 3, Diet.HERBIVORE, "https://media.kidadl.com/Did_You_Know_21_Incredible_Ankylosaurus_Facts_8dacbb4e54.jpg");
//        ankylosaurus.setPeriod(cretaceous);
//        Species titanosaur  = new Species ("Sauropod", "Patagotitan mayorum", 150, Diet.HERBIVORE, "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Patagotitan-Scale-Diagram-Steveoc86.svg/400px-Patagotitan-Scale-Diagram-Steveoc86.svg.png");
//        titanosaur.setPeriod(cretaceous);
//        Species pleisiosaur = new Species ("Plesiosaur", "Plesiosaurus dolichodeirus", 114, Diet.CARNIVORE, "https://www.newdinosaurs.com/wp-content/uploads/2016/11/1362_plesiosaurus_esther_van_hulsen.jpg");
//        pleisiosaur.setPeriod(triassic);
//        Species stegosaur = new Species ("Stegosaur", "Stegosaurus stenops",  80, Diet.HERBIVORE, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Stegosaurus_stenops_Life_Reconstruction.png/1920px-Stegosaurus_stenops_Life_Reconstruction.png");
//        stegosaur.setPeriod(jurassic);
//        List<Species> species = new ArrayList<>();
//        species.add(iguanodon);
//        species.add(archeopteryx);
//        species.add(tRex);
//        species.add(triceratops);
//        species.add(ankylosaurus);
//        species.add(titanosaur);
//        species.add(pleisiosaur);
//        species.add(stegosaur);
//
//        DigSite willowCreek = new DigSite("Willow Creek Formation", "Canada", 49.8, -113.4, LocalDate.of(1861, 5, 1));
//        DigSite hellCreek = new DigSite("Hell Creek Formation", "USA", 46.9, -101.5, LocalDate.of(1861, 9, 30));
//        DigSite bernissartMine = new DigSite("Bernissart Mine", "Belgium", 50.5, 3.6, LocalDate.of(1902, 1, 30));
//        DigSite blumenbergQuarry = new DigSite("Blumenberg Quarry", "Germany", 49.0, 11.2, LocalDate.of(1889, 4, 26));
//        DigSite cerroBarcino = new DigSite("Cerro Barcino Formation", "Argentina", -43.8, -68.6, LocalDate.of(1902, 6, 14));
//        DigSite laSagnette = new DigSite("La Sagnette", "Belgium", 49.7, 5.7, LocalDate.of(2008, 9, 23));
//        DigSite morrisonFormation = new DigSite("Morrison Formation", "USA", 39.7, -105.2, LocalDate.of(1877, 4, 10));
//        DigSite casalNovo = new DigSite("Casal Novo", "Portugal", 39.7, 8.8, LocalDate.of(1999, 10, 3));
//        List<DigSite> digSites = new ArrayList<>();
//        digSites.add(willowCreek);
//        digSites.add(hellCreek);
//        digSites.add(bernissartMine);
//        digSites.add(blumenbergQuarry);
//        digSites.add(cerroBarcino);
//        digSites.add(laSagnette);
//        digSites.add(morrisonFormation);
//        digSites.add(casalNovo);
//
//        triceratops.addDigSite(hellCreek);
//        tRex.addDigSite(hellCreek);
//        tRex.addDigSite(willowCreek);
//        iguanodon.addDigSite(bernissartMine);
//        ankylosaurus.addDigSite(hellCreek);
//        archeopteryx.addDigSite(blumenbergQuarry);
//        titanosaur.addDigSite(cerroBarcino);
//        pleisiosaur.addDigSite(laSagnette);
//        stegosaur.addDigSite(morrisonFormation);
//        stegosaur.addDigSite(casalNovo);
//
//        laSagnette.addSpeciesFound(pleisiosaur);
//        cerroBarcino.addSpeciesFound(titanosaur);
//        blumenbergQuarry.addSpeciesFound(archeopteryx);
//        hellCreek.addSpeciesFound(ankylosaurus);
//        bernissartMine.addSpeciesFound(iguanodon);
//        willowCreek.addSpeciesFound(tRex);
//        hellCreek.addSpeciesFound(tRex);
//        hellCreek.addSpeciesFound(triceratops);
//        morrisonFormation.addSpeciesFound(stegosaur);
//        morrisonFormation.addSpeciesFound(ankylosaurus);
//        casalNovo.addSpeciesFound(stegosaur);
//
//
//        periods.forEach(periodRepository::createPeriod);
//        digSites.forEach(digSiteRepository::createDigSite);
//        species.forEach(speciesRepository::createSpecies);
//        LOGGER.debug("Seeded digSiteRepository, speciesRepository, and periodRepository");
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        seed();
//    }
//}
