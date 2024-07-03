package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ClientDao;
import model.ClientModel;

@WebServlet("/api/clients/*")
public class ClientController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ClientDao clientDao = new ClientDao();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        List<ClientModel> clients = new ArrayList<>();

        if (pathInfo != null && pathInfo.startsWith("/search/")) {
            String searchValue = pathInfo.substring("/search/".length());
            clients = clientDao.searchClients(searchValue);
        } else if (pathInfo == null || pathInfo.equals("/")) {
            clients = clientDao.getAllClients();
        } else {
            String numtel = pathInfo.substring(1);
            ClientModel client = clientDao.getClientById(numtel);
            if (client.getNumtel() != null) {
                clients.add(client);
            }
        }

        String json = gson.toJson(clients);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClientModel client = gson.fromJson(request.getReader(), ClientModel.class);
        clientDao.createClient(client);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClientModel clientModel = gson.fromJson(request.getReader(), ClientModel.class);
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String numtel = pathInfo.substring(1);
            clientDao.updateClient(numtel, clientModel);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String numtel = pathInfo.substring(1);
            clientDao.deleteClient(numtel);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}