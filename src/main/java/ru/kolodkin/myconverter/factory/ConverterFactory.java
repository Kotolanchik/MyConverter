package ru.kolodkin.myconverter.factory;

import ru.kolodkin.myconverter.converter.Converter;
import ru.kolodkin.myconverter.converter.JsonToXml;
import ru.kolodkin.myconverter.converter.XmlToJson;


public class ConverterFactory {
    public Converter createConverter(ConverterType type) {
        return type == ConverterType.JSON2XML ? new JsonToXml()
                : type == ConverterType.XML2JSON ? new XmlToJson()
                : null;
    }
}
