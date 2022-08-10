package ru.kolodkin.myconverter.converter.read;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.io.InputStream;

public interface IReader<T> {
    T read(InputStream inputStream) throws IOException, JAXBException;
}
