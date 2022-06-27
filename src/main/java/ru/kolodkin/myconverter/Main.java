package ru.kolodkin.myconverter;

import lombok.val;
import ru.kolodkin.myconverter.factory.ConverterFactory;
import ru.kolodkin.myconverter.factory.ConverterType;
import ru.kolodkin.myconverter.tool.Extension;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (Extension.checkExtension(args)) {
            System.out.println("� ������������ �� � �������.");

            try (val inputStream = new FileInputStream(args[1]);
                 val outputStream = new FileOutputStream(args[2])) {

                new ConverterFactory().createConverter(ConverterType
                        .valueOf(args[0])).convert(inputStream, outputStream);

                System.out.println("����������� ������ �������.");
            } catch (FileNotFoundException exception) {
                System.out.println("���� �� ������...");
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            } catch (IllegalArgumentException exception) {
                System.out.println("������������ ���� ������� ������");
            } catch (JAXBException e) {
                System.out.println("������ ��� ��������");
            } catch (Exception e) {
                System.out.println("�������������� ������ " + e);
            }
        }
    }
}
