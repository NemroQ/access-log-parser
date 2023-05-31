import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int counter = 0;
        File file = getFilePath();
        boolean fileExists = file.exists();
        boolean isDirectory = file.isDirectory();
        while (true) {
            if (!fileExists) {
                System.out.println("По указанному пути: \"" + file.getPath() + "\" файл не существует");
                file = getFilePath();
                fileExists = file.exists();
                isDirectory = file.isDirectory();
                continue;
            }
            if (isDirectory) {
                System.out.println("В указанном пути: \"" + file.getPath() + "\" указан путь к директории, а не к файлу");
                file = getFilePath();
                fileExists = file.exists();
                isDirectory = file.isDirectory();
                continue;
            }
            counter++;
            System.out.println("Указан путь к файлу: \"" + file.getPath() + "\". Это файл номер " + counter);
            file = getFilePath();
            fileExists = file.exists();
            isDirectory = file.isDirectory();
        }

    }

    public static File getFilePath() {
        System.out.println("Укажите путь к файлу:");
        String path = new Scanner(System.in).nextLine();
        return new File(path);
    }
}