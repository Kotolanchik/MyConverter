package ru.kolodkin.myconverter;

import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.Rams;
import ru.kolodkin.myconverter.models.Root;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlToJson {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Document document = readXml("input.xml");

        List<String> allFirm = new ArrayList<>();
        List<Ram> ramList = new ArrayList<>();
        List<Rams> rams = new ArrayList<>();

        getRamListFromXml(document, allFirm, ramList);
        jsonModelRam(allFirm, ramList, rams);

        writeJson("output.json", rams);
    }

    public static void jsonModelRam(List<String> allFirm, List<Ram> ramList, List<Rams> rams) {
        for (int i = 0; i < allFirm.size(); i++) {
            Rams buffRams = new Rams();
            buffRams.setFirm(allFirm.get(i));
            rams.add(buffRams);
        }

        for (int i = 0; i < rams.size(); i++) {
            for (int j = 0; j < ramList.size(); j++) {
                if (ramList.get(j).getFirm().equals(rams.get(i).getFirm())) {
                    rams.get(i).getRam().add(new Ram(
                            ramList.get(j).getIdRam(),
                            ramList.get(j).getTitle(),
                            ramList.get(j).getReleaseYear(),
                            ramList.get(j).getSpecifications()));
                }
            }
        }
    }

    public static void writeJson(String output, List<Rams> rams) throws IOException {
        Root root = new Root(rams);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(output), root);
    }

    public static Document readXml(String path) throws ParserConfigurationException, IOException, SAXException {
        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам ну84884жно.
        Document document = documentBuilder.parse(new File("input.xml"));
        document.getDocumentElement().normalize();
        return document;
    }

    public static void getRamListFromXml(Document document, List<String> allFirm, List<Ram> ramList) {
        Element ramsElement = (Element) document.getElementsByTagName("rams").item(0);
        NodeList ramNodeList = document.getDocumentElement().getElementsByTagName("ram");

        for (int i = 0; i < ramNodeList.getLength(); i++) {
            if (ramNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) { // проверка типа
                Element ramElement = (Element) ramNodeList.item(i);

                Ram ram = new Ram();

                ram.setIdRam(Integer.valueOf(ramElement.getAttributes().getNamedItem("idRam").getNodeValue()));

                NodeList childNodes = ramElement.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) { // проверка типа
                        Element ramChildElement = (Element) childNodes.item(j);

                        switch (ramChildElement.getNodeName()) {
                            case "title": {
                                ram.setTitle(ramChildElement.getTextContent());
                                break;
                            }

                            case "firm": {
                                ram.setFirm(ramChildElement.getTextContent());
                                if (!allFirm.contains(ramChildElement.getTextContent()))
                                    allFirm.add(ramChildElement.getTextContent());
                                break;
                            }

                            case "releaseYear": {
                                ram.setReleaseYear(Integer.valueOf(ramChildElement.getTextContent()));
                                break;
                            }

                            case "specifications": {
                                NodeList tagsNode = ramChildElement.getChildNodes();
                                for (int k = 0; k < tagsNode.getLength(); k++) {
                                    Node tagNode = tagsNode.item(k);
                                    if (tagNode.getNodeType() != Node.TEXT_NODE) {
                                        if (tagNode.getNodeName() == "clockFrequency") {
                                            ram.getSpecifications().setClockFrequency(Integer.valueOf(tagNode.getTextContent()));
                                        }

                                        ram.getSpecifications().setMemory(Integer.valueOf(tagNode.getTextContent()));
                                    }
                                }
                                break;
                            }

                            case "clockFrequency": {
                                ram.getSpecifications().setClockFrequency(Integer.valueOf(ramChildElement.getTextContent()));
                                break;
                            }
                        }
                    }
                }
                ramList.add(ram);
            }
        }

        allFirm = allFirm.stream().distinct().toList();
    }


}