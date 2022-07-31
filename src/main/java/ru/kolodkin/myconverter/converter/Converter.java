package ru.kolodkin.myconverter.converter;

import jakarta.xml.bind.JAXBException;

import java.io.*;

public interface Converter {
    void convert(InputStream input, OutputStream output) throws IOException, JAXBException;
}
