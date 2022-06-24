package ru.kolodkin.myconverter.converters;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Converter {
    /**
     * Конвертирует файлы с разными расширениями.
     *
     * @param input  Файл для чтения.
     * @param output Файл для записи.
     */
    void convert(InputStream input, OutputStream output) throws IOException, JAXBException;
}
