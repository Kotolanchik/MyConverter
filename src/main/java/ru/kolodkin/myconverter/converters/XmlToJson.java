package ru.kolodkin.myconverter.converters;

import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.Rams;
import ru.kolodkin.myconverter.models.Root;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlToJson {
    public void convert(String input, String output) throws ParserConfigurationException, IOException, SAXException {
        List<String> nameAllFirm = new ArrayList<>();
        List<Ram> ramListFromXml = new ArrayList<>();
        List<Rams> ramListForJson = new ArrayList<>();

        getRamListFromXml(readXml(input), nameAllFirm, ramListFromXml);
        jsonModelRam(nameAllFirm, ramListFromXml, ramListForJson);

        writeJson(ramListForJson, output);
    }

    private void jsonModelRam(List<String> nameAllFirm, List<Ram> ramListFromXml, List<Rams> ramListForJson) {
        for (int indexFirm = 0; indexFirm < nameAllFirm.size(); indexFirm++) {
            Rams buffRams = new Rams();
            buffRams.setFirm(nameAllFirm.get(indexFirm));
            ramListForJson.add(buffRams);
        }

        for (int firstIndex = 0; firstIndex < ramListForJson.size(); firstIndex++) {
            for (int secondIndex = 0; secondIndex < ramListFromXml.size(); secondIndex++) {
                if (ramListFromXml.get(secondIndex).getFirm().equals(ramListForJson.get(firstIndex).getFirm())) {
                    ramListForJson.get(firstIndex).getRam().add(new Ram(
                            ramListFromXml.get(secondIndex).getIdRam(),
                            ramListFromXml.get(secondIndex).getTitle(),
                            ramListFromXml.get(secondIndex).getReleaseYear(),
                            ramListFromXml.get(secondIndex).getSpecifications()));
                }
            }
        }
    }

    private void writeJson(List<Rams> ramListForJson, String output) throws IOException {
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(output), new Root(ramListForJson));
    }

    private Document readXml(String path) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(path));
    }

    private void getRamListFromXml(Document document, List<String> nameAllFirm, List<Ram> ramListFromXml) {
        NodeList ramNodeList = document.getDocumentElement().getElementsByTagName("ram");

        for (int indexNodeRam = 0; indexNodeRam < ramNodeList.getLength(); indexNodeRam++) {
            if (ramNodeList.item(indexNodeRam).getNodeType() == Node.ELEMENT_NODE) {
                Element ramElement = (Element) ramNodeList.item(indexNodeRam);

                Ram ram = new Ram();

                ram.setIdRam(Integer.valueOf(ramElement.getAttributes().getNamedItem("idRam").getNodeValue()));

                NodeList childNodes = ramElement.getChildNodes();

                for (int countTagRam = 0; countTagRam < childNodes.getLength(); countTagRam++) {
                    if (childNodes.item(countTagRam).getNodeType() == Node.ELEMENT_NODE) {
                        Element ramChildElement = (Element) childNodes.item(countTagRam);

                        switch (ramChildElement.getNodeName()) {
                            case "title": {
                                ram.setTitle(ramChildElement.getTextContent());
                                break;
                            }

                            case "firm": {
                                ram.setFirm(ramChildElement.getTextContent());
                                if (!nameAllFirm.contains(ramChildElement.getTextContent()))
                                    nameAllFirm.add(ramChildElement.getTextContent());
                                break;
                            }

                            case "releaseYear": {
                                ram.setReleaseYear(Integer.valueOf(ramChildElement.getTextContent()));
                                break;
                            }

                            case "specifications": {
                                NodeList tagsNode = ramChildElement.getChildNodes();
                                for (int countInnerTag = 0; countInnerTag < tagsNode.getLength(); countInnerTag++) {
                                    Node tagNode = tagsNode.item(countInnerTag);
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
                ramListFromXml.add(ram);
            }
        }

        nameAllFirm = nameAllFirm.stream().distinct().toList();
    }
}
