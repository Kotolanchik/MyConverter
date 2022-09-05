package ru.kolodkin.myconverter;

import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.kolodkin.myconverter.exception.InvalidValidateException;
import ru.kolodkin.myconverter.factory.ConverterFactory;
import ru.kolodkin.myconverter.factory.ConverterType;
import ru.kolodkin.myconverter.tool.Validate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            Validate.validateArgument(args);

            val inputStream = new FileInputStream(args[1]);
            val outputStream = new FileOutputStream(args[2]);

            ConverterFactory.createConverter(ConverterType.valueOf(args[0]))
                    .convert(inputStream, outputStream);

            inputStream.close();
            outputStream.close();
            log.info("Конвертация прошла успешно.");
        } catch (IOException | JAXBException | InvalidValidateException exception) {
            exception.printStackTrace();
        }
    }
}

