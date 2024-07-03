package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.ClientDao;
import model.ClientModel;
import model.EnvoyerModel;
import dao.EnvoyerDao;

@WebServlet("/api/envoyer/generatePDF")
public class GeneratePDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(GeneratePDFServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String numtel = request.getParameter("numtel");
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));

        LOGGER.info("Generating PDF for numtel: " + numtel + ", month: " + month + ", year: " + year);

        EnvoyerDao envoyerDao = new EnvoyerDao();
        try {
            List<EnvoyerModel> operations = envoyerDao.getOperationsByClientAndMonth(numtel, month, year);
            LOGGER.info("Retrieved " + operations.size() + " operations");

            ClientModel client = ClientDao.getClientInfo(numtel);
            if (client == null) {
                LOGGER.warning("Client not found for numtel: " + numtel);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
                return;
            }
            LOGGER.info("Retrieved client info: " + client.getNom());

            if (operations.isEmpty()) {
                LOGGER.info("No operations found for the given period");
                response.setContentType("text/plain");
                response.getWriter().write("Aucune opération trouvée pour cette période.");
                return;
            }

            response.setContentType("application/pdf");
            
            String fileName = String.format("releve_operations_%s_%02d_%d.pdf", 
                                            client.getNom().replaceAll("\\s+", "_"), 
                                            month, 
                                            year);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            SimpleDateFormat pdfDateFormat = new SimpleDateFormat("MMMM yyyy", new Locale("fr", "FR"));
            Date date = new Date(year - 1900, month - 1, 1);
            String formattedDate = pdfDateFormat.format(date);
            Paragraph dt =new Paragraph("Date : " + formattedDate);
            dt.setAlignment(Element.ALIGN_CENTER);

            document.add(dt);
            document.add(new Paragraph("Contact : " + client.getNumtel()));
            document.add(new Paragraph(client.getNom()));
            document.add(new Paragraph(getSexe(client.getSexe())));
            document.add(new Paragraph("Solde actuel : " + client.getSolde() + " Euros"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Date");
            table.addCell("Raison");
            table.addCell("Nom du récepteur");
            table.addCell("Montant");

            double totalDebit = 0;
            for (EnvoyerModel operation : operations) {
                table.addCell(operation.getDate());
                table.addCell(operation.getRaison());
                
                String recepteurName;
                try {
                    recepteurName = ClientDao.getClientName(operation.getNumRecepteur());
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Erreur lors de la récupération du nom du récepteur", e);
                    recepteurName = operation.getNumRecepteur(); // Utiliser le numéro si le nom n'est pas trouvé
                }
                table.addCell(recepteurName);
                
                table.addCell(String.valueOf(operation.getMontant()));
                totalDebit += operation.getMontant();
            }

            document.add(table);
            document.add(new Paragraph("Total Débit : " + totalDebit + " euros"));

            document.close();
            LOGGER.info("PDF generated successfully");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception occurred", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        } catch (DocumentException e) {
            LOGGER.log(Level.SEVERE, "Document Exception occurred", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error occurred");
        }
    }

    private String getMonthName(int month) {
        return new String[]{"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"}[month - 1];
    }

    private String getSexe(String sexe) {
        return "M".equalsIgnoreCase(sexe) ? "Masculin" : "Féminin";
    }
}