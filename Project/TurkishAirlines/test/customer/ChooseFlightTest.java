package customer;

import models.Customer;
import models.Flight;
import models.Seat;
import models.Features;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

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
    private ArrayList<Seat> seats;
    ArrayList<Features> features;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando a lista de assentos e o voo
        seats = new ArrayList<>();  // Inicializa corretamente a lista de assentos
        features.add(new Features(100, 120, 1, true, 32.5, 18.0, 33.0, 18.5, "LCD", "LED", "Economy", "Business", "AC", "DC", "Yes", "No", "Vegetarian", "Vegan"));

        // Criando o voo com os assentos
        Flight flight = new Flight(true, 5, 3, 2, 10, "Flight101", seats, 10, 10, "CityA", "CityB", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 5, 3, 2);
        
        // Criando o cliente
        customers.add(new Customer("Customer", "shariq@customer.com", new ArrayList<>()));
        
        // Adicionando assentos ao voo
        for (int i = 0; i < 10; i++) {
            seats.add(new Seat(i + 1, flight, features, customer));
            System.out.println("Assento adicionado: " + (i+1)); // Adicionando log
        }

        // Inicializando a lista de voos
        flights = new ArrayList<>();
        flights.add(flight);

        // Mocking as dependências para o teste
        when(request.getServletContext().getAttribute("flights")).thenReturn(flights);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("customer")).thenReturn(customer);
        when(request.getRequestDispatcher("CurrentBooking.do")).thenReturn(requestDispatcher);
    }

    // Testa se o voo é selecionado corretamente
    @Test
    public void testFlightIsSelected() throws ServletException, IOException {
        // Definindo o parâmetro 'flight_name' como "Flight101"
        when(request.getParameter("flight_name")).thenReturn("Flight101");

        // Executando o método doPost() da ChooseFlight
        chooseFlight.doPost(request, response);

        // Verificando se o cliente foi atribuído ao assento correto
        assertEquals(customer, seats.get(0).getCustomer());  // O primeiro assento deve ter o cliente
    }
}
