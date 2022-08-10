import jakarta.xml.bind.JAXBException;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.kolodkin.myconverter.converter.XmlToJson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class XmlToJsonTest {
    final private XmlToJson xmlToJson = new XmlToJson();

    @Test
    void xmlToJsonNullTest() throws IOException, JSONException, JAXBException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/xml/inputNull.xml");
             val byteArrayOutputStream = new ByteArrayOutputStream();
             val expectedFile = getClass().getResourceAsStream("expectedFile/json/inputNull.json")) {

            xmlToJson.convert(fileInputStream, byteArrayOutputStream);

            JSONAssert.assertEquals(
                    IOUtils.toString(expectedFile, StandardCharsets.UTF_8),
                    byteArrayOutputStream.toString(StandardCharsets.UTF_8),
                    true
            );
        }
    }

    @Test
    void xmlToJsonStandardTest() throws IOException, JSONException, JAXBException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/xml/input.xml");
             val byteArrayOutputStream = new ByteArrayOutputStream();
             val expectedFile = getClass().getResourceAsStream("expectedFile/json/input.json")) {

            xmlToJson.convert(fileInputStream, byteArrayOutputStream);

            JSONAssert.assertEquals(
                    IOUtils.toString(expectedFile, StandardCharsets.UTF_8),
                    byteArrayOutputStream.toString(StandardCharsets.UTF_8),
                    true
            );
        }
    }
}
