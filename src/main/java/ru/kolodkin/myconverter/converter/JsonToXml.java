package ru.kolodkin.myconverter.converter;

import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import lombok.val;
import ru.kolodkin.myconverter.model.Ram;
import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.model.RootXml;
import ru.kolodkin.myconverter.tool.Validate;
import ru.kolodkin.myconverter.tool.mapper.JSONReader;
import ru.kolodkin.myconverter.tool.mapper.XMLWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static java.util.stream.Collectors.toList;
import static java.util.Comparator.comparingInt;

public final class JsonToXml implements Converter<RootXml, RootJson> {
    public void convert(InputStream inputStream, OutputStream outputStream) throws IOException, JAXBException {
        XMLWriter.write(
                transform(JSONReader.read(inputStream)),
                outputStream
        );
    }

    public RootXml transform(@NonNull final RootJson rootJson) {
        val listRam = new ArrayList<Ram>();
        rootJson.getRams()
                .forEach(rams -> rams.getRam()
                        .forEach(ram -> listRam.add(Ram.builder()
                                .firm(rams.getFirm())
                                .specifications(ram.getSpecifications())
                                .idRam(ram.getIdRam())
                                .releaseYear(ram.getReleaseYear())
                                .title(ram.getTitle())
                                .build()))
                );

        return new RootXml(listRam.stream()
                .sorted((comparingInt(Ram::getIdRam)))
                .collect(toList()));
    }
}