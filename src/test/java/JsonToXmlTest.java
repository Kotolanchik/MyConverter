import lombok.val;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.converter.JsonToXml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

class JsonToXmlTest {
    static JsonToXml jsonToXml;

    @BeforeAll
    static void initialization() {
        jsonToXml = new JsonToXml();
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    void jsonToXmlNullTest() {
        try (val fileInputStream = new FileInputStream("src/test/document/inputFile/json/inputNull.json");
             val fileOutputStream = new FileOutputStream("src/test/document/outputFile/xml/outputNull.xml")) {
            jsonToXml.convert(fileInputStream, fileOutputStream);

            XMLAssert.assertXMLEqual(Files.readString(Path.of("src/test/document/expectedFile/xml/inputNull.xml")),
                    Files.readString(Path.of("src/test/document/outputFile/xml/outputNull.xml")));
        } catch (SAXException e) {
            throw new RuntimeException("Ошибка при сравнение xml файлов... " + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при вводе/выводе... " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    void jsonToXmlStandardTest() {
        try (val fileInputStream = new FileInputStream("src/test/document/inputFile/json/input.json");
             val fileOutputStream = new FileOutputStream("src/test/document/outputFile/xml/output.xml")) {
            jsonToXml.convert(fileInputStream, fileOutputStream);

            XMLAssert.assertXMLEqual(Files.readString(Path.of("src/test/document/expectedFile/xml/input.xml")),
                    Files.readString(Path.of("src/test/document/outputFile/xml/output.xml")));
        } catch (SAXException e) {
            throw new RuntimeException("Ошибка при сравнение xml файлов... " + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при вводе/выводе... " + Arrays.toString(e.getStackTrace()));
        }
    }
}
