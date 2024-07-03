package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.TauxDao;
import model.TauxModel;

@WebServlet("/api/taux/*")
public class TauxController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TauxDao TauxDao = new TauxDao();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<TauxModel> Taux = TauxDao.getAllTaux();
            String json = gson.toJson(Taux);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            String idtaux = pathInfo.substring(1);
            TauxModel Taux = TauxDao.getTauxById(idtaux);
            String json;
            if (Taux.getIdtaux() != null) {
                json = gson.toJson(Taux);
            } else {
                json = "{}"; 
            }
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TauxModel Taux = gson.fromJson(request.getReader(), TauxModel.class);
        TauxDao.createTaux(Taux);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TauxModel Taux = gson.fromJson(request.getReader(), TauxModel.class);
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String idtaux = pathInfo.substring(1);
            TauxDao.updateTaux(idtaux, Taux);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String idtaux = pathInfo.substring(1);
            TauxDao.deleteTaux(idtaux);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}