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
public class ApproveSeatsTest {
    
    private ApproveSeats approveSeatsServlet;
    private ArrayList<Flight> flights;

    @BeforeEach
    public void setUp() {
        approveSeatsServlet = new ApproveSeats();

        // Criação de um contexto simulado para a servlet
        flights = new ArrayList<>();
        Flight flight = new Flight(true, 5, 5, 5, 5, "Flight123", new ArrayList<>(), 100, 90, "CityA", "CityB", null, null, 50, 30, 20);
        flights.add(flight);
        
        // Simulando o contexto da servlet
        getServletContext().setAttribute("flights", flights);
    }

    @Test
    public void testDoPost_ApprovesSeats() throws ServletException, IOException {
        // Simulação dos objetos request e response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        when(request.getParameter("flight_name")).thenReturn("Flight123");

        // Chamada do método doPost
        approveSeatsServlet.doPost(request, response);

        // Verificações
        Flight approvedFlight = flights.get(0);
        assertEquals(0, approvedFlight.getOldESeats());
        assertEquals(0, approvedFlight.getOldBSeats());
        assertEquals(0, approvedFlight.getOldFSeats());
        assertEquals(0, approvedFlight.getOldTSeats());
        assertFalse(approvedFlight.isChanged);

        // Verifica se o redirecionamento foi chamado
        verify(response).sendRedirect("ApproveSeats.jsp");
    }

    // Método para simular o contexto da servlet
    private ServletContext getServletContext() {
        return mock(ServletContext.class);
    }

    private Object verify(HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(int i, int oldFSeats) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertFalse(boolean changed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
