package ru.kolodkin.myconverter.converter.write;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.io.OutputStream;

public interface IWriter<T> {
    void write(T model, OutputStream outputStream) throws IOException, JAXBException;
}
