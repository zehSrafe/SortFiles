package be.intecbrussel.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileSortAndMove {

    public static void sortAndMove(List<Path> paths, Path sortedFolder) {
        try {
            for (Path p : paths) {
                // convert path to string for extracting filename & extension
                String filePath = p.toString();
                String fileName =
                        filePath.substring(filePath.lastIndexOf("\\") + 1);
                String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

                // create path to new directory
                Path newPath = sortedFolder.resolve(extension);

                // if a file is hidden -> create 'hidden' directory
                if (Files.isHidden(Paths.get(filePath))) {
                    newPath = sortedFolder.resolve("hidden");
                }
                // create directories if not yet existing
                if (Files.notExists(newPath)) {
                    Files.createDirectories(newPath);
                }
                // move files to new directory if not yet present
                if (Files.notExists(newPath.resolve(fileName))) {
                    Files.move(Paths.get(filePath), newPath.resolve(fileName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
