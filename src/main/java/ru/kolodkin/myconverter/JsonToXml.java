package ru.kolodkin.myconverter;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.codehaus.jackson.map.ObjectMapper;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.Rams;
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

import static java.lang.System.out;

public class JsonToXml {
    public static void main(String[] args) throws IOException, XMLStreamException {
//        Root root = readJson("output.json");
//        writeXml(readJsonEl(root), "input.xml");
        Root root = readJson(args[0]);
        writeXml(readJsonEl(root), args[1]);
        out.println("Good");
    }

    public static List<Ram> readJsonEl(Root root) {
        List<Ram> listRam = new ArrayList<>();

        for (int i = 0; i < root.getRams().size(); i++) {
            for (int j = 0; j < root.getRams().get(i).getRam().size(); j++) { // root.getRams().get(i).getRams().size() => root(list rams) конкретный rams -->  rams с оперативкой и списком ram ов берем его сайз
                Ram ram = new Ram();
                ram.setFirm(root.getRams().get(i/*первй счетчик*/).getFirm());
                ram.setSpecifications(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getSpecifications());
                ram.setIdRam(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getIdRam());
                ram.setReleaseYear(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getReleaseYear());
                ram.setTitle(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getTitle());
                listRam.add(ram);
            }
        }

        return listRam.stream().sorted((Comparator.comparingInt(Ram::getIdRam))).collect(Collectors.toList());
    }

    public static Root readJson(String path) throws IOException {
        FileReader reader = new FileReader(path);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(reader, Root.class);
    }

    public static void writeXml(List<Ram> listRam, String outputPath) throws XMLStreamException, FileNotFoundException {
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

    public static void writeXmlEl(XMLStreamWriter writer, Ram ram) throws XMLStreamException {
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

