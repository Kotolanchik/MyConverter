package ru.kolodkin.myconverter;

import lombok.val;
import org.apache.commons.io.FilenameUtils;
import ru.kolodkin.myconverter.factory.ConverterFactory;
import ru.kolodkin.myconverter.factory.ConverterType;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (FilenameUtils.getExtension(args[1]).equals("xml") && FilenameUtils.getExtension(args[2]).equals("json")
                || FilenameUtils.getExtension(args[1]).equals("json") && FilenameUtils.getExtension(args[2]).equals("xml")) {
            System.out.println("С расширениями всё в порядке.");
            try (val inputStream = new FileInputStream(args[1]);
                 val outputStream = new FileOutputStream(args[2])) {

                new ConverterFactory().createConverter(ConverterType
                        .valueOf(args[0])).convert(inputStream, outputStream);

                System.out.println("Конвертация прошла успешно.");
            } catch (FileNotFoundException exception) {
                System.err.println("Файл не найден... \n" + Arrays.toString(exception.getStackTrace()));
            } catch (IOException exception) {
                System.err.println("Проблема с доступом к данным... \n" + Arrays.toString(exception.getStackTrace()));
            } catch (IllegalArgumentException exception) {
                System.err.println("Неправильный ввод входных данных... \n" + Arrays.toString(exception.getStackTrace()));
            } catch (JAXBException exception) {
                System.err.println("Ошибка при парсинге... \n" + Arrays.toString(exception.getStackTrace()));
            } catch (Exception exception) {
                System.err.println("Непредвиденная ошибка... \n" + Arrays.toString(exception.getStackTrace()));
            }
        }
    }
}
