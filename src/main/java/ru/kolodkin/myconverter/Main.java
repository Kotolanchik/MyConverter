package ru.kolodkin.myconverter;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import ru.kolodkin.myconverter.factory.ConverterFactory;
import ru.kolodkin.myconverter.factory.ConverterType;
import ru.kolodkin.myconverter.tool.Validate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Log4j2
public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            log.error("Количество аргументов должно быть равно 3. У вас всего: " + args.length);
            return;
        }

        if (!Validate.validateArgument(args[0], args[1], args[2])) {
            log.error("Входные аргументы некорректны.");
            return;
        }

        log.info("С расширениями всё в порядке.");

        try (val inputStream = new FileInputStream(args[1]);
             val outputStream = new FileOutputStream(args[2])) {

            ConverterFactory.createConverter(ConverterType.valueOf(args[0]))
                    .convert(inputStream, outputStream);

            log.info("Конвертация прошла успешно.");
        } catch (FileNotFoundException exception) {
            log.fatal("Файл не найден... " + exception);
        } catch (IllegalArgumentException exception) {
            log.fatal("Неправильный ввод входных данных... ", exception);
        } catch (Exception exception) {
            log.fatal("Непредвиденная ошибка... ", exception);
        }
    }
}

