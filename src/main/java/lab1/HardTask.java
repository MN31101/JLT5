package lab1;

import java.io.File;
import java.io.IOException;

public class HardTask {
    public static void main(String[] args) {

        String classPath = "/home/mn33101/Desktop/KPI/java_subj/labs/src/main/java/";
        File sourceFileTM = new File(classPath, "lab1/TestModule.java");

        long lastModified = 0;
        while (true) {
            if (sourceFileTM.lastModified() > lastModified) {
                try {
                    Process p = Runtime.getRuntime().exec("javac " + sourceFileTM.getAbsolutePath());
                    p.waitFor();
                    lastModified = sourceFileTM.lastModified();

                    OwnClassLoader ocl = new OwnClassLoader(classPath, null);
                    Class<?> myClass = ocl.loadClass("lab1.TestModule");
                    Object t = myClass.getDeclaredConstructor().newInstance();
                    System.out.println(t);
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
