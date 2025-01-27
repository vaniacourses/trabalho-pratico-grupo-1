package customer;

import models.Customer;
import models.Seat;
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

public class CurrentBookingTest {

    @InjectMocks
    private CurrentBooking currentBooking;

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

    private ArrayList<Seat> seats;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        seats = new ArrayList<>();
        seats.add(new Seat(1, null, null, customer));
        seats.add(new Seat(2, null, null, customer));

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("customer")).thenReturn(customer);
        when(customer.getCurrentBooking()).thenReturn(seats);
        when(request.getRequestDispatcher("CurrentBooking.jsp")).thenReturn(requestDispatcher);
    }

    // Testa se os assentos são definidos corretamente no doGet
    @Test
    void testSeatsAreSetInDoGet() throws ServletException, IOException {
        currentBooking.doGet(request, response);
        verify(request).setAttribute("seats", seats);
    }

    // Testa se o dispatcher é chamado corretamente no doGet
    @Test
    void testDispatcherIsCalledInDoGet() throws ServletException, IOException {
        currentBooking.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o método doGet não lança exceções
    @Test
    void testDoGetDoesNotThrowException() {
        assertDoesNotThrow(() -> currentBooking.doGet(request, response));
    }

    // Testa se os assentos são definidos corretamente no doPost
    @Test
    void testSeatsAreSetInDoPost() throws ServletException, IOException {
        currentBooking.doPost(request, response);
        verify(request).setAttribute("seats", seats);
    }

    // Testa se o dispatcher é chamado corretamente no doPost
    @Test
    void testDispatcherIsCalledInDoPost() throws ServletException, IOException {
        currentBooking.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    // Testa se o método doPost não lança exceções
    @Test
    void testDoPostDoesNotThrowException() {
        assertDoesNotThrow(() -> currentBooking.doPost(request, response));
    }
}