package ru.kolodkin.myconverter.factory;

import ru.kolodkin.myconverter.converters.Converter;
import ru.kolodkin.myconverter.converters.JsonToXml;
import ru.kolodkin.myconverter.converters.XmlToJson;

public class ConverterFactory {
    public Converter createConverter(ConverterType type) {
        Converter converter = null;

        switch (type) {
            case JSON2XML:
                converter = new JsonToXml();
                break;
            case XML2JSON:
                converter = new XmlToJson();
                break;
        }

        return converter;
    }
}
