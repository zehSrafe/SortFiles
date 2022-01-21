package be.intecbrussel;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetFileExtensionDemo {
    private static int maxLength = 0;
    // FOLDERS
    // FILES

    public static void main(String[] args) {
        Path path = Paths.get("unsorted");
        Path destination = Paths.get("sorted");
        Path pathSummary = destination.resolve("Summary/Summary.txt");

        try {
            sortAndCopy(getFilesInDirectory(path), destination);
            createSummary(pathSummary);
            writeSummary(getFilesInDirectory(destination), pathSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<Path> getFilesInDirectory(Path path) {
        try {
            Stream<Path> walk = Files.walk(path);
            List<Path> paths = walk.filter(e -> !Files.isDirectory(e))
                    .collect(Collectors.toList());
            return paths;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private static void writeSummary(List<Path> paths, Path pathSummary){
        for (Path p : paths) {
            String filePath = p.toString();
            String fileName = filePath.substring(
                    filePath.lastIndexOf("\\") + 1);

            if (fileName.length() > maxLength) {
                maxLength = fileName.length();
            }
        }

        try (FileWriter fileWriter = new FileWriter(pathSummary.toFile());
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            StringBuilder sb = new StringBuilder();
            Formatter output = new Formatter(sb);
            String formatNumberHeader = "%-" + (maxLength + 5) + "s";
            String formatNumber = "%." + (maxLength + 5) + "s";

            output.format(formatNumberHeader + "| %s | %s |\n" , "name", "       readable       ","       writeable       ");

            String oldDir = "";
            String readableFile = "";
            String writableFile = "";

            for (Path p : paths){
                String filePath = p.toString();
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                String parentDirectories = p.getParent().toString();
                String directoryName = parentDirectories.substring(parentDirectories.lastIndexOf("\\") + 1);

                if (!directoryName.equals(oldDir)){
                    oldDir = directoryName;
                    output.format(formatNumber , "---------------------------------------------------------------------------------------------------------------------------------------------");
                    output.format("\n");
                    output.format(formatNumber, directoryName + ":");
                    output.format("\n");
                    output.format(formatNumber, "---------------------------------------------------------------------------------------------------------------------------------------------");
                    output.format("\n");

                }
                readableFile = Files.isReadable(p) ? "X" : "/";
                writableFile = Files.isWritable(p) ? "X" : "/";
                output.format(formatNumberHeader + "|           %s           |          %s           |\n" , fileName, readableFile, writableFile);
                System.out.println(p);
            }
            bufferedWriter.write(sb.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sortAndCopy(List<Path> paths,
                                   Path sortedFolder) throws IOException {
        for (Path p : paths) {
//            System.out.println(p);
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
                Files.move(Paths.get(filePath), newPath.resolve(fileName));
            }
        }
    }
}
