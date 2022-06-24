package ru.kolodkin.myconverter.converters;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Converter {
    /**
     * ������������ ����� � ������� ������������.
     *
     * @param input  ���� ��� ������.
     * @param output ���� ��� ������.
     */
    void convert(InputStream input, OutputStream output) throws IOException, JAXBException;
}
