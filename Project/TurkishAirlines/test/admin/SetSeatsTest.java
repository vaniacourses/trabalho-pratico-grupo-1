package admin;

import models.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SetSeatsTest {

    @InjectMocks
    private SetSeats setSeatsServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext servletContext;

    private ArrayList<Flight> flights;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        flights = new ArrayList<>();

        flight = new Flight();
        flight.setFlightName("Flight123");
        flight.setEconomySeats(100);
        flight.setBusinessSeats(50);
        flight.setFirstSeats(20);
        flights.add(flight);

        when(servletContext.getAttribute("flights")).thenReturn(flights);
        when(request.getServletContext()).thenReturn(servletContext);

        when(request.getParameter("flight_name")).thenReturn("Flight123");
        when(request.getParameter("seats_e")).thenReturn("120");
        when(request.getParameter("seats_b")).thenReturn("60");
        when(request.getParameter("seats_f")).thenReturn("25");
    }

    @Test
    public void testDoPost() throws Exception {

        setSeatsServlet.doPost(request, response);

        assertEquals(120, flight.getEconomySeats());
        assertEquals(60, flight.getBusinessSeats());
        assertEquals(25, flight.getFirstSeats());
        assertEquals(205, flight.getTotalSeats());

        assertTrue(flight.isChanged);

        verify(response).sendRedirect("SetSeats.jsp");
    }
}
