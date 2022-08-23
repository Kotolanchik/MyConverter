import jakarta.xml.bind.JAXBException;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import ru.kolodkin.myconverter.converter.JsonToXml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

class JsonToXmlTest {
    final private JsonToXml jsonToXml = new JsonToXml();

    @BeforeEach
    void initialization() {
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Test
    void jsonToXmlNullTest() throws IOException, SAXException, JAXBException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/json/inputNull.json");
             val byteArrayOutputStream = new ByteArrayOutputStream();
             val expectedFile = getClass().getResourceAsStream("expectedFile/xml/inputNull.xml")) {

            jsonToXml.convert(fileInputStream, byteArrayOutputStream);

            XMLAssert.assertXMLEqual(
                    IOUtils.toString(expectedFile, UTF_8),
                    byteArrayOutputStream.toString(UTF_8)
            );
        }
    }

    @Test
    void jsonToXmlStandardTest() throws IOException, SAXException, JAXBException {
        try (val fileInputStream = getClass().getResourceAsStream("inputFile/json/input.json");
             val byteArrayOutputStream = new ByteArrayOutputStream();
             val expectedFile = getClass().getResourceAsStream("expectedFile/xml/input.xml")) {

            jsonToXml.convert(fileInputStream, byteArrayOutputStream);

            XMLAssert.assertXMLEqual(
                    IOUtils.toString(expectedFile, UTF_8),
                    byteArrayOutputStream.toString(UTF_8)
            );
        }
    }
}
