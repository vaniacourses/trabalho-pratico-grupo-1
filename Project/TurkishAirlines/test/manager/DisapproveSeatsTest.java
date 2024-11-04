/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package manager;
import models.Flight;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import static org.mockito.Mockito.*;



/**
 *
 * @author Rafaela
 */
public class DisapproveSeatsTest {
    
    private DisapproveSeats disapproveSeatsServlet;
    private ArrayList<Flight> flights;

    @BeforeEach
    public void setUp() {
        disapproveSeatsServlet = new DisapproveSeats();

        // Criação de um contexto simulado para a servlet
        flights = new ArrayList<>();
        Flight flight = new Flight(true, 10, 20, 30, 60, "Flight123", new ArrayList<>(), 100, 90, "CityA", "CityB", null, null, 50, 30, 20);
        flights.add(flight);
        
        // Simulando o contexto da servlet
        ServletContext context = mock(ServletContext.class);
        when(context.getAttribute("flights")).thenReturn(flights);
        disapproveSeatsServlet.setServletContext(context);
    }

    @Test
    public void testDoPost_DisapprovesSeats() throws ServletException, IOException {
        // Simulação dos objetos request e response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        when(request.getParameter("flight_name")).thenReturn("Flight123");

        // Chamada do método doPost
        disapproveSeatsServlet.doPost(request, response);

        // Verificações
        Flight disapprovedFlight = flights.get(0);
        assertEquals(10, disapprovedFlight.getEconomySeats());
        assertEquals(20, disapprovedFlight.getBusinessSeats());
        assertEquals(30, disapprovedFlight.getFirstSeats());
        assertEquals(60, disapprovedFlight.getTotalSeats());
        assertEquals(60, disapprovedFlight.getCurrentSeats());
        assertEquals(0, disapprovedFlight.getOldESeats());
        assertEquals(0, disapprovedFlight.getOldBSeats());
        assertEquals(0, disapprovedFlight.getOldFSeats());
        assertEquals(0, disapprovedFlight.getOldTSeats());
        assertFalse(disapprovedFlight.isChanged);

        // Verifica se o redirecionamento foi chamado
        verify(response).sendRedirect("ApproveSeats.jsp");
    }

    private Object verify(HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
