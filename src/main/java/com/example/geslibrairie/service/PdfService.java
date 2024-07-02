package com.example.geslibrairie.service;

import com.example.geslibrairie.model.Pret;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public byte[] generatePretReceipt(Pret pret) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Formatter pour la date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(100, 750);
                contentStream.showText("Reçu de Prêt");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Pret : " + pret.getIdpret());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Info Membre : " + pret.getMembre().getIdpers());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Nom: " + pret.getMembre().getNom());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Sexe: " + pret.getMembre().getSexe());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Age: " + pret.getMembre().getAge());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Contact: " + pret.getMembre().getContact());
                contentStream.endText();

                float margin = 100;
                float yStart = 540;
                float tableWidth = 400;
                float yPosition = yStart;
                float rowHeight = 20;
                float tableBottomY = yPosition - (rowHeight * 2);

                float[] colWidths = {80, 220, 100};
                float tableHeight = rowHeight * 2;

                contentStream.setLineWidth(1f);

                for (int i = 0; i <= 2; i++) {
                    contentStream.moveTo(margin, yPosition);
                    contentStream.lineTo(margin + tableWidth, yPosition);
                    contentStream.stroke();
                    yPosition -= rowHeight;
                }

                yPosition = yStart;
                float nextX = margin;
                for (int i = 0; i < colWidths.length; i++) {
                    contentStream.moveTo(nextX, yStart);
                    contentStream.lineTo(nextX, tableBottomY);
                    contentStream.stroke();
                    nextX += colWidths[i];
                }
                contentStream.moveTo(nextX, yStart);
                contentStream.lineTo(nextX, tableBottomY);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(margin + 5, yStart - 15);
                contentStream.showText("Code livre");
                contentStream.newLineAtOffset(colWidths[0], 0);
                contentStream.showText("Intitulé");
                contentStream.newLineAtOffset(colWidths[1], 0);
                contentStream.showText("Nombre prêté");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(margin + 5, yStart - 35);
                contentStream.showText(pret.getLivre().getIdlivre().toString());
                contentStream.newLineAtOffset(colWidths[0], 0);
                contentStream.showText(pret.getLivre().getDesignation());
                contentStream.newLineAtOffset(colWidths[1], 0);
                contentStream.showText("1");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, tableBottomY - 40);
                contentStream.showText("Prêté le : " + pret.getDatepret().format(dateFormatter));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Doit être rendu le : " + pret.getDaterendu().format(dateFormatter));
                contentStream.endText();
            }

            document.save(byteArrayOutputStream);
        }

        return byteArrayOutputStream.toByteArray();
    }
}
