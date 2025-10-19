package lab10.task_2;

import java.io.*;
import java.util.logging.*;

public class AdvancedCipher {
    private static final Logger logger = Logger.getLogger(AdvancedCipher.class.getName());

    static {
        try {
            Logger rootLogger = Logger.getLogger("");
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.WARNING);
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler("/home/mn33101/Desktop/KPI/java_subj/labs/src/main/resources/lab10/task_2/cipher_log.txt", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);

            logger.info("Система логування ініціалізована");

        } catch (IOException e) {
            System.err.println("Помилка налаштування логування: " + e.getMessage());
        }
    }


    public static void encryptFile(String inputFile, String outputFile, char key) throws IOException {
        logger.info("Початок шифрування файлу: " + inputFile);
        logger.fine("Параметри: вхідний файл=" + inputFile + ", вихідний файл=" + outputFile + ", ключ=" + (int)key);

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

            logger.info("Файл успішно зашифровано: " + outputFile);
            logger.info("Всього оброблено байтів: " + bytesProcessed);
            System.out.println("File encrypted: " + outputFile);

        } catch (FileNotFoundException e) {
            logger.severe("Файл не знайдено: " + inputFile);
            throw e;
        } catch (IOException e) {
            logger.severe("Помилка вводу-виводу під час шифрування: " + e.getMessage());
            throw e;
        }
    }

    public static void decryptFile(String inputFile, String outputFile, char key) throws IOException {
        logger.info("Початок дешифрування файлу: " + inputFile);
        logger.fine("Параметри: вхідний файл=" + inputFile + ", вихідний файл=" + outputFile + ", ключ=" + (int)key);

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

            logger.info("Файл успішно дешифровано: " + outputFile);
            logger.info("Всього оброблено байтів: " + bytesProcessed);
            System.out.println("File decrypted: " + outputFile);

        } catch (FileNotFoundException e) {
            logger.severe("Файл не знайдено: " + inputFile);
            throw e;
        } catch (IOException e) {
            logger.severe("Помилка вводу-виводу під час дешифрування: " + e.getMessage());
            throw e;
        }
    }
}
