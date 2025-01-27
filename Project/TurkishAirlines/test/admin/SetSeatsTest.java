package admin;

import models.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class SetSeatsTest {

    @InjectMocks
    private SetSeats setSeats;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext context;

    private ArrayList<Flight> flights;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flights = new ArrayList<>();
        flights.add(new Flight(true, 5, 3, 2, 10, "Flight101", new ArrayList<>(), 10, 10, "CityA", "CityB", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 5, 3, 2));
        flights.add(new Flight(true, 6, 4, 3, 13, "Flight102", new ArrayList<>(), 13, 13, "CityC", "CityD", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 6, 4, 3));

        when(context.getAttribute("flights")).thenReturn(flights);
        when(request.getServletContext()).thenReturn(context);
    }

    // Testa se os assentos antigos são salvos
    @Test
    void testOldSeatsAreSaved() throws IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");
        when(request.getParameter("seats_e")).thenReturn("6");
        when(request.getParameter("seats_b")).thenReturn("4");
        when(request.getParameter("seats_f")).thenReturn("3");

        setSeats.doPost(request, response);

        assertEquals(5, flights.get(0).getOldESeats());
        assertEquals(3, flights.get(0).getOldBSeats());
        assertEquals(2, flights.get(0).getOldFSeats());
        assertEquals(10, flights.get(0).getOldTSeats());
    }

    // Testa se os novos assentos são definidos
    @Test
    void testNewSeatsAreSet() throws IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");
        when(request.getParameter("seats_e")).thenReturn("6");
        when(request.getParameter("seats_b")).thenReturn("4");
        when(request.getParameter("seats_f")).thenReturn("3");

        setSeats.doPost(request, response);

        assertEquals(6, flights.get(0).getEconomySeats());
        assertEquals(4, flights.get(0).getBusinessSeats());
        assertEquals(3, flights.get(0).getFirstSeats());
        assertEquals(13, flights.get(0).getTotalSeats());
    }

    // Testa se os assentos atuais são definidos
    @Test
    void testCurrentSeatsAreSet() throws IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");
        when(request.getParameter("seats_e")).thenReturn("6");
        when(request.getParameter("seats_b")).thenReturn("4");
        when(request.getParameter("seats_f")).thenReturn("3");

        setSeats.doPost(request, response);

        assertEquals(13, flights.get(0).getCurrentSeats());
    }

    // Testa se a flag isChanged é definida como verdadeira
    @Test
    void testIsChangedFlagIsSet() throws IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");
        when(request.getParameter("seats_e")).thenReturn("6");
        when(request.getParameter("seats_b")).thenReturn("4");
        when(request.getParameter("seats_f")).thenReturn("3");

        setSeats.doPost(request, response);

        assertTrue(flights.get(0).isChanged);
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    void testRedirectIsDone() throws IOException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");
        when(request.getParameter("seats_e")).thenReturn("6");
        when(request.getParameter("seats_b")).thenReturn("4");
        when(request.getParameter("seats_f")).thenReturn("3");

        setSeats.doPost(request, response);

        verify(response).sendRedirect("SetSeats.jsp");
    }
}