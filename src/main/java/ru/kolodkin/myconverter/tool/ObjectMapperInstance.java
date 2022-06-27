package ru.kolodkin.myconverter.tool;

import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;

@NoArgsConstructor
public class ObjectMapperInstance {
    private static volatile ObjectMapper INSTANCE;

    public static ObjectMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (ObjectMapperInstance.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ObjectMapper();
                }
            }
        }
        return INSTANCE;
    }
}
