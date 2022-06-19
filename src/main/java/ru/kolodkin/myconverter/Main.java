package ru.kolodkin.myconverter;

import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.converters.JsonToXml;
import ru.kolodkin.myconverter.converters.XmlToJson;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        if (args[0].split("\\.")[1].equals("xml")) {
            XmlToJson xmltojson = new XmlToJson();
            xmltojson.convert(args[0], args[1]);

            System.out.println("complete");
        } else if (args[0].split("\\.")[1].equals("json")){
            JsonToXml jsonToXml = new JsonToXml();
            jsonToXml.convert(args[0], args[1]);

            System.out.println("complete");
        } else {
            System.out.println("Что-то пошло не так...");
        }
    }
}
