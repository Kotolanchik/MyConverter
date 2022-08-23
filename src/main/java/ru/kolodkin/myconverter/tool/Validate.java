package ru.kolodkin.myconverter.tool;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Slf4j
@UtilityClass
public final class Validate {
    public void validateArgument(final String[] args) {
        if (args.length != 3) {
            throw new ArrayIndexOutOfBoundsException("Неполное количество аргументов.");
        }

        if (!(StringUtils.equals(args[0], "XML2JSON") || StringUtils.equals(args[0], "JSON2XML"))) {
            throw new IllegalArgumentException(String.format("Вы ввели некорректный аргумент: %s", args[0]));
        }

        if (!(equalsIgnoreCase(getExtension(args[1]), "xml")
                || equalsIgnoreCase(getExtension(args[1]), "json"))) {
            throw new IllegalArgumentException(String.format("Вы ввели некорректный аргумент: %s", args[0]));
        }

        if (!(equalsIgnoreCase(getExtension(args[2]), ("json"))
                || equalsIgnoreCase(getExtension(args[2]), ("xml")))) {
            throw new IllegalArgumentException(String.format("Вы ввели некорректный аргумент: %s", args[2]));
        }
        log.info("С аргументами всё в порядке");
    }

    public void validateNullObject(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            throw new NullPointerException("Получен пустой объект.");
        }
    }
}
