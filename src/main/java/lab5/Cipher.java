package lab5;

import java.io.*;
public class Cipher {

    public static void encryptFile(String inputFile, String outputFile, char key) throws IOException {
        try (FilterInputStream fis = new BufferedInputStream(new FileInputStream(inputFile));
             FilterOutputStream fos = new BufferedOutputStream(new FileOutputStream(outputFile))) {

            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b + key);
            }
            System.out.println("File encrypted: " + outputFile);
        }
    }

    public static void decryptFile(String inputFile, String outputFile, char key) throws IOException {
        try (FilterInputStream fis = new BufferedInputStream(new FileInputStream(inputFile));
             FilterOutputStream fos = new BufferedOutputStream(new FileOutputStream(outputFile))) {

            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b - key);
            }
            System.out.println("File decrypted: " + outputFile);
        }
    }
}