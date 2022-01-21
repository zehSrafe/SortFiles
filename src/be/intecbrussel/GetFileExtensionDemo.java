package be.intecbrussel;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GetFileExtensionDemo {

    public static void main(String[] args) {
        Path path = Paths.get("unsorted");
        Path destination = Paths.get("sorted");

        try {
            Path[] files = Files.walk(path)
                    .toArray(Path[]::new);
            sortAndCopy(files, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }



//        createDirectories(getFileExtentions(path));
//        moveFiles(path);

    }

//    private static void moveFiles(Path path) {
//        try {
//            Path[] files = Files.walk(path)
//                    .toArray(Path[]::new);
//            for (Path p : files) {
//                Files.move(p, )
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static Set<String> getFileExtentions(Path path) {
//        try {
//            Set<String> fileExtensions = new HashSet<>();
//            int indexDot;
//            Path[] colectedFiles = Files.walk(path)
//                    .filter(s -> s.toString().contains("."))
//                    .filter(s -> !s.startsWith("."))
//                    .toArray(Path[]::new);
//            for (Path c : colectedFiles) {
//                indexDot = c.toString().lastIndexOf('.');
//                fileExtensions.add(c.toString().substring(indexDot + 1));
//            }
//            return fileExtensions;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public static void sortAndCopy(Object[] paths,
                                   Path sorted_folder) throws IOException {

        for (Object p : paths) {

            String filePath = p.toString();
            String fileName = filePath.substring(
                    filePath.lastIndexOf("//") + 1);
            String extension = filePath.substring(
                    filePath.lastIndexOf(".") + 1);
            Path newPath = sorted_folder.resolve(extension);
            if (Files.isHidden(Paths.get(filePath))) {
                newPath = sorted_folder.resolve("hidden");
            }
            System.out.println(newPath);
            if (Files.notExists(newPath)) {
                Files.createDirectories(newPath);
            }
            if (Files.notExists(newPath.resolve(fileName))) {
                Files.move(Paths.get(filePath), newPath.resolve(fileName),
                           REPLACE_EXISTING);
            }
        }
    }


//    private static void createDirectories(Set<String> fileExtensions) {
//        try {
//            for (String s : fileExtensions) {
//                Files.createDirectories(Path.of("unsorted/createFolders/" + s));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
