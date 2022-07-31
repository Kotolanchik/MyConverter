import lombok.val;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.converter.JsonToXml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class JsonToXmlTest {
    final JsonToXml jsonToXml = new JsonToXml();

    @BeforeEach
    void initialization() {
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Test
    void jsonToXmlNullTest() throws IOException, SAXException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/json/inputNull.json");
             val fileOutputStream = new FileOutputStream("src/test/resources/outputFile/xml/outputNull.xml")) {
            jsonToXml.convert(fileInputStream, fileOutputStream);
        }

        XMLAssert.assertXMLEqual(
                Files.readString(Path.of("src/test/resources/expectedFile/xml/inputNull.xml")),
                Files.readString(Path.of("src/test/resources/outputFile/xml/outputNull.xml"))
        );
    }

    @Test
    void jsonToXmlStandardTest() throws IOException, SAXException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/json/input.json");
             val fileOutputStream = new FileOutputStream("src/test/resources/outputFile/xml/output.xml")) {
            jsonToXml.convert(fileInputStream, fileOutputStream);
        }

        XMLAssert.assertXMLEqual(
                Files.readString(Path.of("src/test/resources/expectedFile/xml/input.xml")),
                Files.readString(Path.of("src/test/resources/outputFile/xml/output.xml"))
        );
    }
}
