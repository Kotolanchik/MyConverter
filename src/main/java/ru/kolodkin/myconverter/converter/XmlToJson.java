package ru.kolodkin.myconverter.converter;

import lombok.val;
import ru.kolodkin.myconverter.model.Ram;
import ru.kolodkin.myconverter.model.Rams;
import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.model.RootXml;
import ru.kolodkin.myconverter.tool.ObjectMapperInstance;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class XmlToJson implements Converter {
    public void convert(FileInputStream input, FileOutputStream output) {
        val rootXml = readXml(input);

        writeJson(getJsonModel(getNameFirms(rootXml), rootXml.getRamList()), output);
    }

    private ArrayList<Rams> getJsonModel(Set<String> nameAllFirm, List<Ram> ramListFromXml) {
        val ramListForJson = new ArrayList<Rams>();
        for (var firm : nameAllFirm) {
            ramListForJson.add(new Rams(firm));
        }

        for (var rams : ramListForJson) {
            for (var ram : ramListFromXml) {
                if (ram.getFirm().equals(rams.getFirm())) {
                    rams.getRam().add(Ram.builder()
                            .idRam(ram.getIdRam())
                            .title(ram.getTitle())
                            .releaseYear(ram.getReleaseYear())
                            .specifications(ram.getSpecifications())
                            .build());
                }
            }
        }
        return ramListForJson;
    }

    private void writeJson(List<Rams> ramListForJson, FileOutputStream outputStream) {
        try {
            ObjectMapperInstance.getInstance()
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(outputStream, new RootJson(ramListForJson));
        } catch (IOException exception) {
            throw new RuntimeException("Ошибка при вводе/выводе... \n" + Arrays.toString(exception.getStackTrace()));
        }
    }

    private RootXml readXml(FileInputStream inputStream) {
        try {
            return (RootXml) JAXBContext.newInstance(RootXml.class)
                    .createUnmarshaller()
                    .unmarshal(inputStream);
        } catch (JAXBException exception) {
            throw new RuntimeException("Ошибка при преобразование xml файла в объект... \n"
                    + Arrays.toString(exception.getStackTrace()));
        }
    }

    /**
     * @param rootXml Xml модель.
     * @return Возвращает уникальные названия всех фирм из модели.
     */
    private Set<String> getNameFirms(RootXml rootXml) {
        if (rootXml == null) {
            throw new NullPointerException("XML файл пустой.");
        }
        return rootXml.getRamList()
                .stream()
                .map(Ram::getFirm)
                .collect(Collectors.toSet());
    }
}
