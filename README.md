## ConverterService
Сервис конвертирует данные из файла с расширением xml в json и наоборот.
### Входные данные
Первым аргументов входных данных будет выбор вида преобразования данных:
1. XML2JSON
2. JSON2XML

Вторым аргументом может быть или xml, или json файл.

Выбор расширения второго аргумента, исключает выбор этого расширения в третьем аргументе. 

Пример: 
1. XML2JSON input.xml output.json 
2. JSON2XML output.json input.xml
