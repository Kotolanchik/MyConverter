package ru.kolodkin.myconverter.converter;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import ru.kolodkin.myconverter.converter.read.JSONReader;
import ru.kolodkin.myconverter.converter.write.XMLWriter;
import ru.kolodkin.myconverter.model.Ram;
import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.model.RootXml;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Log4j2
public final class JsonToXml implements Converter {
    public void convert(InputStream inputStream, OutputStream outputStream) {
        XMLWriter.getInstance().write(
                transformJsonToXml(JSONReader.getInstance().read(inputStream)),
                outputStream
        );
    }

    private RootXml transformJsonToXml(RootJson rootJson) {
        if(rootJson == null) {
            log.error("Пустой json файл.");
            return null;
        }

        val listRam = new ArrayList<Ram>();
        for (int rootIndex = 0; rootIndex < rootJson.getRams().size(); rootIndex++) {
            for (int ramsIndex = 0; ramsIndex < rootJson.getRams().get(rootIndex).getRam().size(); ramsIndex++) {
                listRam.add(Ram.builder()
                        .firm(rootJson.getRams().get(rootIndex).getFirm())
                        .specifications(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getSpecifications())
                        .idRam(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getIdRam())
                        .releaseYear(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getReleaseYear())
                        .title(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getTitle())
                        .build()
                );
            }
        }

        return new RootXml(listRam.stream()
                .sorted((Comparator.comparingInt(Ram::getIdRam)))
                .collect(Collectors.toList()));
    }
}
