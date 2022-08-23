package ru.kolodkin.myconverter.factory;

import lombok.experimental.UtilityClass;
import ru.kolodkin.myconverter.converter.Converter;
import ru.kolodkin.myconverter.converter.JsonToXml;
import ru.kolodkin.myconverter.converter.XmlToJson;

@UtilityClass
public final class ConverterFactory {
    public Converter createConverter(final ConverterType type) {
        return type == ConverterType.JSON2XML ? new JsonToXml() : new XmlToJson();
    }
}
