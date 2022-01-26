package be.intecbrussel.app;

import java.nio.file.Path;
import java.nio.file.Paths;

import static be.intecbrussel.utilities.FileSortAndMove.sortAndMove;
import static be.intecbrussel.utilities.PathCollector.getPaths;
import static be.intecbrussel.utilities.SummaryMaker.createSummary;
import static be.intecbrussel.utilities.SummaryMaker.writeSummary;

public class FileSorterApp {

    public static void main(String[] args) {
        Path unsortedPath = Paths.get("unsorted");
        Path sortedPath = Paths.get("sorted");
        Path pathSummaryFile = sortedPath.resolve("summary/Summary.txt");

        System.out.println("Sorting started");

        sortAndMove(getPaths(unsortedPath), sortedPath);

        System.out.println("Files sorted :)");

        createSummary(pathSummaryFile);
        writeSummary(getPaths(sortedPath), pathSummaryFile);

        System.out.println("Summary is ready! :)");
    }
}
