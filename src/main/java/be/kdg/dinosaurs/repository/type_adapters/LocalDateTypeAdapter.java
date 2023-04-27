package be.kdg.dinosaurs.repository.type_adapters;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {
    @Override
    public void write(JsonWriter out, LocalDate localDate) throws IOException {
        out.value(localDate.toString());
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        return null;
    }
}
