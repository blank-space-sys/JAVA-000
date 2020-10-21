package com.algorithm.practicing.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MyClassLoder extends ClassLoader {
    String fileUrl = "D:\\Hello\\Hello.xlass";

    public static void main(String[] args) {
        try {
            Object object = new MyClassLoder().findClass("Hello").newInstance();
            Class<?> hello = object.getClass();
            hello.getDeclaredMethod("hello", null).invoke(object);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(fileUrl);
        byte[] result = new byte[(int) file.length()];
        if (file.isFile()) {
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = fin.read(buf)) != -1) {
                    for (int i = 0; i < len; i++) {
                        result[i] = (byte) (255 - buf[i]);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return defineClass(name, result, 0, result.length);
    }
}
