package ru.kolodkin.myconverter;

import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.converters.JsonToXml;
import ru.kolodkin.myconverter.converters.XmlToJson;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String extension = args[0].split("\\.")[1];

        if (extension.equals("xml")) {
            new XmlToJson().convert(args[0], args[1]);

            System.out.println("complete");
        } else if (extension.equals("json")){
            new JsonToXml().convert(args[0], args[1]);

            System.out.println("complete");
        } else {
            System.out.println("no complete...");
        }
    }
}
