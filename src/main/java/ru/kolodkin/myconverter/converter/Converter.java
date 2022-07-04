package ru.kolodkin.myconverter.converter;

import javax.xml.bind.JAXBException;
import java.io.*;

public interface Converter {
    void convert(FileInputStream input, FileOutputStream output) throws IOException, JAXBException;
}
