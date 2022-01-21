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
    // Class variable for summary indentation
    private static int maxLength = 0;

    public static void main(String[] args) {
        Path unsortedPath = Paths.get("unsorted");
        Path sortedPath = Paths.get("sorted");
        Path pathSummaryFile = sortedPath.resolve("summary/Summary.txt");

        System.out.println("Sorting started");

        sortAndMove(getFilesInDirectory(unsortedPath), sortedPath);
        createSummary(pathSummaryFile);
        writeSummary(getFilesInDirectory(sortedPath), pathSummaryFile);
    }


    private static List<Path> getFilesInDirectory(Path path) {
        try {
            // gets all paths from files in directory
            Stream<Path> walk = Files.walk(path);
            List<Path> paths = walk.filter(e -> !Files.isDirectory(e))
                    .collect(Collectors.toList());
            return paths;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void sortAndMove(List<Path> paths, Path sortedFolder){
        try {
            for (Path p : paths) {
                // Converting path to string for extracting filemame and extension
                String filePath = p.toString();
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

                // create path to new directory
                Path newPath = sortedFolder.resolve(extension);
                // If a hidden file exsist -> create hidden directory
                if (Files.isHidden(Paths.get(filePath))) {
                    newPath = sortedFolder.resolve("hidden");
                }
                // Create directories if they don't exist yet
                if (Files.notExists(newPath)) {
                    Files.createDirectories(newPath);
                }
                // Move the files if not present in sorted directory
                if (Files.notExists(newPath.resolve(fileName))) {
                    Files.move(Paths.get(filePath), newPath.resolve(fileName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private static void writeSummary(List<Path> paths, Path pathSummary) {
        // Find greatest length of filename (used for proper indentaion)
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
            // Create proper indentation based on greatest length of filename
            String formatNumberHeader = "%-" + (maxLength + 5) + "s";
            String formatNumber = "%." + (maxLength + 5) + "s";

            // Construct header
            output.format(formatNumberHeader + "|       %s        |       %s      |\n" , "name", "readable", "writeable");

            // Help variables used to determin when directory changes
            String oldDir = "";
            // Help variables used for cleaner look
            String readableFile = "";
            String writableFile = "";
            for (Path p : paths){
                // Converting path to string for extracting filemame and directory name
                String filePath = p.toString();
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                String parentDirectories = p.getParent().toString();
                String directoryName = parentDirectories.substring(parentDirectories.lastIndexOf("\\") + 1);

                // Construct directory layout
                if (!directoryName.equals(oldDir)){
                    oldDir = directoryName;
                    output.format("\n");
                    output.format(formatNumber , "---------------------------------------------------------------------------------------------------------------------------------------------");
                    output.format("\n");
                    output.format(formatNumber, directoryName + ":");
                    output.format("\n");
                    output.format(formatNumber, "---------------------------------------------------------------------------------------------------------------------------------------------");
                    output.format("\n");
                    output.format("\n");
                }
                // Construct file layout and determin readable/writable
                readableFile = Files.isReadable(p) ? "X" : "/";
                writableFile = Files.isWritable(p) ? "X" : "/";
                output.format(formatNumberHeader + "|           %s           |          %s           |\n" , fileName, readableFile, writableFile);
            }
            // Write everything to summary.txt
            bufferedWriter.write(sb.toString());
            System.out.println("Files sorted :)");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
