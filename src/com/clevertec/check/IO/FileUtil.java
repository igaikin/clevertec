package com.clevertec.check.IO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static String[] readArguments(String path) {
        try {
            String str = new String(Files.readAllBytes(Paths.get(path)));
            return str.split(" ");
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public static boolean writeFile(String content, String target) {
        try {
            Path path = Paths.get(target);
            createIfNotExists(path);
            Files.write(Paths.get(target), content.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createIfNotExists(Path path) {
        try {
            if (Files.notExists(path.getParent())) {
                Files.createDirectory(path.getParent());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
