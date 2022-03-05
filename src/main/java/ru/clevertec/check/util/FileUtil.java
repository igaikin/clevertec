package ru.clevertec.check.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static String[] readOrderArguments(String path) {
        try {
            String str = new String(Files.readAllBytes(Paths.get(path)));
            return str.split(" ");
        } catch (IOException e) {
            e.printStackTrace();//FIXME logger!
            return new String[0];
        }
    }

    public static void writeCheckInFile(String content, String target) {
        try {
            Path path = Paths.get(target);
            createIfNotExists(path);
            Files.write(Paths.get(target), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();//FIXME logger!
        }
    }

    private static void createIfNotExists(Path path) {
        try {
            if (Files.notExists(path.getParent())) {
                Files.createDirectory(path.getParent());
            }
        } catch (IOException e) {
            e.printStackTrace();//FIXME logger!
        }
    }
}
