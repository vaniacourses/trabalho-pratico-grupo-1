package manager;

import models.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class DisapproveSeatsTest {

    @InjectMocks
    private DisapproveSeats disapproveSeats;

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

    // Testa se os assentos antigos são restaurados
    @Test
    void testOldSeatsAreRestored() throws IOException, ServletException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        disapproveSeats.doPost(request, response);

        assertEquals(5, flights.get(0).getEconomySeats());
        assertEquals(3, flights.get(0).getBusinessSeats());
        assertEquals(2, flights.get(0).getFirstSeats());
    }

    // Testa se os assentos antigos são resetados
    @Test
    void testOldSeatsAreReset() throws IOException, ServletException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        disapproveSeats.doPost(request, response);

        assertEquals(0, flights.get(0).getOldESeats());
        assertEquals(0, flights.get(0).getOldBSeats());
        assertEquals(0, flights.get(0).getOldFSeats());
        assertEquals(0, flights.get(0).getOldTSeats());
    }

    // Testa se a flag isChanged é definida como falsa
    @Test
    void testIsChangedFlagIsReset() throws IOException, ServletException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        disapproveSeats.doPost(request, response);

        assertFalse(flights.get(0).isChanged);
    }

    // Testa se o redirecionamento é feito corretamente
    @Test
    void testRedirectIsDone() throws IOException, ServletException {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        disapproveSeats.doPost(request, response);

        verify(response).sendRedirect("ApproveSeats.jsp");
    }

    // Testa se o método doPost não lança exceções
    @Test
    void testDoPostDoesNotThrowException() {
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        assertDoesNotThrow(() -> disapproveSeats.doPost(request, response));
    }
}