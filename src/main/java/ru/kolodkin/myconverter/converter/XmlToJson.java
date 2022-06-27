package ru.kolodkin.myconverter.converter;

import ru.kolodkin.myconverter.model.Ram;
import ru.kolodkin.myconverter.model.Rams;
import ru.kolodkin.myconverter.model.RootJson;
import ru.kolodkin.myconverter.model.RootXml;
import ru.kolodkin.myconverter.tool.ObjectMapperInstance;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XmlToJson implements Converter {
    public void convert(InputStream input, OutputStream output) throws IOException, JAXBException {
        RootXml rootXml = readXml(input);

        writeJson(getJsonModel(getNameFirms(rootXml), rootXml.getRamList()), output);
    }

    private ArrayList<Rams> getJsonModel(List<String> nameAllFirm, List<Ram> ramListFromXml) {
        ArrayList<Rams> ramListForJson = new ArrayList<>();
        for (String firm : nameAllFirm) {
            ramListForJson.add(new Rams(firm));
        }

        for (Rams rams : ramListForJson) {
            for (Ram ram : ramListFromXml) {
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

    private void writeJson(List<Rams> ramListForJson, OutputStream outputStream) throws IOException {
        ObjectMapperInstance.getInstance()
                .writerWithDefaultPrettyPrinter()
                .writeValue(outputStream, new RootJson(ramListForJson));
    }

    private RootXml readXml(InputStream inputStream) throws JAXBException {
        return (RootXml) JAXBContext.newInstance(RootXml.class)
                .createUnmarshaller()
                .unmarshal(inputStream);
    }

    /**
     * @param rootXml Xml модель.
     * @return Возвращает уникальные названия всех фирм из модели.
     */
    private List<String> getNameFirms(RootXml rootXml) {
        if (rootXml == null) {
            throw new NullPointerException("XML файл пустой.");
        }

        return rootXml.getRamList().stream().collect(
                        () -> new ArrayList<String>(),
                        (list, item) -> list.add(item.getFirm()),
                        ArrayList::addAll)
                .stream().distinct()
                .collect(Collectors.toList());
    }
}
