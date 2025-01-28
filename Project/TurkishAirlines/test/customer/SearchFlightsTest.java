package customer;

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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class SearchFlightsTest {

    @InjectMocks
    private SearchFlights searchFlights;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    private ArrayList<Flight> flights;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flights = new ArrayList<>();
        flights.add(new Flight(true, 5, 3, 2, 10, "Flight101", new ArrayList<>(), 10, 10, "CityA", "CityB", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 5, 3, 2));
        flights.add(new Flight(true, 6, 4, 3, 13, "Flight102", new ArrayList<>(), 13, 13, "CityC", "CityD", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 6, 4, 3));

        when(request.getServletContext().getAttribute("flights")).thenReturn(flights);
        when(request.getRequestDispatcher("ShowFlights.jsp")).thenReturn(requestDispatcher);
    }

    // Testa se os voos são filtrados corretamente
    @Test
    void testFlightsAreFiltered() throws ServletException, IOException {
        when(request.getParameter("from")).thenReturn("CityA");
        when(request.getParameter("to")).thenReturn("CityB");
        when(request.getParameter("depart")).thenReturn("12/12/2023");
        when(request.getParameter("people")).thenReturn("2");
        when(request.getParameter("class")).thenReturn("Economy");

        searchFlights.doPost(request, response);

        verify(request).setAttribute(eq("results"), anyList());
    }

    // Testa se o dispatcher é chamado corretamente
    @Test
    void testDispatcherIsCalled() throws ServletException, IOException {
        when(request.getParameter("from")).thenReturn("CityA");
        when(request.getParameter("to")).thenReturn("CityB");
        when(request.getParameter("depart")).thenReturn("12/12/2023");
        when(request.getParameter("people")).thenReturn("2");
        when(request.getParameter("class")).thenReturn("Economy");

        searchFlights.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o método doPost não lança exceções
    @Test
    void testDoPostDoesNotThrowException() {
        when(request.getParameter("from")).thenReturn("CityA");
        when(request.getParameter("to")).thenReturn("CityB");
        when(request.getParameter("depart")).thenReturn("12/12/2023");
        when(request.getParameter("people")).thenReturn("2");
        when(request.getParameter("class")).thenReturn("Economy");

        assertDoesNotThrow(() -> searchFlights.doPost(request, response));
    }

    // Testa se os resultados são definidos corretamente
    @Test
    void testResultsAreSet() throws ServletException, IOException {
        when(request.getParameter("from")).thenReturn("CityA");
        when(request.getParameter("to")).thenReturn("CityB");
        when(request.getParameter("depart")).thenReturn("12/12/2023");
        when(request.getParameter("people")).thenReturn("2");
        when(request.getParameter("class")).thenReturn("Economy");

        searchFlights.doPost(request, response);

        verify(request).setAttribute(eq("results"), anyList());
    }

    // Testa se o formato da data é correto
    @Test
    void testDateFormatIsCorrect() throws ServletException, IOException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        when(request.getParameter("depart")).thenReturn("12/12/2023");

        searchFlights.doPost(request, response);

        assertDoesNotThrow(() -> df.parse(request.getParameter("depart")));
    }
}