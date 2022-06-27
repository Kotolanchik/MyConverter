import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.kolodkin.myconverter.converter.XmlToJson;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlToJsonTest {
    static XmlToJson xmlToJson;

    @BeforeAll
    public static void initialization() {
        xmlToJson = new XmlToJson();
    }

    @Test
    public void xmlToJsonNullTest() {
        final String inputFile = "src/test/resources/inputFile/xml/inputNull.xml";
        final String outputFile = "src/test/resources/outputFile/json/outputNull.json";
        final String expectedFile = "src/test/resources/expectedFile/json/inputNull.json";


        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            xmlToJson.convert(fileInputStream, fileOutputStream);

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
    public void xmlToJsonStandardTest() {
        final String inputFile = "src/test/resources/inputFile/xml/input.xml";
        final String outputFile = "src/test/resources/outputFile/json/output.json";
        final String expectedFile = "src/test/resources/expectedFile/json/input.json";


        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            xmlToJson.convert(fileInputStream, fileOutputStream);

            assertEquals(Files.readString(Path.of(expectedFile)), Files.readString(Path.of(outputFile)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
