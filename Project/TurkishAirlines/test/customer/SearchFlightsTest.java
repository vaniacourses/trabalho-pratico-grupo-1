package customer;

import models.Flight;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Criando a lista de voos
        flights = new ArrayList<>();
        flights.add(new Flight(true, 5, 3, 2, 10, "Flight101", new ArrayList<>(), 10, 10, "CityA", "CityB", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 5, 3, 2));
        flights.add(new Flight(true, 6, 4, 3, 13, "Flight102", new ArrayList<>(), 13, 13, "CityC", "CityD", new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), 6, 4, 3));

        // Configurando mocks
        when(request.getServletContext().getAttribute("flights")).thenReturn(flights);
        when(request.getRequestDispatcher("ShowFlights.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testFlightsAreFilteredAndSetInRequest() throws ServletException, IOException {
        // Configurando parâmetros
        when(request.getParameter("from")).thenReturn("CityA");
        when(request.getParameter("to")).thenReturn("CityB");
        when(request.getParameter("depart")).thenReturn("12/12/2023");
        when(request.getParameter("people")).thenReturn("2");
        when(request.getParameter("class")).thenReturn("Economy");

        // Chamando o método doPost
        searchFlights.doPost(request, response);

        // Verificando que o atributo "results" foi configurado com a lista de resultados
        verify(request).setAttribute(eq("results"), anyList());

        // Verificando se o dispatcher foi chamado corretamente
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDispatcherIsCalledCorrectly() throws ServletException, IOException {
        // Configurando parâmetros
        when(request.getParameter("from")).thenReturn("CityA");
        when(request.getParameter("to")).thenReturn("CityB");
        when(request.getParameter("depart")).thenReturn("12/12/2023");
        when(request.getParameter("people")).thenReturn("2");
        when(request.getParameter("class")).thenReturn("Economy");

        // Chamando o método doPost
        searchFlights.doPost(request, response);

        // Verificando o redirecionamento
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testResultsContainExpectedFlight() throws ServletException, IOException {
        // Configurando parâmetros
        when(request.getParameter("from")).thenReturn("CityA");
        when(request.getParameter("to")).thenReturn("CityB");
        when(request.getParameter("depart")).thenReturn("12/12/2023");
        when(request.getParameter("people")).thenReturn("2");
        when(request.getParameter("class")).thenReturn("Economy");

        // Chamando o método doPost
        searchFlights.doPost(request, response);

        // Verificando os resultados no atributo "results"
        verify(request).setAttribute(eq("results"), argThat(argument -> {
            if (!(argument instanceof ArrayList)) {
                return false;
            }
            ArrayList<Flight> flightResults = (ArrayList<Flight>) argument;
            return flightResults.stream().anyMatch(flight -> flight.getFlightName().equals("Flight101"));
        }));
    }
}
