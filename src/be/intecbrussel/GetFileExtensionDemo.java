package be.intecbrussel;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetFileExtensionDemo {

    public static void main(String[] args) {
        Path path = Paths.get("unsorted");

        System.out.println(Files.walk(path));
//        createDirectories(path);

    }

    private static void fileReader() {


    }

    private static void createDirectories(Path path){
        String[] fileList = path.toFile().list();
        String fileExtensions;
        for (String s : fileList) {
            if (s.contains(".") && !s.startsWith(".")) {
//                int indexDot = s.charAt('.'); // not working but why?
                int indexDot = s.lastIndexOf('.');
                fileExtensions = s.substring(indexDot + 1);
                try {
                    Files.createDirectories(Path.of(Paths.get("unsorted/createFolders/") + fileExtensions));
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

//    private static ArrayList<String> getExtensionFile(Path path) {

//        return fileExtensions;
//        System.out.println(fileExtensions);
//        System.out.println(Arrays.toString(fileList));
//    }
}
