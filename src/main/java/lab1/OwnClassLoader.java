package lab1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class OwnClassLoader extends ClassLoader {

    private final String classPath;

    public OwnClassLoader(String classPath, ClassLoader parent) {
        super(parent);
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = loadClassBytes(name);
        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(String className) throws ClassNotFoundException {
        String fileName = className.replace('.', File.separatorChar) + ".class";
        try {
            FileInputStream fis = new FileInputStream(classPath + File.separator + fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int b;
            while ((b = fis.read()) != -1) {
                bos.write(b);
            }
            fis.close();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException("Class '" + className + "' not found.", e);
        }
    }

}
