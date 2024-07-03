package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.EnvoyerDao;
import dao.ClientDao;
import model.ClientModel;
import model.EnvoyerModel;
import config.Notification;

@WebServlet("/api/envoyer/*")
public class EnvoyerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EnvoyerDao envoyerDao = new EnvoyerDao();
    private final ClientDao clientDao = new ClientDao();
    private final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        try {
            if ("/phoneNumbers".equals(pathInfo)) {
                List<String> phoneNumbers = clientDao.getAllPhoneNumbers();
                String json = gson.toJson(phoneNumbers);
                response.setContentType("application/json");
                response.getWriter().write(json);
            } else {
                List<EnvoyerModel> envoyers = new ArrayList<>();

                if (pathInfo != null && pathInfo.startsWith("/search/")) {
                    String searchValue = pathInfo.substring("/search/".length());
                    envoyers = envoyerDao.searchEnvoyerByDate(searchValue);
                } else if (pathInfo == null || pathInfo.equals("/")) {
                    envoyers = envoyerDao.getAllEnvoyer();
                } else {
                    String idEnv = pathInfo.substring(1);
                    EnvoyerModel envoyer = envoyerDao.getEnvoyerById(idEnv);
                    if (envoyer != null && envoyer.getIdEnv() != null) {
                        envoyers.add(envoyer);
                    }
                }

                String json = gson.toJson(envoyers);
                response.setContentType("application/json");
                response.getWriter().write(json);
            }
        } catch (SQLException e) {
            handleException(response, e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EnvoyerModel envoyer = gson.fromJson(request.getReader(), EnvoyerModel.class);

            // Vérifier et formater la date
            if (envoyer.getDate() != null) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = inputFormat.parse(envoyer.getDate());
                envoyer.setDate(outputFormat.format(date));
            } else {
                throw new IllegalArgumentException("La date est requise");
            }

            if (envoyer.getNumEnvoyeur().equals(envoyer.getNumRecepteur())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("L'envoyeur et le récepteur ne peuvent pas être les mêmes.");
                return;
            }

            ClientModel envoyeurInfo = clientDao.getClientInfo(envoyer.getNumEnvoyeur());
            ClientModel recepteurInfo = clientDao.getClientInfo(envoyer.getNumRecepteur());

            if (envoyeurInfo == null || recepteurInfo == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("L'un des clients spécifiés n'existe pas.");
                return;
            }

            if (envoyeurInfo.getSolde() < envoyer.getMontant()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Solde insuffisant pour effectuer cette transaction.");
                return;
            }

            clientDao.updateBalances(envoyer.getNumEnvoyeur(), envoyer.getNumRecepteur(), envoyer.getMontant());

            envoyerDao.createEnvoyer(envoyer);

            if (envoyeurInfo.getMail() != null && !envoyeurInfo.getMail().isEmpty()) {
                Notification.sendNotificationEmail(envoyeurInfo.getMail(), envoyeurInfo.getNom(), recepteurInfo.getNom(), envoyer.getMontant(), true);
            }
            if (recepteurInfo.getMail() != null && !recepteurInfo.getMail().isEmpty()) {
                Notification.sendNotificationEmail(recepteurInfo.getMail(), recepteurInfo.getNom(), envoyeurInfo.getNom(), envoyer.getMontant(), false);
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(envoyer));
        } catch (SQLException e) {
            handleException(response, e);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Format de date invalide. Utilisez le format yyyy-MM-dd.");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String idEnv = pathInfo.substring(1);
            try {
                EnvoyerModel envoyer = gson.fromJson(request.getReader(), EnvoyerModel.class);
                EnvoyerModel existingEnvoyer = envoyerDao.getEnvoyerById(idEnv);
                if (existingEnvoyer == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Envoyer not found");
                    return;
                }

                // Vérifier et formater la date
                if (envoyer.getDate() != null) {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = inputFormat.parse(envoyer.getDate());
                    envoyer.setDate(outputFormat.format(date));
                } else {
                    envoyer.setDate(existingEnvoyer.getDate());
                }

                int oldMontant = existingEnvoyer.getMontant();
                int newMontant = envoyer.getMontant();

                if (oldMontant != newMontant) {
                    int diffMontant = newMontant - oldMontant;
                    if (diffMontant > 0) {
                        clientDao.updateBalancesOnIncrease(existingEnvoyer.getNumEnvoyeur(), existingEnvoyer.getNumRecepteur(), diffMontant);
                    } else {
                        clientDao.updateBalancesOnDecrease(existingEnvoyer.getNumEnvoyeur(), existingEnvoyer.getNumRecepteur(), -diffMontant);
                    }
                }

                envoyerDao.updateEnvoyer(idEnv, envoyer);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(envoyer));
            } catch (SQLException e) {
                handleException(response, e);
            } catch (ParseException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Format de date invalide. Utilisez le format yyyy-MM-dd.");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid path for update operation");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String idEnv = pathInfo.substring(1);
            try {
                EnvoyerModel envoyer = envoyerDao.getEnvoyerById(idEnv);
                if (envoyer == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Envoyer not found");
                    return;
                }

                clientDao.updateBalancesOnCancellation(envoyer.getNumRecepteur(), envoyer.getNumEnvoyeur(), envoyer.getMontant());

                envoyerDao.deleteEnvoyer(idEnv);

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Envoyer deleted successfully and balances updated");
            } catch (SQLException e) {
                handleException(response, e);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid path for delete operation");
        }
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("Error: " + e.getMessage());
        e.printStackTrace();
    }
}