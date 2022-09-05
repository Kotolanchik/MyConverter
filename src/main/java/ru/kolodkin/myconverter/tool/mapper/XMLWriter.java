package ru.kolodkin.myconverter.tool.mapper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.val;
import ru.kolodkin.myconverter.model.RootXml;

import java.io.OutputStream;

public final class XMLWriter {
    public static void write(final RootXml model, final OutputStream outputStream) throws JAXBException {
        val jaxbMarshaller = JAXBContext.newInstance(RootXml.class).createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(model, outputStream);
    }
}
