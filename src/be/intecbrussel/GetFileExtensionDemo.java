package be.intecbrussel;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GetFileExtensionDemo {

    public static void main(String[] args) {
        Path path = Paths.get("unsorted");
        Path destination = Paths.get("sorted");
        Path pathSummary = destination.resolve("Summary/Summary.txt");

        try {
            Object[] paths = Files.walk(path)
                    .filter(e -> !Files.isDirectory(e))
                    .toArray();
            sortAndCopy(paths, destination);
            createSummary(pathSummary);
            writeSummary(pathSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }



//        createDirectories(getFileExtentions(path));
//        moveFiles(path);

    }

    private static void createSummary(Path pathSummary){
        try {
            Files.createDirectories(pathSummary.getParent());
            if (!Files.exists(pathSummary)){
                Files.createFile(pathSummary);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void writeSummary(Path path){
        try (FileWriter fileWriter = new FileWriter(path.toFile(), true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            StringBuilder sb = new StringBuilder();
            Formatter output = new Formatter(sb);
            output.format("%-15s %-15s %-15s %2f\n" , "tes4455454t", "test","test", 0.420);
            bufferedWriter.write(sb.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
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
                                   Path sortedFolder) throws IOException {
        int counter = 0;
        for (Object p : paths) {
            String filePath = p.toString();
            String fileName = filePath.substring(
                    filePath.lastIndexOf("\\") + 1);
            String extension = filePath.substring(
                    filePath.lastIndexOf(".") + 1);
            Path newPath = sortedFolder.resolve(extension);
            if (Files.isHidden(Paths.get(filePath))) {
                newPath = sortedFolder.resolve("hidden");
            }
            if (Files.notExists(newPath)) {
                Files.createDirectories(newPath);
            }
            if (Files.notExists(newPath.resolve(fileName))) {
//                do {
//                    counter ++;
//                } while (Files.exists(newPath.resolve(fileName)));
//                Files.move(Paths.get(filePath), newPath.resolve(fileName + "(" + counter + ")"));
//            } else {
                Files.move(Paths.get(filePath), newPath.resolve(fileName));
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
