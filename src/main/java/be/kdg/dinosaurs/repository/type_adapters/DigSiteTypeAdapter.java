package be.kdg.dinosaurs.repository.type_adapters;

import be.kdg.dinosaurs.domain.DigSite;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DigSiteTypeAdapter extends TypeAdapter<DigSite> {
    @Override
    public void write(JsonWriter out, DigSite digSite) throws IOException {
        if (digSite == null){
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("id");
        out.value(digSite.getId());
        out.name("name");
        out.value(digSite.getName());
        out.name("latitude");
        out.value(digSite.getLatitude());
        out.name("longitude");
        out.value(digSite.getLongitude());
        out.name("firstExcavation");
        new LocalDateTypeAdapter().write(out, digSite.getFirstExcavation());
        out.endObject();
    }

    @Override
    public DigSite read(JsonReader in) throws IOException {
        return null;
    }
}
