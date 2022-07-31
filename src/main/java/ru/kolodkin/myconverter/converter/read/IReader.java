package ru.kolodkin.myconverter.converter.read;

import java.io.InputStream;

public interface IReader<T> {
   T read(InputStream inputStream);
}
