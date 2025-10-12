package lab5;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Task task = new Task();
        System.out.println("============MAX WORDS IN LINE=============");
        task.fieldWithMaxWords();
        System.out.println("============ENCRYPTER=============");
        task.encryptFile();
        System.out.println("============DECRYPTER=============");
        task.decryptFile();
        System.out.println("============TAGS COUNTER=============");
        task.tagsCounter();
    }


    public void fieldWithMaxWords() {
        try {
            System.out.print("Input filepath: ");
            String filePath = scanner.nextLine().trim();

            FileManipulator fm = new FileManipulator(filePath);

            if (!fm.fileExists()) {
                System.out.println("File doesn't exist");
                return;
            }

            String text = fm.readTextFile();

            if (text.isEmpty()) {
                System.out.println("File is empty");
                return;
            }

            String[] lines = text.split("\n");

            String maxLine = "";
            int maxWords = 0;

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] words = line.trim().split("\\s+");
                int count = words.length;

                if (count > maxWords) {
                    maxWords = count;
                    maxLine = line;
                }
            }

            if (maxWords > 0) {
                System.out.println("Words in the largest line: (" + maxWords + "):");
                System.out.println(maxLine);
            } else {
                System.out.println("No words in lines");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encryptFile() {
        try {
            System.out.print("Input filepath: ");
            String inputFile = scanner.nextLine().trim();

            FileManipulator fm = new FileManipulator(inputFile);
            if (!fm.fileExists()) {
                System.out.println("File doesn't exist");
                return;
            }

            System.out.print("Enter key(char): ");
            String keyInput = scanner.nextLine();

            if (keyInput.isEmpty()) {
                System.out.println("k can't be empty");
                return;
            }

            char key = keyInput.charAt(0);

            System.out.print("Output filepath: ");
            String outputFile = scanner.nextLine().trim();

            if (outputFile.isEmpty()) {
                System.out.println("File is empty");
                return;
            }

            Cipher.encryptFile(inputFile, outputFile, key);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decryptFile() {
        try {
            System.out.print("Input filepath: ");
            String inputFile = scanner.nextLine().trim();

            FileManipulator fm = new FileManipulator(inputFile);
            if (!fm.fileExists()) {
                System.out.println("File doesn't exist");
                return;
            }

            System.out.print("Enter key(char): ");
            String keyInput = scanner.nextLine();

            if (keyInput.isEmpty()) {
                System.out.println("k can't be empty");
                return;
            }

            char key = keyInput.charAt(0);

            System.out.print("Output filepath: ");
            String outputFile = scanner.nextLine().trim();

            if (outputFile.isEmpty()) {
                System.out.println("File is empty");
                return;
            }

            Cipher.decryptFile(inputFile, outputFile, key);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void tagsCounter() {
        BufferedReader in = null;
        try {
            System.out.print("URL: ");
            String urlString = scanner.nextLine().trim();

            if (urlString.isEmpty()) {
                System.out.println("Url is empty");
                return;
            }

            if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
                urlString = "https://" + urlString;
            }

            URL url = new URL(urlString);
            in = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder html = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }

            Pattern tagPattern = Pattern.compile("<\\s*([a-zA-Z0-9]+)");
            Matcher matcher = tagPattern.matcher(html.toString());

            Map<String, Integer> tagCount = new HashMap<>();
            while (matcher.find()) {
                String tag = matcher.group(1).toLowerCase();
                tagCount.put(tag, tagCount.getOrDefault(tag, 0) + 1);
            }

            if (tagCount.isEmpty()) {
                System.out.println("No tags on page");
                return;
            }

            System.out.println("\n=== Теги у лексикографічному порядку ===");
            tagCount.keySet().stream()
                    .sorted()
                    .forEach(tag -> System.out.printf("%-15s -> %d%n", tag, tagCount.get(tag)));

            System.out.println("\n=== Теги за частотою (зростання) ===");
            tagCount.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(e -> System.out.printf("%-15s -> %d%n", e.getKey(), e.getValue()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}