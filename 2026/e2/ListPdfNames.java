package e2_4;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListPdfNames {

    public static void main(String[] args) {
        

        Path dir = Paths.get("data", "202309");
        

        if (!Files.isDirectory(dir)) {
            System.err.println("ディレクトリが見つかりません: " + dir.toAbsolutePath());
        }

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*.pdf")) {
            for (Path entry : ds) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
