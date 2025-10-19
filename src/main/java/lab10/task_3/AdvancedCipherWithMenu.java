package lab10.task_3;

import java.io.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.*;

public class AdvancedCipherWithMenu {
    private static final Logger logger = Logger.getLogger(AdvancedCipherWithMenu.class.getName());
    private static ResourceBundle messages;
    private static Locale currentLocale;
    private static final Scanner scanner = new Scanner(System.in);

    static {
        try {
            logger.setUseParentHandlers(false);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.WARNING);
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("/home/mn33101/Desktop/KPI/java_subj/labs/src/main/resources/lab10/task_3/cipher_log.txt", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);

            currentLocale = new Locale("uk");
            messages = ResourceBundle.getBundle("lab10.location.messages", currentLocale);

            logger.info("Система логування ініціалізована");

        } catch (IOException e) {
            System.err.println("Помилка налаштування логування: " + e.getMessage());
        }
    }
    public static void encryptFile(String inputFile, String outputFile, char key) throws IOException {
        logger.info("Початок шифрування файлу: " + inputFile);
        logger.fine("Параметри: вхідний=" + inputFile + ", вихідний=" + outputFile + ", ключ=" + (int)key);

        try (FilterInputStream fis = new BufferedInputStream(new FileInputStream(inputFile));
             FilterOutputStream fos = new BufferedOutputStream(new FileOutputStream(outputFile))) {

            int bytesProcessed = 0;
            int b;

            while ((b = fis.read()) != -1) {
                fos.write(b + key);
                bytesProcessed++;

                if (bytesProcessed % 100 == 0) {
                    logger.fine("Оброблено байтів: " + bytesProcessed);
                }
            }

            logger.info("Файл зашифровано: " + outputFile + " (" + bytesProcessed + " байт)");
            logger.warning("Використано Caesar cipher!");

        } catch (FileNotFoundException e) {
            logger.severe("Файл не знайдено: " + inputFile);
            throw e;
        } catch (IOException e) {
            logger.severe("Помилка I/O: " + e.getMessage());
            throw e;
        }
    }

    public static void decryptFile(String inputFile, String outputFile, char key) throws IOException {
        logger.info("Початок дешифрування файлу: " + inputFile);
        logger.fine("Параметри: вхідний=" + inputFile + ", вихідний=" + outputFile + ", ключ=" + (int)key);

        try (FilterInputStream fis = new BufferedInputStream(new FileInputStream(inputFile));
             FilterOutputStream fos = new BufferedOutputStream(new FileOutputStream(outputFile))) {

            int bytesProcessed = 0;
            int b;

            while ((b = fis.read()) != -1) {
                fos.write(b - key);
                bytesProcessed++;

                if (bytesProcessed % 100 == 0) {
                    logger.fine("Оброблено байтів: " + bytesProcessed);
                }
            }

            logger.info("Файл дешифровано: " + outputFile + " (" + bytesProcessed + " байт)");

            if (bytesProcessed < 50) {
                logger.warning("Малий файл (" + bytesProcessed + " байт)");
            }

        } catch (FileNotFoundException e) {
            logger.severe("Файл не знайдено: " + inputFile);
            throw e;
        } catch (IOException e) {
            logger.severe("Помилка I/O: " + e.getMessage());
            throw e;
        }
    }
    public static void runProgram() {
        logger.info(messages.getString("info.program.start"));

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    encryptFileMenu();
                    break;
                case 2:
                    decryptFileMenu();
                    break;
                case 3:
                    changeLanguage();
                    break;
                case 4:
                    running = false;
                    System.out.println(messages.getString("goodbye"));
                    logger.info(messages.getString("info.program.end"));
                    break;
                default:
                    System.out.println(messages.getString("menu.invalid"));
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(messages.getString("menu.title"));
        System.out.println("=".repeat(50));
        System.out.println("1. " + messages.getString("menu.encrypt"));
        System.out.println("2. " + messages.getString("menu.decrypt"));
        System.out.println("3. " + messages.getString("menu.language"));
        System.out.println("4. " + messages.getString("menu.exit"));
        System.out.println("=".repeat(50));
        System.out.print(messages.getString("menu.choice"));
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void encryptFileMenu() {
        try {
            System.out.print(messages.getString("input.file"));
            String inputFile = scanner.nextLine();

            System.out.print(messages.getString("output.file"));
            String outputFile = scanner.nextLine();

            System.out.print(messages.getString("input.key"));
            char key = scanner.nextLine().charAt(0);

            encryptFile(inputFile, outputFile, key);
            System.out.println(MessageFormat.format(
                    messages.getString("success.encrypt"), outputFile));

        } catch (Exception e) {
            System.err.println(MessageFormat.format(
                    messages.getString("error.general"), e.getMessage()));
            logger.severe("Помилка шифрування: " + e.getMessage());
        }
    }

    private static void decryptFileMenu() {
        try {
            System.out.print(messages.getString("input.file"));
            String inputFile = scanner.nextLine();

            System.out.print(messages.getString("output.file"));
            String outputFile = scanner.nextLine();

            System.out.print(messages.getString("input.key"));
            char key = scanner.nextLine().charAt(0);

            decryptFile(inputFile, outputFile, key);
            System.out.println(MessageFormat.format(
                    messages.getString("success.decrypt"), outputFile));

        } catch (Exception e) {
            System.err.println(MessageFormat.format(
                    messages.getString("error.general"), e.getMessage()));
            logger.severe("Помилка дешифрування: " + e.getMessage());
        }
    }

    private static void changeLanguage() {
        System.out.println("\n--- Available Languages ---");
        System.out.println("1. Українська (uk)");
        System.out.println("2. English (en)");
        System.out.print("Choose language: ");

        int choice = getUserChoice();
        String langCode;

        switch (choice) {
            case 1:
                langCode = "_uk";
                currentLocale = new Locale("uk");
                break;
            case 2:
                langCode = "_en";
                currentLocale = new Locale("en");
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }
        messages = ResourceBundle.getBundle("lab10.location.messages", currentLocale);

        System.out.println(MessageFormat.format(
                messages.getString("success.language"),
                currentLocale.getDisplayLanguage()));

        logger.info("Мову змінено на: " + currentLocale.getDisplayLanguage());

        try {

            messages = ResourceBundle.getBundle("lab10.location.messages" + langCode, currentLocale);

            System.out.println(MessageFormat.format(
                    messages.getString("success.language"),
                    choice == 1 ? "Українська" : "English"));
            logger.info("Мову змінено на: " + (choice == 1 ? "Українська" : "English"));

        } catch (MissingResourceException e) {
            System.err.println("Помилка завантаження мовного файлу: " + e.getMessage());
            logger.severe("Не вдалося завантажити мову: " + langCode);
        }
    }
}
