package e2_4;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;

public class E2_4_2 {

    public static void main(String[] args) {
        Path path = Paths.get("data", "student.pdf");

        // PDFBox 3.x 推奨：Loader.loadPDF + try-with-resources
        try (PDDocument pdDoc = Loader.loadPDF(path.toFile())) {

            // ---- ① テキスト抽出（1ページのみ） ----
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(1);
            String parsedText = pdfStripper.getText(pdDoc);
            System.out.println(parsedText);

            // ---- ② AcroForm のフィールド出力 ----
            PDDocumentCatalog catalog = pdDoc.getDocumentCatalog();
            PDAcroForm acroForm = (catalog != null) ? catalog.getAcroForm() : null;

            if (acroForm == null) {
                System.out.println("[Info] このPDFにはAcroFormがありません。");
                return;
            }

            List<PDField> fields = acroForm.getFields();
            if (fields == null || fields.isEmpty()) {
                System.out.println("[Info] AcroFormはありますが、フィールドは空です。");
                return;
            }

            for (PDField field : fields) {
                // getFullyQualifiedName / getValueAsString は 3.x でも利用可
                System.out.print(field.getFullyQualifiedName() + ": ");
                System.out.println(field.getValueAsString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
