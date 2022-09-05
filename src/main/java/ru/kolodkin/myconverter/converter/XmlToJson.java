package ru.kolodkin.myconverter.converter;

import com.google.common.collect.ImmutableSet;
import jakarta.xml.bind.JAXBException;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import ru.kolodkin.myconverter.model.Ram;
import ru.kolodkin.myconverter.model.Rams;
import ru.kolodkin.myconverter.model.RootXml;
import ru.kolodkin.myconverter.tool.Validate;
import ru.kolodkin.myconverter.tool.mapper.JSONWriter;
import ru.kolodkin.myconverter.tool.mapper.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static java.util.stream.Collectors.toSet;

public final class XmlToJson implements Converter<ArrayList<Rams>, RootXml> {
    public void convert(InputStream input, OutputStream output) throws JAXBException, IOException {
        JSONWriter.write(
                transform(XMLReader.read(input)),
                output
        );
    }

    public ArrayList<Rams> transform(@NonNull final RootXml rootXml) {
        val ramListForJson = new ArrayList<Rams>();
        getUniqueFirm(rootXml)
                .forEach(firm -> ramListForJson.add(new Rams(firm)));

        for (val rams : ramListForJson) {
            rootXml.getRamList().stream()
                    .filter(ram -> StringUtils.equals(rams.getFirm(), ram.getFirm()))
                    .forEach(ram -> rams.getRam().add(ram));
        }

        return ramListForJson;
    }

    private ImmutableSet<String> getUniqueFirm(final RootXml rootXml) {
        return ImmutableSet.copyOf(rootXml.getRamList()
                .stream()
                .map(Ram::getFirm)
                .collect(toSet()));
    }
}