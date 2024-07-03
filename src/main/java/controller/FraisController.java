package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.FraisDao;
import model.FraisModel;

@WebServlet("/api/frais/*")
public class FraisController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final FraisDao FraisDao = new FraisDao();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<FraisModel> Frais = FraisDao.getAllFrais();
            String json = gson.toJson(Frais);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            String idfrais = pathInfo.substring(1);
            FraisModel Frais = FraisDao.getFraisById(idfrais);
            String json;
            if (Frais.getIdfrais() != null) {
                json = gson.toJson(Frais);
            } else {
                json = "{}"; 
            }
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FraisModel Frais = gson.fromJson(request.getReader(), FraisModel.class);
        FraisDao.createFrais(Frais);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FraisModel Frais = gson.fromJson(request.getReader(), FraisModel.class);
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String idfrais = pathInfo.substring(1);
            FraisDao.updateFrais(idfrais, Frais);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            String idfrais = pathInfo.substring(1);
            FraisDao.deleteFrais(idfrais);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}