package ru.kolodkin.myconverter.converter.write;

import ru.kolodkin.myconverter.model.Rams;
import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.tool.ObjectMapperInstance;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public final class JSONWriter implements IWriter<List<Rams>> {
    @Override
    public void write(List<Rams> model, OutputStream outputStream) throws IOException {
        ObjectMapperInstance.getInstance()
                .writerWithDefaultPrettyPrinter()
                .writeValue(outputStream, new RootJson(model));
    }
}
