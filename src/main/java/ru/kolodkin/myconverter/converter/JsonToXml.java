package ru.kolodkin.myconverter.converter;

import lombok.val;
import ru.kolodkin.myconverter.model.Ram;
import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.model.RootXml;
import ru.kolodkin.myconverter.tool.ObjectMapperInstance;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class JsonToXml implements Converter {
    public void convert(FileInputStream inputStream, FileOutputStream outputStream) {
        writeXml(getXmlModel(readJson(inputStream)), outputStream);
    }

    private RootXml getXmlModel(RootJson rootJson) {
        val listRam = new ArrayList<Ram>();

        for (int rootIndex = 0; rootIndex < rootJson.getRams().size(); rootIndex++) {
            for (int ramsIndex = 0; ramsIndex < rootJson.getRams().get(rootIndex).getRam().size(); ramsIndex++) {
                listRam.add(Ram.builder()
                        .firm(rootJson.getRams().get(rootIndex).getFirm())
                        .specifications(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getSpecifications())
                        .idRam(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getIdRam())
                        .releaseYear(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getReleaseYear())
                        .title(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getTitle())
                        .build());
            }
        }

        return new RootXml(listRam.stream()
                .sorted((Comparator.comparingInt(Ram::getIdRam)))
                .collect(Collectors.toList()));
    }

    private RootJson readJson(FileInputStream inputStream) {
        try {
            return ObjectMapperInstance.getInstance()
                    .readValue(inputStream, RootJson.class);
        } catch (IOException exception) {
            throw new RuntimeException("Ошибка при чтении json файла... \n" + Arrays.toString(exception.getStackTrace()));
        }
    }

    private void writeXml(RootXml root, FileOutputStream outputStream) {
        try {
            val jaxbMarshaller = JAXBContext.newInstance(RootXml.class).createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(root, outputStream);
        } catch (JAXBException exception) {
            throw new RuntimeException("Ошибка при преобразование объектов в документ xml... \n"
                    + Arrays.toString(exception.getStackTrace()));
        }
    }
}
