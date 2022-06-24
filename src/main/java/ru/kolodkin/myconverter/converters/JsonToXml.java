package ru.kolodkin.myconverter.converters;

import org.codehaus.jackson.map.ObjectMapper;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.RootJson;
import ru.kolodkin.myconverter.models.RootXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JsonToXml implements Converter {
    public void convert(InputStream inputStream, OutputStream outputStream) throws IOException, JAXBException {
        writeXml(getXmlModel(readJson(inputStream)), outputStream);
    }

    /**
     * Преобразует json модель в xml.
     *
     * @param rootJson json моодель.
     * @return Возвращает Xml модель.
     */
    private RootXml getXmlModel(RootJson rootJson) {
        List<Ram> listRam = new ArrayList<>();

        for (int rootIndex = 0; rootIndex < rootJson.getRams().size(); rootIndex++) {
            for (int ramsIndex = 0; ramsIndex < rootJson.getRams().get(rootIndex).getRam().size(); ramsIndex++) {
                Ram ram = new Ram();

                ram.setFirm(rootJson.getRams().get(rootIndex).getFirm());
                ram.setSpecifications(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getSpecifications());
                ram.setIdRam(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getIdRam());
                ram.setReleaseYear(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getReleaseYear());
                ram.setTitle(rootJson.getRams().get(rootIndex).getRam().get(ramsIndex).getTitle());

                listRam.add(ram);
            }
        }
        return new RootXml(listRam.stream().sorted((Comparator.comparingInt(Ram::getIdRam))).collect(Collectors.toList()));
    }

    private RootJson readJson(InputStream inputStream) throws IOException {
        return new ObjectMapper().readValue(inputStream, RootJson.class);
    }

    private void writeXml(RootXml root, OutputStream outputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RootXml.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(root, outputStream);
    }
}
