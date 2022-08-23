package ru.kolodkin.myconverter.tool.mapper;

import ru.kolodkin.myconverter.model.Rams;
import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.tool.ObjectMapperInstance;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public final class JSONWriter {
    public static void write(final List<Rams> model, final OutputStream outputStream) throws IOException {
        ObjectMapperInstance.getObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(outputStream, new RootJson(model));
    }
}
