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
        // Получение фабрики, чтобы после получить билдер документов.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Получили из фабрики билдер, который парсит XML, создает структуру Document в виде иерархического дерева.
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        // Запарсили XML, создав структуру Document. Теперь у нас есть доступ ко всем элементам, каким нам ну84884жно.
        Document document = documentBuilder.parse(new File("input.xml"));
        document.getDocumentElement().normalize();

        Element ramsElement = (Element) document.getElementsByTagName("rams").item(0);
        String department = ramsElement.getAttribute("department");

        // Получение списка всех элементов employee внутри корневого элемента (getDocumentElement возвращает ROOT элемент XML файла).
        NodeList ramNodeList = document.getDocumentElement().getElementsByTagName("ram");
        List<String> allFirm = new ArrayList<>();

        List<Ram> ramList = new ArrayList<>();
        for (int i = 0; i < ramNodeList.getLength(); i++) {
            if (ramNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) { // проверка типа
                Element ramElement = (Element) ramNodeList.item(i);

                Ram ram = new Ram();

                ram.setDepartment(department);
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

        ramList.forEach(System.out::println);
        System.out.println("\n\n\n");

        List<Rams> rams = new ArrayList<>();

        allFirm = allFirm.stream().distinct().toList();


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
                            ramList.get(j).getDepartment(),
                            ramList.get(j).getSpecifications()));
                }
            }
        }

        Root root = new Root(rams);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), root);
        System.out.println(jsonString);
    }
}