package ru.kolodkin.myconverter.converters;

import org.codehaus.jackson.map.ObjectMapper;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.Rams;
import ru.kolodkin.myconverter.models.RootJson;
import ru.kolodkin.myconverter.models.RootXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XmlToJson implements Converter {
    public void convert(InputStream input, OutputStream output) throws IOException, JAXBException {
        RootXml rootXml = readXml(input);

        writeJson(getJsonModel(getNameFirms(rootXml),
                rootXml.getRamList()), output);
    }

    /**
     * Преобразует Xml модель в JSON.
     *
     * @param nameAllFirm    Название всех фирм.
     * @param ramListFromXml Модель из XML файла.
     * @return Возвращает JSON модель.
     */
    private ArrayList<Rams> getJsonModel(List<String> nameAllFirm, List<Ram> ramListFromXml) {
        ArrayList<Rams> ramListForJson = new ArrayList<>();
        for (String firm : nameAllFirm) {
            Rams buffRams = new Rams();
            buffRams.setFirm(firm);
            ramListForJson.add(buffRams);
        }

        for (Rams rams : ramListForJson) {
            for (Ram ram : ramListFromXml) {
                if (ram.getFirm().equals(rams.getFirm())) {
                    rams.getRam().add(
                            new Ram(ram.getIdRam(),
                                    ram.getTitle(),
                                    ram.getReleaseYear(),
                                    ram.getSpecifications()));
                }
            }
        }
        return ramListForJson;
    }

    private void writeJson(List<Rams> ramListForJson, OutputStream outputStream) throws IOException {
        new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValue(outputStream, new RootJson(ramListForJson));
    }

    private RootXml readXml(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RootXml.class);
        jaxbContext.createMarshaller().setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return (RootXml) jaxbContext.createUnmarshaller().unmarshal(inputStream);
    }

    /**
     * @param rootXml Xml модель.
     * @return Возвращает уникальные названия всех фирм из модели.
     */
    private ArrayList<String> getNameFirms(RootXml rootXml) {
        if (rootXml == null) {
            throw new NullPointerException("XML файл пустой.");
        }

        return (ArrayList<String>) rootXml.getRamList().stream().collect(
                        () -> new ArrayList<String>(),
                        (list, item) -> list.add(item.getFirm()),
                        ArrayList::addAll)
                .stream().distinct()
                .collect(Collectors.toList());
    }
}
