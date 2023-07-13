import java.io.*;
import java.util.Objects;
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
            int yBot = 0;
            int gBot = 0;
            while ((line = reader.readLine()) != null) {
                int lenght = line.length();
                if (lenght > 1024) throw new ExceededStringLenghtException("Длина строки файла превышает 1024 символа");
                counter++;
                String[] userAgent = line.split(" \"");
                String[] str = userAgent[userAgent.length - 1].split("\\(");
                String firstBrackets = "";
                if (str.length >= 2) {
                    firstBrackets = str[str.length - 1];
                }
                String[] parts = firstBrackets.split(";");
                String[] fragment = new String[0];
                if (parts.length >= 2) {
                    fragment = parts[1].split("/");
                }
                String bots = null;
                if (fragment.length >= 2) {
                    bots = fragment[0].trim();
                }
                if (Objects.equals(bots, "YandexBot")) {
                    yBot++;
                }
                if (Objects.equals(bots, "Googlebot")) {
                    gBot++;
                }
            }
            System.out.println("Общее количество строк в файле: " + counter);
            System.out.println("Количество поисковых запросов от YandexBot: " + yBot);
            System.out.println("Количество поисковых запросов от GoogleBot: " + gBot);
        } catch (FileNotFoundException e) {
            System.out.println("Указанный файл не существует");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Невозможно прочитать указанный файл");
            e.printStackTrace();
        }
    }
}