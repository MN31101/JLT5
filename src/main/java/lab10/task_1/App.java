package lab10.task_1;

import java.lang.reflect.Field;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // literal
        MyString s = new MyString("hi");
        System.out.println("Before: " + s);
        changeStringValue(s, "gb");
        System.out.println("After: " + s);

        // from terminal
        System.out.print("\nEnter string: ");
        String input = sc.nextLine();
        MyString userS = new MyString(input);

        System.out.print("Enter new value: ");
        String userNewS = sc.nextLine();

        System.out.println("Before: " + userS);
        changeStringValue(userS, userNewS);
        System.out.println("After: " + userS);

        sc.close();
    }
     private static void changeStringValue(MyString oldValue, String newValue) throws Exception{
         Field field = MyString.class.getDeclaredField("value");
         field.setAccessible(true);

         byte[] oldBytes = (byte[]) field.get(oldValue);
         byte[] newBytes = newValue.getBytes();

         System.arraycopy(newBytes, 0, oldBytes, 0, Math.min(newBytes.length, oldBytes.length));
     }

}
