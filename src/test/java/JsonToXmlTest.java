import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.kolodkin.myconverter.converter.JsonToXml;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonToXmlTest {
    static JsonToXml jsonToXml;

    @BeforeAll
    public static void initialization() {
        jsonToXml = new JsonToXml();
    }

    @Test
    public void jsonToXmlNullTest(){
        final String inputFile = "src/test/resources/inputFile/json/inputNull.json";
        final String outputFile = "src/test/resources/outputFile/xml/outputNull.xml";
        final String expectedFile = "src/test/resources/expectedFile/xml/inputNull.xml";


        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            jsonToXml.convert(fileInputStream, fileOutputStream);

            assertEquals(Files.readString(Path.of(expectedFile)), Files.readString(Path.of(outputFile)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void jsonToXmlStandardTest(){
        final String inputFile = "src/test/resources/inputFile/json/input.json";
        final String outputFile = "src/test/resources/outputFile/xml/output.xml";
        final String expectedFile = "src/test/resources/expectedFile/xml/input.xml";


        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            jsonToXml.convert(fileInputStream, fileOutputStream);

            assertEquals(Files.readString(Path.of(expectedFile)), Files.readString(Path.of(outputFile)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
