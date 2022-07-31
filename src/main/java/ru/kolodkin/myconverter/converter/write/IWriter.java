package ru.kolodkin.myconverter.converter.write;

import java.io.OutputStream;

public interface IWriter<T> {
    void write(T model, OutputStream outputStream);
}
