import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число: ");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число: ");
        int secondNumber = new Scanner(System.in).nextInt();

        int sum = firstNumber + secondNumber;
        System.out.println("Сумма введённых числе: " + sum);

        int different = firstNumber - secondNumber;
        System.out.println("Разность введённых чисел: " + different);

        int produce = firstNumber * secondNumber;
        System.out.println("Произведение введённых чисел: " + produce);

        double quotient = (double) firstNumber / secondNumber;
        System.out.println("Частное введённых чисел: " + quotient);
    }
}