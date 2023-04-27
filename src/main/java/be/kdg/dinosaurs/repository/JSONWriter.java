package be.kdg.dinosaurs.repository;

import be.kdg.dinosaurs.domain.DigSite;
import be.kdg.dinosaurs.domain.Species;
import be.kdg.dinosaurs.repository.type_adapters.DigSiteTypeAdapter;
import be.kdg.dinosaurs.repository.type_adapters.SpeciesTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Component
public class JSONWriter {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String DIGSITES_JSON = "digSites.json";
    private static final String SPECIES_JSON = "species.json";
    private Gson gson;

    public JSONWriter() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(Species.class, new SpeciesTypeAdapter());
        builder.registerTypeAdapter(DigSite.class, new DigSiteTypeAdapter());
        gson = builder.create();
    }

    public void writeDigSites(List<DigSite> digSites) {
        String json = gson.toJson(digSites);
        try (FileWriter writer = new FileWriter(DIGSITES_JSON)) {
            writer.write(json);
            LOGGER.info("JSONWriter saved digsite data to " + DIGSITES_JSON);
        } catch (IOException e) {
            LOGGER.error("JSONWriter could not save digsite data to " + DIGSITES_JSON);
            throw new RuntimeException("Unable to save digsites to JSON", e);
        }
    }

    public void writeSpecies(List<Species> species) {
        String json = gson.toJson(species);
        try (FileWriter writer = new FileWriter(SPECIES_JSON)) {
            writer.write(json);
            LOGGER.info("JSONWriter saved species data to " + SPECIES_JSON);
        } catch (IOException e) {
            LOGGER.error("JSONWriter could not save species data to " + SPECIES_JSON);
            throw new RuntimeException("Unable to save species to JSON", e);
        }
    }
}




