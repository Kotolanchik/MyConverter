package ru.kolodkin.myconverter.tool;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import ru.kolodkin.myconverter.exception.InvalidValidateException;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Slf4j
@UtilityClass
public final class Validate {
    public void validateArgument(final String[] args) throws InvalidValidateException {
        StringBuilder errorMessage = new StringBuilder();
        boolean check = true;
        final String inCorrectStr = "Вы ввели некорректный аргумент: %s\n";

        if (args.length != 3) {
            errorMessage.append("Количество аргументов должно быть равно 3.\n");
            check = false;
        }

        if (!(StringUtils.equals(args[0], "XML2JSON") || StringUtils.equals(args[0], "JSON2XML"))) {
            errorMessage.append(String.format(inCorrectStr, args[0]));
            check = false;
        }

        if (!(equalsIgnoreCase(getExtension(args[1]), "xml")
                || equalsIgnoreCase(getExtension(args[1]), "json"))) {
            errorMessage.append(String.format(inCorrectStr, args[1]));
            check = false;
        }

        if (!(equalsIgnoreCase(getExtension(args[2]), ("json"))
                || equalsIgnoreCase(getExtension(args[2]), ("xml")))) {
            errorMessage.append(String.format(inCorrectStr, args[2]));
            check = false;
        }

        if (!check) {
            throw new InvalidValidateException(errorMessage.toString());
        }
    }
}
