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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OriginCompleterTest {

    @InjectMocks
    private OriginCompleter originCompleter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext context;

    private List<Flight> flights;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flights = new ArrayList<>();
        flights.add(new Flight(true, 5, 3, 2, 10, "Flight101", new ArrayList<>(), 10, 10, "CityA", "CityB", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 5, 3, 2));
        flights.add(new Flight(true, 6, 4, 3, 13, "Flight102", new ArrayList<>(), 13, 13, "CityC", "CityD", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 6, 4, 3));

        when(context.getAttribute("flights")).thenReturn(flights);
        when(request.getServletContext()).thenReturn(context);
    }

    // Testa se a lista de origens é retornada corretamente
    @Test
    void testGetOrigins() throws IOException {
        when(request.getParameter("term")).thenReturn("City");

        originCompleter.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).getWriter().write("[\"CityA\",\"CityC\"]");
    }

    // Testa se a lista de origens é filtrada corretamente
    @Test
    void testGetFilteredOrigins() throws IOException {
        when(request.getParameter("term")).thenReturn("CityA");

        originCompleter.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).getWriter().write("[\"CityA\"]");
    }

    // Testa se a lista de origens está vazia quando não há correspondência
    @Test
    void testGetNoMatchingOrigins() throws IOException {
        when(request.getParameter("term")).thenReturn("CityX");

        originCompleter.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).getWriter().write("[]");
    }

    // Testa se o método doGet não lança exceções
    @Test
    void testDoGetDoesNotThrowException() {
        when(request.getParameter("term")).thenReturn("City");

        assertDoesNotThrow(() -> originCompleter.doGet(request, response));
    }
}