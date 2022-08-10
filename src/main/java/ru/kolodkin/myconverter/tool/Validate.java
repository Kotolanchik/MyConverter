package ru.kolodkin.myconverter.tool;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

@UtilityClass
public final class Validate {
    public ImmutablePair<String, Boolean> validateArgument(String type, String inputPath, String outputPath) {
        if (!(type.equals("XML2JSON") || type.equals("JSON2XML"))) {
            return ImmutablePair.of("Вы ввели некорректный аргумент: " + type, false);
        }

        if (!(FilenameUtils.getExtension(inputPath).equals(FilenameUtils.getExtension("/src/main/document/input.xml"))
                || FilenameUtils.getExtension(inputPath).equals(FilenameUtils.getExtension("/src/main/document/output.json")))) {
            return ImmutablePair.of("Вы ввели некорректный аргумент: " + inputPath, false);
        }

        if (!(FilenameUtils.getExtension(outputPath).equals(FilenameUtils.getExtension("/src/main/document/output.json"))
                || FilenameUtils.getExtension(outputPath).equals(FilenameUtils.getExtension("/src/main/document/input.xml")))) {
            return ImmutablePair.of("Вы ввели некорректный аргумент: " + outputPath, false);
        }

        return ImmutablePair.of("С аргументами всё в порядке.", true);
    }
}
