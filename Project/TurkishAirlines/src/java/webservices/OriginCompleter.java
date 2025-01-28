package webservices;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

import models.FBS;

public class OriginCompleter extends HttpServlet 
{
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
    {
        response.setContentType("application/json");

        try 
        {
            ServletContext sc = getServletContext();

            String term = request.getParameter("term");

            ArrayList<String> list = ((FBS) (sc.getAttribute("fbs"))).getCities((Connection) sc.getAttribute("con"), term);

            String searchList = new Gson().toJson(list);

            response.getWriter().write(searchList);
            
        } catch (Exception e) {
        e.printStackTrace();  // Isso ajuda a entender o que deu errado no log
        try {
            response.getWriter().write("{\"error\": \"An error occurred while processing the request.\"}");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    }
    
    
}