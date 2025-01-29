package manager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import models.Flight;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author heronlancellot
 */
public class ApproveSeats extends HttpServlet {


    @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

     ArrayList<Flight> flights = (ArrayList<Flight>) getServletContext().getAttribute("flights");

     if (flights == null) {
         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Flight list is not available.");
         return;
     }

     Flight f = null;

     String flightName = request.getParameter("flight_name");
     if (flightName == null || flightName.isEmpty()) {
         response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Flight name parameter is missing.");
         return;
     }

     for (Flight flight : flights) {
         if (flight.getFlightName().equals(flightName)) {
             f = flight;
             break;
         }
     }

     if (f == null) {
         response.sendError(HttpServletResponse.SC_NOT_FOUND, "Flight not found.");
         return;
     }

     f.setOldESeats(0);
     f.setOldBSeats(0);
     f.setOldFSeats(0);
     f.setOldTSeats(0);

     f.isChanged = false;
     response.sendRedirect("ApproveSeats.jsp");
 }
}
