import java.util.Scanner;

public class AutoBoxSum {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Integer total = 0;  // Integer object used for autoboxing/unboxing
        String value;

        System.out.println("Enter numbers one by one (type 'stop' to end):");

        while (true) {
            value = input.nextLine();
            if (value.equalsIgnoreCase("stop")) {
                break;
            }
            try {
                total = total + Integer.parseInt(value);  // auto unboxing + boxing
            } catch (NumberFormatException ex) {
                System.out.println("⚠ Please enter a valid integer.");
            }
        }

        System.out.println("Final Sum = " + total);
        input.close();
    }
}
