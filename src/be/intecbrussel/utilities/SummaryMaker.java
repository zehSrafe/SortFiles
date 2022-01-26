package be.intecbrussel.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.List;

public class SummaryMaker {
    private static int maxLength = 0; // used for checking maxLength filename

    // creates summary directory and summary file
    public static void createSummary(Path pathSummary) {
        try {
            Files.createDirectories(pathSummary.getParent());
            if (!Files.exists(pathSummary)) {
                Files.createFile(pathSummary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSummary(List<Path> paths, Path pathSummary) {
        setMaxLength(paths);

        try (FileWriter fileWriter = new FileWriter(pathSummary.toFile());
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            StringBuilder sb = new StringBuilder();
            Formatter output = new Formatter(sb);
            // create proper indentation based on greatest length of filename
            String formatNumberHeader = "%-" + (maxLength + 5) + "s";
            String formatNumber = "%." + (maxLength + 5) + "s";

            // construct header
            output.format(formatNumberHeader + "|       %s        |       %s      |\n" , "name", "readable", "writeable");

            // help variable used to determine when directory changes
            String oldDir = "";
            // help variables used for cleaner look
            String readableFile;
            String writableFile;
            for (Path p : paths) {
                // convert path to string for extract file- & directory name
                String filePath = p.toString();
                String fileName =
                        filePath.substring(filePath.lastIndexOf("/") + 1);
                String parentDirectories = p.getParent().toString();
                String directoryName =
                        parentDirectories.substring(parentDirectories.lastIndexOf("/") + 1);

                // construct print layout directory line
                if (!directoryName.equals(oldDir)) {
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
                // construct print layout file line & check readable/writable
                readableFile = Files.isReadable(p) ? "X" : "/";
                writableFile = Files.isWritable(p) ? "X" : "/";
                output.format(formatNumberHeader + "|           %s           |          %s           |\n" , fileName, readableFile, writableFile);
            }
            // write to summary.txt
            bufferedWriter.write(sb.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // find max length of filename (used for proper indentation)
    private static void setMaxLength(List<Path> paths) {
        for (Path p : paths) {
            String filePath = p.toString();
            String fileName = filePath.substring(
                    filePath.lastIndexOf("\\") + 1);

            if (fileName.length() > maxLength) {
                maxLength = fileName.length();
            }
        }
    }
}
