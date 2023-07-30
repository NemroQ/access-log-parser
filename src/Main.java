import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Statistics stat = new Statistics();
        System.out.println("Укажите путь к файлу:");
        String path = new Scanner(System.in).nextLine();
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                int lenght = line.length();
                if (lenght > 1024) throw new ExceededStringLenghtException("Длина строки файла превышает 1024 символа");
                LogEntry logEntry = new LogEntry(line.trim());
                stat.addEntry(logEntry);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Указанный файл не существует");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Невозможно прочитать указанный файл");
            e.printStackTrace();
        }
        System.out.println("Общее количество траффика за час: " + stat.getTrafficRate());
    }
}