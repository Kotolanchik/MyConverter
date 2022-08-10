package ru.kolodkin.myconverter;

import jakarta.xml.bind.JAXBException;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import ru.kolodkin.myconverter.factory.ConverterFactory;
import ru.kolodkin.myconverter.factory.ConverterType;
import ru.kolodkin.myconverter.tool.Validate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Log4j2
public class Main {
    public static void main(String[] args) {
        val validateArg = Validate.validateArgument(args[0], args[1], args[2]);
        if (args.length != 3) {
            throw new ArrayIndexOutOfBoundsException("Вы ввели не все аргументы.");
        }

        if (!validateArg.right) {
            throw new IllegalArgumentException(validateArg.left);
        }

        log.info(validateArg.left);

        try (val inputStream = new FileInputStream(args[1]);
             val outputStream = new FileOutputStream(args[2])) {

            ConverterFactory.createConverter(ConverterType.valueOf(args[0]))
                    .convert(inputStream, outputStream);

            log.info("Конвертация прошла успешно.");
        } catch (JAXBException | IOException exception) {
            exception.printStackTrace();
        }
    }
}

