package lab5;

import java.io.*;

public class FileManipulator {
    private String filePath;

    public FileManipulator(String filePath) {
        this.filePath = filePath;
    }

    public String readTextFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    public void writeTextFile(String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(text);
            System.out.println("Text was saved " + filePath);
        }
    }

    public boolean fileExists() {
        return new File(filePath).exists();
    }
}
