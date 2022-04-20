package ru.kolodkin.myconverter;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.Root;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class JsonToXml {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, TransformerException {
        FileReader reader = new FileReader("output.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Root root = objectMapper.readValue(reader, Root.class);
        root.getRams().forEach(out::println);

        List<Ram> listRam = new ArrayList<>();


        for (int i = 0; i < root.getRams().size(); i++) {
            for (int j = 0; j < root.getRams().get(i).getRam().size(); j++) { // root.getRams().get(i).getRams().size() => root(list rams) конкретный rams -->  rams с оперативкой и списком ram ов берем его сайз
                Ram ram = new Ram();
                ram.setFirm(root.getRams().get(i/*первй счетчик*/).getFirm());
                ram.setSpecifications(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getSpecifications());
                ram.setIdRam(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getIdRam());
                ram.setDepartment(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getDepartment());
                ram.setReleaseYear(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getReleaseYear());
                ram.setTitle(root.getRams().get(i).getRam().get(j/*второй счетчик*/).getTitle());
                listRam.add(ram);
            }
        }

        listRam = listRam.stream().sorted((Comparator.comparingInt(Ram::getIdRam))).collect(Collectors.toList());

        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = new IndentingXMLStreamWriter(output.createXMLStreamWriter(new FileOutputStream("ooot.xml")));

        writer.writeStartDocument("utf-8", "1.0");
        writer.writeStartElement("rams");
        writer.writeAttribute("department", listRam.get(1).getDepartment());

        write(writer,listRam.get(0));
        write(writer,listRam.get(1));
        write(writer,listRam.get(2));
        writer.writeEndElement();



        //
        writer.writeEndDocument();
        //
        writer.flush();

        writer.close();


        listRam.forEach(out::println);
    }

    public static void write(XMLStreamWriter writer, Ram ram) throws XMLStreamException {
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
