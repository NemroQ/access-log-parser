import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Укажите путь к файлу:");
        String path = new Scanner(System.in).nextLine();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            int counter = 0;
            int shortLine = 1024;
            int longLine = 0;
            while ((line = reader.readLine()) != null) {
                int lenght = line.length();
                if (lenght > 1024) throw new ExceededStringLenghtException("Длина строки файла превышает 1024 символа");
                if (lenght < shortLine) shortLine = lenght;
                if (lenght > longLine) longLine = lenght;
                counter++;
            }
            System.out.println("Общее количество строк в файле: " + counter);
            System.out.println("Длина самой короткой строки: " + shortLine);
            System.out.println("Длина самой длинной строки: " + longLine);
        } catch (FileNotFoundException e) {
            System.out.println("Указанный файл не существует");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Невозможно прочитать указанный файл");
            e.printStackTrace();
        }
    }
}