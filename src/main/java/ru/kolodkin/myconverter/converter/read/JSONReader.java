package ru.kolodkin.myconverter.converter.read;

import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.tool.ObjectMapperInstance;

import java.io.IOException;
import java.io.InputStream;

public final class JSONReader implements IReader<RootJson> {
    @Override
    public RootJson read(InputStream inputStream) throws IOException {
        return ObjectMapperInstance.getInstance()
                .readValue(inputStream, RootJson.class);
    }
}