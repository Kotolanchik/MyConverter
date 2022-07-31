package ru.kolodkin.myconverter.factory;

import ru.kolodkin.myconverter.converter.Converter;
import ru.kolodkin.myconverter.converter.JsonToXml;
import ru.kolodkin.myconverter.converter.XmlToJson;

public final class ConverterFactory {
    public static Converter createConverter(ConverterType type) {
        if (type == ConverterType.JSON2XML) {
            return new JsonToXml();
        }
        return new XmlToJson();
    }
}
