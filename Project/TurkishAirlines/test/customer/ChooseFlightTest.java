package customer;

import models.Customer;
import models.Flight;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChooseFlight extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Flight> flights = (ArrayList<Flight>) getServletContext().getAttribute("flights");

        if (flights == null  flights.isEmpty()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Nenhum voo disponível.");
            return;
        }

        String flightName = request.getParameter("flight_name");
        if (flightName == null  flightName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome do voo não informado.");
            return;
        }

        Flight f = null;
        for (Flight flight : flights) {
            if (flight.getFlightName().equals(flightName)) {
                f = flight;
                break;
            }
        }

        if (f == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Voo não encontrado.");
            return;
        }

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        if (customer == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autenticado.");
            return;
        }

        f.setCustomer(customer);

        request.getRequestDispatcher("CurrentBooking.do").forward(request, response);
    }
}
