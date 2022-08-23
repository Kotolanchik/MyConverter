package ru.kolodkin.myconverter.tool.mapper;


import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.tool.ObjectMapperInstance;

import java.io.IOException;
import java.io.InputStream;

public final class JSONReader  {
    public static RootJson read(final InputStream inputStream) throws IOException {
        return ObjectMapperInstance.getObjectMapper()
                .readValue(inputStream, RootJson.class);
    }
}