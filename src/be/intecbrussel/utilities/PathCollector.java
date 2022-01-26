package be.intecbrussel.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathCollector {
    private static List<Path> paths = new ArrayList<>();

    // collects all paths from files in source directory
    public static void collectPaths(Path path) {
        try {
            paths = Files.walk(path)
                    .filter(e -> !Files.isDirectory(e))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Path> getPaths(Path path) {
        collectPaths(path);
        return paths;
    }
}
