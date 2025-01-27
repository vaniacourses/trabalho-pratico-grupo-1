package customer;

import models.Customer;
import models.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

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
    private RequestDispatcher requestDispatcher;

    @Mock
    private Customer customer;

    private ArrayList<Flight> flights;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flights = new ArrayList<>();
        flights.add(new Flight(true, 5, 3, 2, 10, "Flight101", new ArrayList<>(), 10, 10, "CityA", "CityB", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 5, 3, 2));
        flights.add(new Flight(true, 6, 4, 3, 13, "Flight102", new ArrayList<>(), 13, 13, "CityC", "CityD", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 6, 4, 3));

        when(request.getServletContext().getAttribute("flights")).thenReturn(flights);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("customer")).thenReturn(customer);
        when(request.getRequestDispatcher("CurrentBooking.do")).thenReturn(requestDispatcher);
    }

    // Testa se o voo é selecionado corretamente
    @Test
    void testFlightIsSelected() throws ServletException, IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        chooseFlight.doPost(request, response);

        assertEquals(customer, flights.get(0).getSeats().get(0).getCustomer());
    }

    // Testa se o cliente é definido no voo
    @Test
    void testCustomerIsSetInFlight() throws ServletException, IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        chooseFlight.doPost(request, response);

        verify(session).getAttribute("customer");
        assertEquals(customer, flights.get(0).getSeats().get(0).getCustomer());
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    void testRedirectIsDone() throws ServletException, IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        chooseFlight.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o método doPost não lança exceções
    @Test
    void testDoPostDoesNotThrowException() {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        assertDoesNotThrow(() -> chooseFlight.doPost(request, response));
    }
}