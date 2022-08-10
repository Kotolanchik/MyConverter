package ru.kolodkin.myconverter.converter.read;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import ru.kolodkin.myconverter.model.RootXml;

import java.io.InputStream;


public final class XMLReader implements IReader<RootXml> {
    @Override
    public RootXml read(InputStream inputStream) throws JAXBException {
        return (RootXml) JAXBContext.newInstance(RootXml.class)
                .createUnmarshaller()
                .unmarshal(inputStream);
    }
}
