package lab10.task_2;

import java.io.PrintWriter;

import static lab10.task_2.AdvancedCipher.*;

public class DemoAdvancedCipherWork {
    public static void main(String[] args) {
        try {
            String testFile = "/home/mn33101/Desktop/KPI/java_subj/labs/src/main/resources/lab5/file1.txt";
            String encryptedFile = "/home/mn33101/Desktop/KPI/java_subj/labs/src/main/resources/lab5/cryptedFile.txt";
            String decryptedFile = "/home/mn33101/Desktop/KPI/java_subj/labs/src/main/resources/lab5/decryptedFile";
            char key = 5;

            try (PrintWriter writer = new PrintWriter(testFile)) {
                writer.println("In the heart of the old city,\n" +
                        "tucked between a bakery and an antique shop,\n" +
                        "stood a library that time seemed to have overlooked.\n" +
                        "Its wooden doors, worn smooth by countless hands,\n" +
                        "opened with a familiar creak that echoed through the dusty halls.");
            }
            encryptFile(testFile, encryptedFile, key);
            decryptFile(encryptedFile, decryptedFile, key);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
