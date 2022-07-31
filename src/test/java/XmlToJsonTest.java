import lombok.val;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.kolodkin.myconverter.converter.XmlToJson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class XmlToJsonTest {
    final XmlToJson xmlToJson = new XmlToJson();

    @Test
    void xmlToJsonNullTest() throws IOException, JSONException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/xml/inputNull.xml");
             val fileOutputStream = new FileOutputStream("src/test/resources/outputFile/json/outputNull.json")) {
            xmlToJson.convert(fileInputStream, fileOutputStream);
        }

        JSONAssert.assertEquals(
                FileUtils.readFileToString(
                        new File("src/test/resources/expectedFile/json/inputNull.json"),
                        "utf-8"),
                FileUtils.readFileToString(
                        new File("src/test/resources/outputFile/json/outputNull.json"),
                        "utf-8"),
                true
        );
    }

    @Test
    void xmlToJsonStandardTest() throws IOException, JSONException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/xml/input.xml");
             val fileOutputStream = new FileOutputStream("src/test/resources/outputFile/json/output.json")) {
            xmlToJson.convert(fileInputStream, fileOutputStream);
        }

        JSONAssert.assertEquals(
                FileUtils.readFileToString(
                        new File("src/test/resources/expectedFile/json/input.json"),
                        "utf-8"),
                FileUtils.readFileToString(
                        new File("src/test/resources/outputFile/json/output.json"),
                        "utf-8"),
                true
        );
    }
}
