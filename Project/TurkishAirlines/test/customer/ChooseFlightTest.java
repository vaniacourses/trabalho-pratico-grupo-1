package customer;

import models.Customer;
import models.Flight;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;

public class ChooseFlightTest {

    @InjectMocks
    private ChooseFlight chooseFlight;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private ServletContext servletContext;

    @Mock
    private RequestDispatcher requestDispatcher;

    private ArrayList<Flight> flights;
    private Customer customer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Inicializando cliente e voos
        customer = new Customer("John Doe", "john.doe@example.com", new ArrayList<>());
        flights = new ArrayList<>();
        flights.add(new Flight(
            false, 10, 5, 3, 18,
            "Flight101", new ArrayList<>(), 18, 18,
            "New York", "London", Date.valueOf("2025-01-01"), Date.valueOf("2025-01-02"),
            10, 5, 3
        ));

        // Configurando mocks
        when(request.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("flights")).thenReturn(flights);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("customer")).thenReturn(customer);
        when(request.getRequestDispatcher("CurrentBooking.do")).thenReturn(requestDispatcher);
    }



    @Test
    public void testFlightSelectionAndCustomerAssociation() throws IOException, ServletException {
        // Simulando o parâmetro "flight_name"
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        // Chamando o método doPost
        chooseFlight.doPost(request, response);

        // Verificando se o cliente foi associado ao voo
        Flight selectedFlight = flights.get(0);
        assertEquals("O número de assentos disponíveis deve diminuir", 17, selectedFlight.getCurrentSeats());
        boolean customerAssociated = selectedFlight.getSeats().stream().anyMatch(seat -> seat.c == customer);
        assertTrue("O cliente deve estar associado ao assento", customerAssociated);

        // Verificando o redirecionamento
        verify(requestDispatcher).forward(request, response);
    }




}
