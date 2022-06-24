package ru.kolodkin.myconverter;

import lombok.val;
import ru.kolodkin.myconverter.converters.XmlToJson;
import ru.kolodkin.myconverter.factory.ConverterFactory;
import ru.kolodkin.myconverter.factory.ConverterType;
import ru.kolodkin.myconverter.models.Ram;
import ru.kolodkin.myconverter.models.RootXml;
import ru.kolodkin.myconverter.tools.Extension;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (Extension.checkExtension(args)) {
            System.out.println("С расширениями всё в порядке.");

            try (val inputStream = new FileInputStream(args[1]);
                 val outputStream = new FileOutputStream(args[2])) {

                new ConverterFactory().createConverter(ConverterType
                        .valueOf(args[0])).convert(inputStream, outputStream);

                System.out.println("Конвертация прошла успешно.");

            } catch (FileNotFoundException exception) {
                System.out.println("Файл не найден...");
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
