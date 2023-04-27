package be.kdg.dinosaurs.repository.type_adapters;

import be.kdg.dinosaurs.domain.Period;
import be.kdg.dinosaurs.domain.Species;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class SpeciesTypeAdapter extends TypeAdapter<Species> {

    @Override
    public void write(JsonWriter out, Species species) throws IOException {
        if (species == null){
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("id");
        out.value(species.getId());
        out.name("scientificName");
        out.value(species.getScientificName());
        out.name("numberSpecimensFound");
        out.value(species.getNumberSpecimensFound());
        out.name("period");
//        GsonBuilder gsonBuilder = new GsonBuilder()
//                .registerTypeAdapter(Period.class, new Gson().getAdapter(Period.class));
//        gsonBuilder.disableHtmlEscaping();
//        Gson gson = gsonBuilder.create();
//        System.out.println(gson.toJson(species.getPeriod()));
//        out.value(gson.toJson(species.getPeriod()));
        writePeriod(out, species.getPeriod());
        out.name("image");
        out.value(species.getImage());
        out.endObject();
    }

    private void writePeriod(JsonWriter out, Period period) throws IOException{
        out.beginObject();
        out.name("id").value(period.getId());
        out.name("name").value(period.getName());
        out.name("startMillionsYears").value(period.getStartMillionsYears());
        out.name("endMillionsYears").value(period.getEndMillionsYears());
        out.endObject();
    }
    @Override
    public Species read(JsonReader in) throws IOException {
        return null;
    }
}
