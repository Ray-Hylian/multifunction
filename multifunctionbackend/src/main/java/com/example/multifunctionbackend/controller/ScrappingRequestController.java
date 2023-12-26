package com.example.multifunctionbackend.controller;

import com.example.multifunctionbackend.entities.ScrappingRequest;
import com.example.multifunctionbackend.repository.ScrappingRequestRepository;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/scrapping-requests")
public class ScrappingRequestController {

    @Autowired
    private ScrappingRequestRepository scrappingRequestRepository;

    @PostMapping("/scrap")
    public String scrapAndGeneratePdf(@RequestBody ScrappingRequest request) {
        System.setProperty("jsse.enableSNIExtension", "false");
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
        String urlWebsite = request.getUrl();
        String motParticulier = request.getKeyword().toLowerCase();

        try {
            Document document = Jsoup.connect(urlWebsite).get();
            Elements liens = document.select("a[href]");

            StringBuilder result = new StringBuilder("Liens extraits de la page contenant le texte particulier:\n");

            for (Element lienElement : liens) {
                String texteLien = lienElement.text().toLowerCase();
                if (texteLien.contains(motParticulier)) {
                    result.append(lienElement.attr("href")).append("\n");
                }
            }

            if (result.toString().equals("Liens extraits de la page contenant le texte particulier:\n")) {
                result.append("Aucun lien correspondant au mot particulier trouvé.");
            }

            String cheminPDF = "C:\\Users\\bough\\Desktop\\scrapping.pdf";

            int i = 1;
            while (new File(cheminPDF).exists()) {
                cheminPDF = "C:\\Users\\r_boug\\Desktop\\scrapping" + i + ".pdf";
                i++;
            }

            genererPDF(liens, cheminPDF, motParticulier);

            scrappingRequestRepository.save(request);

            return result.toString();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return "Erreur lors du scrapping : " + e.getMessage();
        }
    }

    private static void genererPDF(Elements liens, String nomFichier, String motParticulier) throws DocumentException {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            document.open();

            boolean desLiensAjoutes = false;

            for (Element lienElement : liens) {
                String texteLien = lienElement.text().toLowerCase();
                if (texteLien.contains(motParticulier)) {
                    document.add(new Paragraph(lienElement.attr("href")));
                    desLiensAjoutes = true;
                }
            }

            // Ajouter une page seulement si des liens ont été ajoutés
            if (!desLiensAjoutes) {
                document.add(new Paragraph("Aucun lien correspondant au mot particulier trouvé."));
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }
}
