package e2_4;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class E2_4_1 {

    public static void main(String[] args) {
        Path path = Paths.get("data", "student.pdf");

        try (PDDocument pdDoc = Loader.loadPDF(path.toFile())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // 1ページ目のみ抽出（PDFBoxは1始まりの指定でOK）
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(1);

            String parsedText = pdfStripper.getText(pdDoc);
            System.out.println(parsedText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}