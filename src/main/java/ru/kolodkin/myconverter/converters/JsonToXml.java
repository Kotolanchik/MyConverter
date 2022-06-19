package ru.kolodkin.myconverter.converters;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.codehaus.jackson.map.ObjectMapper;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.Root;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JsonToXml {
    public void convert(String input, String output) throws IOException, XMLStreamException {
        writeXml(readJsonEl(readJson(input)), output);
    }

    private List<Ram> readJsonEl(Root root) {
        List<Ram> listRam = new ArrayList<>();

        for (int firstIndex = 0; firstIndex < root.getRams().size(); firstIndex++) {
            for (int secondIndex = 0; secondIndex < root.getRams().get(firstIndex).getRam().size(); secondIndex++) {
                Ram ram = new Ram();

                ram.setFirm(root.getRams().get(firstIndex).getFirm());
                ram.setSpecifications(root.getRams().get(firstIndex).getRam().get(secondIndex).getSpecifications());
                ram.setIdRam(root.getRams().get(firstIndex).getRam().get(secondIndex).getIdRam());
                ram.setReleaseYear(root.getRams().get(firstIndex).getRam().get(secondIndex).getReleaseYear());
                ram.setTitle(root.getRams().get(firstIndex).getRam().get(secondIndex).getTitle());

                listRam.add(ram);
            }
        }

        return listRam.stream().sorted((Comparator.comparingInt(Ram::getIdRam))).collect(Collectors.toList());
    }

    private Root readJson(String path) throws IOException {
        FileReader reader = new FileReader(path);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(reader, Root.class);
    }

    private void writeXml(List<Ram> listRam, String outputPath) throws XMLStreamException, FileNotFoundException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = new IndentingXMLStreamWriter(output.createXMLStreamWriter(new FileOutputStream(outputPath)));

        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("rams");

        for (int i = 0; i < listRam.size(); i++) {
            writeXmlEl(writer, listRam.get(i));
        }

        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }

    private void writeXmlEl(XMLStreamWriter writer, Ram ram) throws XMLStreamException {
        writer.writeStartElement("ram");
        writer.writeAttribute("idRam", String.valueOf(ram.getIdRam()));

        writer.writeStartElement("title");
        writer.writeCharacters(ram.getTitle());
        writer.writeEndElement();

        writer.writeStartElement("firm");
        writer.writeCharacters(ram.getFirm());
        writer.writeEndElement();

        writer.writeStartElement("specifications");
        writer.writeStartElement("memory");
        writer.writeCharacters(String.valueOf(ram.getSpecifications().getMemory()));
        writer.writeEndElement();

        writer.writeStartElement("clockFrequency");
        writer.writeCharacters(String.valueOf(ram.getSpecifications().getClockFrequency()));
        writer.writeEndElement();

        writer.writeEndElement();

        writer.writeStartElement("releaseYear");
        writer.writeCharacters(String.valueOf(ram.getReleaseYear()));
        writer.writeEndElement();

        writer.writeEndElement();
    }
}
