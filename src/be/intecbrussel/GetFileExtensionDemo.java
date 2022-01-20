package be.intecbrussel;


import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GetFileExtensionDemo {

    public static void main(String[] args) {
        Path path = Paths.get("unsorted");

        createDirectories(getFileExtentions(path));
    }

    private static Set<String> getFileExtentions(Path path) {
        try {
            Set<String> fileExtensions = new HashSet<>();
            int indexDot;
            Path[] colectedFiles = Files.walk(path)
                    .filter(s -> s.toString().contains("."))
                    .filter(s -> !s.startsWith("."))
                    .toArray(Path[]::new);
            for (Path c : colectedFiles){
                indexDot = c.toString().lastIndexOf('.');
                fileExtensions.add(c.toString().substring(indexDot + 1));
            }
            return fileExtensions;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    private static void createDirectories(Set<String> fileExtensions){
        try {
            for (String s : fileExtensions){
                Files.createDirectories(Path.of("unsorted/createFolders/" + s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
