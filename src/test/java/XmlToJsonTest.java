import lombok.val;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ru.kolodkin.myconverter.converter.XmlToJson;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

class XmlToJsonTest {
    static XmlToJson xmlToJson;

    @BeforeAll
    static void initialization() {
        xmlToJson = new XmlToJson();
    }

    @Test
    void xmlToJsonNullTest() {
        try (val fileInputStream = new FileInputStream("src/test/resources/inputFile/xml/inputNull.xml");
             val fileOutputStream = new FileOutputStream("src/test/resources/outputFile/json/outputNull.json")) {
            xmlToJson.convert(fileInputStream, fileOutputStream);

            JSONAssert.assertEquals(FileUtils.readFileToString(new File("src/test/resources/expectedFile/json/inputNull.json")),
                    FileUtils.readFileToString(new File("src/test/resources/outputFile/json/outputNull.json")), true);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге... " + Arrays.toString(e.getStackTrace()));
        } catch (JSONException e) {
            throw new RuntimeException("Ошибка при сравнение json файлов... " + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при вводе/выводе... " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void xmlToJsonStandardTest() {
        try (val fileInputStream = new FileInputStream("src/test/resources/inputFile/xml/input.xml");
             val fileOutputStream = new FileOutputStream("src/test/resources/outputFile/json/output.json")) {
            xmlToJson.convert(fileInputStream, fileOutputStream);

            JSONAssert.assertEquals(FileUtils.readFileToString(new File("src/test/resources/expectedFile/json/input.json")),
                    FileUtils.readFileToString(new File("src/test/resources/outputFile/json/output.json")), true);
        } catch (JAXBException e) {
            throw new RuntimeException("Ошибка при парсинге... " + Arrays.toString(e.getStackTrace()));
        } catch (JSONException e) {
            throw new RuntimeException("Ошибка при сравнение json файлов... " + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при вводе/выводе... " + Arrays.toString(e.getStackTrace()));
        }
    }
}
